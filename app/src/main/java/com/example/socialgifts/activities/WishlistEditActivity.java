package com.example.socialgifts.activities;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;


import com.example.socialgifts.ApiCalls;
import com.example.socialgifts.R;
import com.example.socialgifts.WishList;

import java.util.Calendar;
import java.util.Date;

public class WishlistEditActivity extends BaseAcivity  {
        private EditText newWishlistEditText;
        private Button editarWishlistButton;
        private ImageButton backButton;
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
            backButton = findViewById(R.id.edit_wish_back_button);

            Intent intent = getIntent();
            int id = intent.getIntExtra("id",0);
            String name = intent.getStringExtra("name");
            String description = intent.getStringExtra("description");
            String data = intent.getStringExtra("end_date");

            if (data != null && data.contains("T")) {
                data = data.substring(0, data.indexOf("T"));
            }
            newWishlistEditText.setText(name);
            newWishlistDescriptionEditText.setText(description);
            datePicker.setText(data);

            editarWishlistButton = findViewById(R.id.wishlist_edit_button);
            editarWishlistButton.setOnClickListener(view -> {
                    if(productCorrect()) {
                        makePost();

                    } else{
                        Toast.makeText(this, "Complete los campos correctamente.", Toast.LENGTH_SHORT).show();
                    }


            });
            backButton.setOnClickListener(view -> {
               finish();
            });
        }

    private boolean productCorrect(){
        String name = newWishlistEditText.getText().toString();
        String description = newWishlistDescriptionEditText.getText().toString();
        String data = datePicker.getText().toString();


        return !name.isEmpty() && !description.isEmpty() && !data.isEmpty();
    }
        private void makePost(){
            SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
            String accessToken = sharedPreferences.getString("accessToken", "");

            String name = newWishlistEditText.getText().toString();
            String description = newWishlistDescriptionEditText.getText().toString();
            String data = datePicker.getText().toString();

            String[] parts = data.split("-");

            if(parts.length == 3) {

                String year = parts[0];
                String month = parts[1];
                String day = parts[2];

                if (dataCorrecta(year, month, day)) {
                    data = year + "-" + month + "-" + day + "T00:00:00.000Z";

                    Intent intent = getIntent();
                    int id = intent.getIntExtra("id",0);

                    WishList product = new WishList(name, description,data);
                    ApiCalls apiCalls = new ApiCalls(this);
                    apiCalls.editWishlist(accessToken,id,product,this);

                    Intent intent2 = new Intent(this, MainActivity.class);
                    startActivity(intent2);

                } else {
                    Toast.makeText(this, "Fecha invalida o pasada.", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(this, "Formato incorrecto", Toast.LENGTH_SHORT).show();
            }
        }

    private boolean dataCorrecta(String year, String month, String day){
        String yearPattern = "\\d{4}";
        String monthPattern = "\\d{2}";
        String dayPattern = "\\d{2}";

        if(!year.matches(yearPattern) || !month.matches(monthPattern) || !day.matches(dayPattern)){
            return false;
        }

        int yearInt = Integer.parseInt(year);
        int monthInt = Integer.parseInt(month);
        int dayInt = Integer.parseInt(day);

        Calendar calendar = Calendar.getInstance();
        calendar.setLenient(false);
        calendar.set(Calendar.YEAR, yearInt);
        calendar.set(Calendar.MONTH, monthInt-1);
        calendar.set(Calendar.DAY_OF_MONTH, dayInt);
        try{
            Date input =  calendar.getTime();
            Date actual = new Date();
            if(input.before(actual)){
                return false;
            } else{

            }
        } catch (Exception e){
            return false;
        }
        return true;
    }


}
