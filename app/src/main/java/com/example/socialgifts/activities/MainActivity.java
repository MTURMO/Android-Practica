package com.example.socialgifts.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.socialgifts.R;
import com.example.socialgifts.fragments.FeedFragment;
import com.example.socialgifts.fragments.FriendsFragment;
import com.example.socialgifts.fragments.WishListFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
    private FeedFragment feedFragment = new FeedFragment();
    private FriendsFragment friendsFragment = new FriendsFragment();
    private WishListFragment wishListFragment = new WishListFragment();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView navigationView = findViewById(R.id.main_bottom_nav_bar);
        navigationView.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener);
        loadFragment(feedFragment);
    }

    private final BottomNavigationView.OnNavigationItemSelectedListener onNavigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()){
                case R.id.main_bottom_nav_bar_feed:
                    loadFragment(feedFragment);
                    return true;
                case R.id.main_bottom_nav_bar_whishlist:
                    loadFragment(wishListFragment);
                    return true;
                case R.id.main_bottom_nav_bar_friends:
                    loadFragment(friendsFragment);
                    return true;
            }
            return false;
        }
    };

    private void loadFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.main_fragment_container, fragment);
        transaction.commit();
    }
}