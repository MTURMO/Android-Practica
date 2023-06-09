package com.example.socialgifts;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

public class CreateWishlistActivity extends AppCompatActivity {
    private EditText newWishlistEditText;
    private Button createWishlistButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_wishlist);
        newWishlistEditText = findViewById(R.id.wishlist_new);
        createWishlistButton = findViewById(R.id.wishlist_new_create_button);
        createWishlistButton.setOnClickListener(view -> goBack());
    }

    private void goBack() {
        finishActivity(R.layout.activity_create_wishlist);
    }
}