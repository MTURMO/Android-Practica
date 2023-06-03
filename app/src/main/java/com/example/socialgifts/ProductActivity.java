package com.example.socialgifts;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.socialgifts.activities.MainActivity;

public class ProductActivity extends AppCompatActivity {

    private TextView textViewName;
    private TextView textViewcategory;
    private TextView textViewprice;
    private TextView textViewdescription;
    private Button buttonCreateGift;
    private AlertDialog selectWishList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);
        textViewName = findViewById(R.id.product_name);
        textViewcategory = findViewById(R.id.product_category);
        textViewprice = findViewById(R.id.product_price);
        textViewdescription = findViewById(R.id.product_description);
        buttonCreateGift = findViewById(R.id.product_create_gift);
        buildDialog(this);
        buttonCreateGift.setOnClickListener(view -> selectWishList.show());
    }

    private void buildDialog(Context context){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view = getLayoutInflater().inflate(R.layout.category_pop_up, null);

        builder.setView(view);
        builder.setTitle("Choose a wish list")
                .setPositiveButton("OK", (dialogInterface, i) -> {
                    Intent intent = new Intent(context, MainActivity.class);
                    startActivity(intent);
                })
                .setNegativeButton("Cancel", (dialogInterface, i) -> {

                });
        selectWishList = builder.create();
    }
}