package com.example.socialgifts.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import com.example.socialgifts.Product;
import com.example.socialgifts.R;
import com.example.socialgifts.fragments.WishProductFragmentFriend;

import java.util.ArrayList;

public class WishlistActivityFriend extends AppCompatActivity {
    private TextView wishlistNameTextView;
    private TextView wishlistUserNameTextView;
    private TextView wishlistNumGiftsTextView;
    private TextView wishlistDescriptionTextView;

    private ArrayList<Product> products;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wishlist_friend);

        wishlistNameTextView = findViewById(R.id.wishlist_activity_name_friend);
        wishlistUserNameTextView = findViewById(R.id.wishlist_activity_user_name_friend);
        wishlistNumGiftsTextView = findViewById(R.id.wishlist_activity_num_gifts_friend);
        wishlistDescriptionTextView = findViewById(R.id.wishlist_activity_description_friend);


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

        WishProductFragmentFriend wishProductFragmentFriend = new WishProductFragmentFriend();
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.fragment_container_friend, wishProductFragmentFriend).commit();


    }
}
