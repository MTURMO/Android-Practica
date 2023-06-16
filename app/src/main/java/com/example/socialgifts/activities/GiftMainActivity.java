package com.example.socialgifts.activities;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Response;
import com.bumptech.glide.Glide;
import com.example.socialgifts.ApiCalls;
import com.example.socialgifts.R;

import org.json.JSONObject;

public class GiftMainActivity extends AppCompatActivity {

        private TextView textViewName;
        private TextView textViewcategory;
        private TextView textViewprice;
        private TextView textViewdescription;
        public ImageView image;
        private int idProduct;

        private Button editButton;
        private Button deleteButton;



        @SuppressLint({"MissingInflatedId", "SetTextI18n"})
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.gift_view_main_user);
            image = findViewById(R.id.product_image2);
            textViewName = findViewById(R.id.product_name2);
            textViewcategory = findViewById(R.id.product_category2);
            textViewprice = findViewById(R.id.product_price2);
            textViewdescription = findViewById(R.id.product_description2);
            editButton = findViewById(R.id.product_edit_gift);
            deleteButton = findViewById(R.id.product_delete_gift);

            Intent intent = getIntent();
            String name2 = intent.getStringExtra("name");
            String description2 = intent.getStringExtra("description");
            String image_url2 = intent.getStringExtra("image");
            int category = intent.getIntExtra("category", 0);
            float price = intent.getFloatExtra("price", 0);

            int priority = intent.getIntExtra("priority", 0);
            int id_gift = intent.getIntExtra("id_gift", 0);


            idProduct = intent.getIntExtra("id", 0);
            name2=name2!=null?name2:"null";
            description2=description2!=null?description2:"null";
            image_url2=image_url2!=null?image_url2:"null";

            textViewName.setText(name2);
            textViewcategory.setText(Integer.toString(category));
            textViewprice.setText(Float.toString(price));
            textViewdescription.setText(description2);
            Glide.with(this).load(image_url2).error(R.drawable.ic_launcher_foreground).into(this.image);


            String finalName = name2;
            String finalDescription = description2;
            String finalImage_url = image_url2;
            editButton.setOnClickListener(view ->{
                    Intent intent2 = new Intent(this, EditGiftActivity.class);
                    intent2.putExtra("name", finalName);
                    intent2.putExtra("description", finalDescription);
                    intent2.putExtra("image", finalImage_url);
                    intent2.putExtra("category", category);
                    intent2.putExtra("price", price);
                    intent2.putExtra("id", idProduct);
                    intent2.putExtra("priority", priority);
                    intent2.putExtra("id_gift", id_gift);
                    startActivity(intent2);
            });

            SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
            String accessToken = sharedPreferences.getString("accessToken", "");
            deleteButton.setOnClickListener(view ->{
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Delete gift");
                builder.setMessage("Are you sure you want to delete this gift?");
                builder.setPositiveButton("Yes", (dialog, which) -> {
                    ApiCalls apiCalls = new ApiCalls(this);
                    apiCalls.deleteGiftById(accessToken,id_gift ,this, new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            Intent intent = new Intent(GiftMainActivity.this, MainActivity.class);
                            startActivity(intent);
                        }
                    });
                });
                builder.setNegativeButton("No", (dialog, which) -> dialog.cancel());
                builder.show();
            });
        }




}
