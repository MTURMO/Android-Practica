package com.example.socialgifts.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.socialgifts.ApiCalls;
import com.example.socialgifts.Product;
import com.example.socialgifts.R;
import com.example.socialgifts.fragments.WishProductFragmentMain;


import java.util.ArrayList;

public class WishlistActivityMain extends AppCompatActivity {
    private TextView wishlistNameTextView;
    private TextView wishlistUserNameTextView;
    private TextView wishlistEndDateTextView;
    private TextView wishlistDescriptionTextView;

    private Button button,deleteButton;
    private ImageButton back;
    private ArrayList<Product> products;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wishlist_main);

        wishlistNameTextView = findViewById(R.id.wishlist_activity_name);
        wishlistUserNameTextView = findViewById(R.id.wishlist_activity_user_name);
        wishlistEndDateTextView = findViewById(R.id.wishlist_activity_end_main);
        wishlistDescriptionTextView = findViewById(R.id.wishlist_activity_description);
        button = findViewById(R.id.edit_button);
        deleteButton = findViewById(R.id.delete_button);
        back = findViewById(R.id.wish_main_back_button);

        Intent intent = getIntent();
        String name = intent.getStringExtra("name");

        int id = intent.getIntExtra("id",0);
        int user_id = intent.getIntExtra("user_id",0);
        String end_date = intent.getStringExtra("end_date");
        String description = intent.getStringExtra("description");

        SharedPreferences sharedPreferences = this.getSharedPreferences("MyPrefs", MODE_PRIVATE);
        String nameUser = sharedPreferences.getString("name", "");
        String last_nameUser = sharedPreferences.getString("lastname", "");
        String accessToken = sharedPreferences.getString("accessToken", "");

        wishlistNameTextView.setText(name);
        wishlistUserNameTextView.setText("Created by: " + nameUser + " "+ last_nameUser);
        wishlistDescriptionTextView.setText(description);
        wishlistEndDateTextView.setText("End date: " + end_date);

        WishProductFragmentMain wishProductFragmentMain = new WishProductFragmentMain();
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.fragment_container, wishProductFragmentMain).commit();

        button.setOnClickListener(view -> {
            Intent intent1 = new Intent(this, WishlistEditActivity.class);
            intent1.putExtra("name", name);
            intent1.putExtra("id", id);
            intent1.putExtra("end_date", end_date);
            intent1.putExtra("description", description);

            startActivity(intent1);
        });
        deleteButton.setOnClickListener(view -> {
            ApiCalls apiCalls = new ApiCalls(this);
            apiCalls.deleteWishlistById(accessToken, id, this, new Response.Listener() {
                @Override
                public void onResponse(Object response) {
                    Toast.makeText(getApplicationContext(), "Llista eliminada", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(getApplicationContext(), "Per poder eliminar-la, ha d'estar buida", Toast.LENGTH_SHORT).show();
                }
            });
        }

        );
        back.setOnClickListener(view -> {
            finish();
        });
    }


}