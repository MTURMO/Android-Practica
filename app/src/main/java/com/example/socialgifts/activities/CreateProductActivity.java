package com.example.socialgifts.activities;

import androidx.appcompat.app.AppCompatActivity;

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

import com.android.volley.Response;
import com.example.socialgifts.ApiCalls;
import com.example.socialgifts.Category;
import com.example.socialgifts.Product;
import com.example.socialgifts.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class CreateProductActivity extends AppCompatActivity {
    private EditText editTextName;
    private EditText editTextDescription;
    private EditText editTextProductPrice;
    private ImageButton buttonTakePic;
    private Button buttonCreate;
    private Spinner spinnerCategory;
    private List<String> categories;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_product);

        editTextName = findViewById(R.id.create_product_name);
        editTextDescription = findViewById(R.id.create_product_description);
        editTextProductPrice = findViewById(R.id.create_product_price);
        buttonTakePic = findViewById(R.id.create_product_take_pic);
        buttonCreate = findViewById(R.id.create_product_create);

        buttonCreate.setOnClickListener(view -> {
            if (productCorrect()) {
                makePost();
            }
        });

        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        String accessToken = sharedPreferences.getString("accessToken", "");

        spinnerCategory = findViewById(R.id.create_product_spinner);
        categories = new ArrayList<>();

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
                categories.add(category.getName());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCategory.setAdapter(adapter);
    }
    private void makePost(){
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        String accessToken = sharedPreferences.getString("accessToken", "");

        String name = editTextName.getText().toString();
        String description = editTextDescription.getText().toString();
        float price = Float.parseFloat(editTextProductPrice.getText().toString());

        Product product = new Product(name, description, price, "a", "a",0);

        ApiCalls apiCalls = new ApiCalls(this);
        apiCalls.createProduct(accessToken,product,this);
    }

    private boolean productCorrect(){
        String name = editTextName.getText().toString();
        String description = editTextDescription.getText().toString();
        String price = editTextProductPrice.getText().toString();
        return !name.isEmpty() && !description.isEmpty() && !price.isEmpty();
    }
}