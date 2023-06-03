package com.example.socialgifts.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.example.socialgifts.R;

public class CreateProductActivity extends AppCompatActivity {
    private EditText editTextName;
    private EditText editTextDescription;
    private EditText editTextProductPrice;
    private ImageButton buttonTakePic;
    private Button buttonCreate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_product);

        editTextName = findViewById(R.id.create_product_name);
        editTextDescription = findViewById(R.id.create_product_description);
        editTextProductPrice = findViewById(R.id.create_product_price);
        buttonTakePic = findViewById(R.id.create_product_take_pic);
        buttonCreate = findViewById(R.id.create_product_create);

        buttonCreate.setOnClickListener(view -> makePost());
    }

    private void makePost(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}