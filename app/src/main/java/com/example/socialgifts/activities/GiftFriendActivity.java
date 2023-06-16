package com.example.socialgifts.activities;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Response;
import com.bumptech.glide.Glide;
import com.example.socialgifts.ApiCalls;
import com.example.socialgifts.R;

import org.json.JSONObject;

public class GiftFriendActivity extends AppCompatActivity {

        private TextView textViewName;
        private TextView textViewcategory;
        private TextView textViewprice;
        private TextView textViewdescription;
        public ImageView image;
        private int idProduct;

        private Button reserva;



        @SuppressLint({"MissingInflatedId", "SetTextI18n"})
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.gift_view_friend);
            image = findViewById(R.id.product_image_friend);
            textViewName = findViewById(R.id.product_name_friend);
            textViewcategory = findViewById(R.id.product_category_friend);
            textViewprice = findViewById(R.id.product_price_friend);
            textViewdescription = findViewById(R.id.product_description_friend);
            reserva = findViewById(R.id.product_reserva_gift);

            Intent intent = getIntent();
            String name2 = intent.getStringExtra("name");
            String description2 = intent.getStringExtra("description");
            String image_url2 = intent.getStringExtra("image");
            int category = intent.getIntExtra("category", 0);
            float price = intent.getFloatExtra("price", 0);


            idProduct = intent.getIntExtra("id", 0);
            name2=name2!=null?name2:"null";
            description2=description2!=null?description2:"null";
            image_url2=image_url2!=null?image_url2:"null";

            textViewName.setText(name2);
            textViewcategory.setText(Integer.toString(category));
            textViewprice.setText(Float.toString(price));
            textViewdescription.setText(description2);
            Glide.with(this).load(image_url2).error(R.drawable.ic_launcher_foreground).into(this.image);

            SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
            String accessToken = sharedPreferences.getString("accessToken", "");




            reserva.setOnClickListener(view ->{
                    Intent intent2 = new Intent(this, MainActivity.class);
                    startActivity(intent2);
            });



        }




}
