package com.example.socialgifts.activities;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Response;
import com.example.socialgifts.ApiCalls;
import com.example.socialgifts.Category;
import com.example.socialgifts.Product;
import com.example.socialgifts.R;
import com.example.socialgifts.WishList;
import com.google.gson.JsonArray;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class CreateProductActivity extends BaseAcivity {
    private EditText editTextName;
    private EditText editTextDescription;
    private EditText editTextProductPrice;
    private EditText editTextProductUrl;
    private Button buttonCreate;
    private ImageButton backButton;
    private Spinner spinnerCategory;
    private List<Category> categories;
    private List<String> categoriesName;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_product);

        editTextName = findViewById(R.id.create_product_name);
        editTextDescription = findViewById(R.id.create_product_description);
        editTextProductPrice = findViewById(R.id.create_product_price);
        editTextProductUrl = findViewById(R.id.create_product_url);
        buttonCreate = findViewById(R.id.create_product_create);
        backButton = findViewById(R.id.create_product_back_button);
        buttonCreate.setOnClickListener(view -> {
            if (productCorrect()) {
                makePost();
            }
        });

        backButton.setOnClickListener(view -> {
            finish();
        });
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        String accessToken = sharedPreferences.getString("accessToken", "");

        spinnerCategory = findViewById(R.id.create_product_spinner);
        categories = new ArrayList<>();
        categoriesName = new ArrayList<>();
        ApiCalls apiCalls = new ApiCalls(this);

        apiCalls.getCategoryList(accessToken, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                parsear(response);
            }
        });

    }

    private void parsear(JSONArray response) {
        for (int i = 0; i < response.length(); i++) {
            try {
                JSONObject categoryObject = response.getJSONObject(i);
                int id = categoryObject.getInt("id");
                String name = categoryObject.getString("name");
                String description = categoryObject.getString("description");
                String photo = categoryObject.getString("photo");

                Category category = new Category(id, name, description, photo);
                categories.add(category);
                categoriesName.add(name);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categoriesName);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCategory.setAdapter(adapter);
    }
    private void makePost(){
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        String accessToken = sharedPreferences.getString("accessToken", "");

        String name = editTextName.getText().toString();
        String description = editTextDescription.getText().toString();
        String priceText = editTextProductPrice.getText().toString();
        String url = editTextProductUrl.getText().toString();

        if(isFloat(priceText)){
            float price = Float.parseFloat(priceText);
            int category = spinnerCategory.getSelectedItemPosition();
            Category category1 = categories.get((category));

            JSONArray categoryId = new JSONArray();
            categoryId.put(category1.getId());

            Product product = new Product(name, description, price, url, url,categoryId);

            ApiCalls apiCalls = new ApiCalls(this);
            apiCalls.createProduct(accessToken,product,this);

        } else{
            Toast.makeText(this, "El precio debe ser un float", Toast.LENGTH_SHORT).show();
        }


    }
    private boolean isFloat(String priceText){
        try{
            Float.parseFloat(priceText);
            return true;
        } catch (Exception e){
            return false;
        }
    }


    private boolean productCorrect(){
        String name = editTextName.getText().toString();
        String description = editTextDescription.getText().toString();
        String price = editTextProductPrice.getText().toString();
        return !name.isEmpty() && !description.isEmpty() && !price.isEmpty();
    }
}