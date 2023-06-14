package com.example.socialgifts.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import com.example.socialgifts.ApiCalls;
import com.example.socialgifts.Product;
import com.example.socialgifts.R;
import com.example.socialgifts.WishList;

public class CreateWishlistActivity extends AppCompatActivity {
    private EditText newWishlistEditText;
    private Button createWishlistButton;
    private EditText newWishlistDescriptionEditText;
    private DatePicker datePicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_wishlist);
        newWishlistEditText = findViewById(R.id.wishlist_new_name);
        newWishlistDescriptionEditText = findViewById(R.id.wishlist_new_description);
        datePicker = findViewById(R.id.wishlist_new_end_date);

        createWishlistButton = findViewById(R.id.wishlist_new_create_button);
        createWishlistButton.setOnClickListener(view -> {
                if(productCorrect()) {
                    makePost();
                    Intent intent = new Intent(this, MainActivity.class);
                    startActivity(intent);
                }
        });
    }

    private void goBack() {
        finishActivity(R.layout.activity_create_wishlist);
    }
    private void makePost(){
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        String accessToken = sharedPreferences.getString("accessToken", "");

        String name = newWishlistEditText.getText().toString();
        String description = newWishlistDescriptionEditText.getText().toString();
        String data = datePicker.toString();

        WishList product = new WishList(name, description,data);

        ApiCalls apiCalls = new ApiCalls(this);
        apiCalls.createWishList(accessToken,product,this);
    }
    private boolean productCorrect(){
        String name = newWishlistEditText.getText().toString();
        String description = newWishlistDescriptionEditText.getText().toString();
        String data = datePicker.toString();
        return !name.isEmpty() && !description.isEmpty() && !data.isEmpty();
    }
}