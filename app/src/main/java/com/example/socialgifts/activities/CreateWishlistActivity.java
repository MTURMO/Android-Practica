package com.example.socialgifts.activities;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.socialgifts.ApiCalls;
import com.example.socialgifts.R;
import com.example.socialgifts.WishList;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class CreateWishlistActivity extends BaseAcivity {
    private EditText newWishlistEditText;
    private Button createWishlistButton;
    private ImageButton backButton;

    private EditText newWishlistDescriptionEditText;
    private EditText endDateYear, endDateMonth, endDateDay, endDate;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_wishlist);
        newWishlistEditText = findViewById(R.id.wishlist_new_name);
        newWishlistDescriptionEditText = findViewById(R.id.wishlist_new_description);
        endDateYear = findViewById(R.id.editTextDateYear);
        endDateMonth = findViewById(R.id.editTextDateMonth);
        endDateDay = findViewById(R.id.editTextDateDay);
        backButton = findViewById(R.id.create_wish_back_button);
        createWishlistButton = findViewById(R.id.wishlist_new_create_button);
        createWishlistButton.setOnClickListener(view -> {
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

    private void makePost(){
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        String accessToken = sharedPreferences.getString("accessToken", "");

        String name = newWishlistEditText.getText().toString();
        String description = newWishlistDescriptionEditText.getText().toString();
        String year = endDateYear.getText().toString();
        String month = endDateMonth.getText().toString();
        String day = endDateDay.getText().toString();

        if(dataCorrecta(year,month,day)){
            String data = year+"-"+month+"-"+day+"T00:00:00.000Z";

            WishList product = new WishList(name, description,data);

            ApiCalls apiCalls = new ApiCalls(this);
            apiCalls.createWishList(accessToken,product,this);
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        } else{
            Toast.makeText(this, "Formato de fecha incorrecto(yyyy-MM-dd) o fecha pasada", Toast.LENGTH_SHORT).show();
        }

    }
    private boolean productCorrect(){
        String name = newWishlistEditText.getText().toString();
        String description = newWishlistDescriptionEditText.getText().toString();
        String year = endDateYear.getText().toString();
        String month = endDateMonth.getText().toString();
        String day = endDateDay.getText().toString();

        String data = year+"-"+month+"-"+day+"T00:00:00.000Z";

        return !name.isEmpty() && !description.isEmpty() && !data.isEmpty();
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