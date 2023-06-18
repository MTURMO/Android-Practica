package com.example.socialgifts.activities;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.example.socialgifts.ApiCalls;
import com.example.socialgifts.R;
import com.example.socialgifts.WishList;

public class CreateWishlistActivity extends BaseAcivity {
    private EditText newWishlistEditText;
    private Button createWishlistButton;
    private ImageButton backButton;

    private EditText newWishlistDescriptionEditText;
    private EditText endDate;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_wishlist);
        newWishlistEditText = findViewById(R.id.wishlist_new_name);
        newWishlistDescriptionEditText = findViewById(R.id.wishlist_new_description);
        endDate = findViewById(R.id.editTextDate);
        backButton = findViewById(R.id.create_wish_back_button);
        createWishlistButton = findViewById(R.id.wishlist_new_create_button);
        createWishlistButton.setOnClickListener(view -> {
                if(productCorrect()) {
                    makePost();
                    Intent intent = new Intent(this, MainActivity.class);
                    startActivity(intent);
                }
        });
        backButton.setOnClickListener(view -> {
            finish();
        });
    }

    private void makePost(){
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        String accessToken = sharedPreferences.getString("accessToken", "");

        String name = newWishlistEditText.getText().toString();
        String description = newWishlistDescriptionEditText.getText().toString();
        String data = endDate.getText().toString();

        WishList product = new WishList(name, description,data);

        ApiCalls apiCalls = new ApiCalls(this);
        apiCalls.createWishList(accessToken,product,this);
    }
    private boolean productCorrect(){
        String name = newWishlistEditText.getText().toString();
        String description = newWishlistDescriptionEditText.getText().toString();
        String data = endDate.getText().toString();
        return !name.isEmpty() && !description.isEmpty() && !data.isEmpty();
    }
}