package com.example.socialgifts.activities;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;


import androidx.appcompat.app.AppCompatActivity;

import com.example.socialgifts.ApiCalls;
import com.example.socialgifts.R;
import com.example.socialgifts.WishList;

public class WishlistEditActivity extends AppCompatActivity  {
        private EditText newWishlistEditText;
        private Button editarWishlistButton;
        private EditText newWishlistDescriptionEditText;
        private EditText datePicker;

        @SuppressLint("MissingInflatedId")
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_edit_wishlist);
            newWishlistEditText = findViewById(R.id.wishlist_new_name2);
            newWishlistDescriptionEditText = findViewById(R.id.wishlist_new_description2);
            datePicker = findViewById(R.id.wishlist_new_end_date2);

            Intent intent = getIntent();
            int id = intent.getIntExtra("id",0);
            String name = intent.getStringExtra("name");
            String description = intent.getStringExtra("description");
            String data = intent.getStringExtra("end_date");

            newWishlistEditText.setText(name);
            newWishlistDescriptionEditText.setText(description);
            datePicker.setText(data);

            editarWishlistButton = findViewById(R.id.wishlist_edit_button);
            editarWishlistButton.setOnClickListener(view -> {

                    makePost();
                    Intent intent2 = new Intent(this, MainActivity.class);
                    startActivity(intent2);

            });
        }

        private void makePost(){
            SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
            String accessToken = sharedPreferences.getString("accessToken", "");

            String name = newWishlistEditText.getText().toString();
            String description = newWishlistDescriptionEditText.getText().toString();
            String data = datePicker.toString();

            Intent intent = getIntent();
            int id = intent.getIntExtra("id",0);

            WishList product = new WishList(name, description,data);
            ApiCalls apiCalls = new ApiCalls(this);
            apiCalls.editWishlist(accessToken,id,product,this);
        }


}
