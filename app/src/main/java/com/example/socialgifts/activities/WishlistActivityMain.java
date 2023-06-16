package com.example.socialgifts.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.example.socialgifts.Product;
import com.example.socialgifts.R;
import com.example.socialgifts.fragments.WishProductFragmentMain;

import java.util.ArrayList;

public class WishlistActivityMain extends AppCompatActivity {
    private TextView wishlistNameTextView;
    private TextView wishlistUserNameTextView;
    private TextView wishlistNumGiftsTextView;
    private TextView wishlistDescriptionTextView;

    private Button button;
    private ArrayList<Product> products;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wishlist_main);

        wishlistNameTextView = findViewById(R.id.wishlist_activity_name);
        wishlistUserNameTextView = findViewById(R.id.wishlist_activity_user_name);
        wishlistNumGiftsTextView = findViewById(R.id.wishlist_activity_num_gifts);
        wishlistDescriptionTextView = findViewById(R.id.wishlist_activity_description);
        button = findViewById(R.id.edit_button);

        Intent intent = getIntent();
        String name = intent.getStringExtra("name");
        int id = intent.getIntExtra("id",0);
        int user_id = intent.getIntExtra("user_id",0);
        String creation_date = intent.getStringExtra("creation_date");
        String description = intent.getStringExtra("description");



        wishlistNameTextView.setText(name);
        wishlistUserNameTextView.setText("Created by: " + user_id);
        wishlistNumGiftsTextView.setText("Created on: " + creation_date);
        wishlistDescriptionTextView.setText(description);

        WishProductFragmentMain wishProductFragmentMain = new WishProductFragmentMain();
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.fragment_container, wishProductFragmentMain).commit();

        button.setOnClickListener(view -> {
            Intent intent1 = new Intent(this, WishlistEditActivity.class);
            intent1.putExtra("name", name);
            intent1.putExtra("id", id);
            intent1.putExtra("creation_date", creation_date);
            intent1.putExtra("description", description);


            startActivity(intent1);
        });
    }


}