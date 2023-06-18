package com.example.socialgifts.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
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
    private TextView wishlistDescriptionTextView;
    private TextView wishlistEndDateTextView;

    private Button back;
    private ArrayList<Product> products;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wishlist_friend);

        wishlistNameTextView = findViewById(R.id.wishlist_activity_name_friend);
        wishlistUserNameTextView = findViewById(R.id.wishlist_activity_user_name_friend);
        wishlistDescriptionTextView = findViewById(R.id.wishlist_activity_description_friend);
        wishlistEndDateTextView = findViewById(R.id.wishlist_activity_end);
        back = findViewById(R.id.wishlist_activity_back_friend);

        Intent intent = getIntent();
        String name = intent.getStringExtra("name");
        int id = intent.getIntExtra("id",0);
        int user_id = intent.getIntExtra("user_id",0);
        String end_date = intent.getStringExtra("end_date");
        String description = intent.getStringExtra("description");



        String nameUser = intent.getStringExtra("nameUser");
        String last_nameUser = intent.getStringExtra("lastname");

        wishlistNameTextView.setText(name);
        wishlistUserNameTextView.setText("Created by: " + nameUser + " "+ last_nameUser);
        wishlistDescriptionTextView.setText(description);
        wishlistEndDateTextView.setText("End date: " + end_date);

        WishProductFragmentFriend wishProductFragmentFriend = new WishProductFragmentFriend();
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.fragment_container_friend, wishProductFragmentFriend).commit();

        back.setOnClickListener(v -> {
            finish();
        });
    }
}
