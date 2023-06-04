package com.example.socialgifts.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.example.socialgifts.ApiCalls;
import com.example.socialgifts.Product;
import com.example.socialgifts.R;

public class CreateProductActivity extends AppCompatActivity {
    private EditText editTextName;
    private EditText editTextDescription;
    private EditText editTextProductPrice;
    private ImageButton buttonTakePic;
    private Button buttonCreate;

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
            if(productCorrect()){
                makePost();
            }
        });
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