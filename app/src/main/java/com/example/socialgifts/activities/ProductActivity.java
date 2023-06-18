package com.example.socialgifts.activities;

import androidx.appcompat.app.AlertDialog;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.volley.Response;
import com.bumptech.glide.Glide;
import com.example.socialgifts.ApiCalls;
import com.example.socialgifts.Gift;
import com.example.socialgifts.R;
import com.example.socialgifts.WishList;

import org.json.JSONArray;
import org.json.JSONObject;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class ProductActivity extends BaseAcivity {

    private TextView textViewName;
    private TextView textViewcategory;
    private TextView textViewprice;
    private TextView textViewdescription;
    public ImageView image;
    private int idProduct;

    private Button buttonCreateGift, back;
    private Spinner spinner ;
    private AlertDialog selectWishList;
    private List<String> wishLists;
    private int [] idWishlist;



    @SuppressLint({"MissingInflatedId", "SetTextI18n"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.product_view_2);
        image = findViewById(R.id.product_image);
        textViewName = findViewById(R.id.product_name);
        textViewcategory = findViewById(R.id.product_category);
        textViewprice = findViewById(R.id.product_price);
        textViewdescription = findViewById(R.id.product_description);
        buttonCreateGift = findViewById(R.id.product_create_gift);
        back = findViewById(R.id.back_create_gift);

        Intent intent = getIntent();
        String name = intent.getStringExtra("name");
        String description = intent.getStringExtra("description");
        String image_url = intent.getStringExtra("image");
        String category = intent.getStringExtra("category");
        float price = intent.getFloatExtra("price", 0);
        idProduct = intent.getIntExtra("id", 0);

        name=name!=null?name:"null";
        description=description!=null?description:"null";
        image_url=image_url!=null?image_url:"null";

        textViewName.setText(name);
        textViewcategory.setText((category));
        textViewprice.setText(Float.toString(price));
        textViewdescription.setText(description);
        Glide.with(this).load(image_url).error(R.drawable.imagemissing_92832).into(this.image);


        buildDialog(this);
        buttonCreateGift.setOnClickListener(view -> selectWishList.show());
        back.setOnClickListener(view -> {
            finish();
        });
    }

    private void buildDialog(Context context){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view = getLayoutInflater().inflate(R.layout.category_pop_up, null);

        ApiCalls apiCalls = new ApiCalls(ProductActivity.this);
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        String accessToken = sharedPreferences.getString("accessToken", "");

        builder.setView(view);
        builder.setTitle("Choose a wish list")
                .setPositiveButton("OK", (dialogInterface, i) -> {
                    ApiCalls apiCalls2 = new ApiCalls(ProductActivity.this);

                    int posWhishList = spinner.getSelectedItemPosition();
                    String url= "https://balandrau.salle.url.edu/i3/mercadoexpress/api/v1/products/"+idProduct;
                    Gift gift = new Gift(idWishlist[posWhishList],url,0);

                    apiCalls2.createGift(accessToken,gift,ProductActivity.this);

                    Intent intent2 = new Intent(context, MainActivity.class);
                    startActivity(intent2);
                })
                .setNegativeButton("Cancel", (dialogInterface, i) -> {

                });

        selectWishList = builder.create();


        String id = sharedPreferences.getString("id", "");

        spinner = view.findViewById(R.id.wish_list_spinner);
        wishLists = new ArrayList<>();


        //ApiCalls apiCalls = new ApiCalls(this);
        apiCalls.getAllUserWhishlistSpinnerProduct(accessToken, Integer.parseInt(id),new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                parsear(response);
            }
        });

    }

    private void parsear(JSONArray response) {
        idWishlist = new int[response.length()];
        for (int i = 0; i < response.length(); i++) {
            try {
                JSONObject categoryObject = response.getJSONObject(i);
                int id = categoryObject.getInt("id");
                String name = categoryObject.getString("name");
                String end = categoryObject.getString("end_date");


                    idWishlist[i]=id;
                    WishList wishList = new WishList(id, name);
                    wishLists.add(wishList.getName());



            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, wishLists);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }
    private boolean fechaEndPasada(String endDate){
        if (endDate == null || endDate.equalsIgnoreCase("null")) {
            // Si endDate es null, aceptar en este caso
            return true;
        }else
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
            LocalDateTime currentDate = LocalDateTime.now();
            LocalDateTime endDateDate =LocalDateTime.parse(endDate, formatter);
            return endDateDate.isBefore(currentDate);
        }
        return false;
    }
    }
