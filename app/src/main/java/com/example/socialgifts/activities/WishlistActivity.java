package com.example.socialgifts.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.TextView;

import com.example.socialgifts.Product;
import com.example.socialgifts.R;
import com.example.socialgifts.adapters.FeedAdapter;

import java.util.ArrayList;
import java.util.List;

public class WishlistActivity extends AppCompatActivity {
    private TextView wishlistNameTextView;
    private TextView wishlistUserNameTextView;
    private TextView wishlistNumGiftsTextView;
    private TextView wishlistDescriptionTextView;
    private FeedAdapter feedAdapter;
    private ArrayList<Product> products;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wishlist);
        wishlistNameTextView = findViewById(R.id.wishlist_activity_name);
        wishlistUserNameTextView = findViewById(R.id.wishlist_activity_user_name);
        wishlistNumGiftsTextView = findViewById(R.id.wishlist_activity_num_gifts);
        wishlistDescriptionTextView = findViewById(R.id.wishlist_activity_description);

        RecyclerView recyclerView = findViewById(R.id.wishlist_activity_recycle);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 1));
        feedAdapter = new FeedAdapter(products, this, "all");
        recyclerView.setAdapter(feedAdapter);
    }
}