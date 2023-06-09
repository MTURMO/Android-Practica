package com.example.socialgifts;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.socialgifts.activities.MainActivity;

public class UsersActivity extends AppCompatActivity {

    private TextView textViewName;
    public TextView last_name;
    public ImageView image;
    private Button back;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        image = findViewById(R.id.user_image);
        textViewName = findViewById(R.id.user_name);
        last_name = findViewById(R.id.user_last_name);
        back = findViewById(R.id.back);


        Intent intent = getIntent();
        String name = intent.getStringExtra("name");
        String lastname = intent.getStringExtra("last_name");
        String image = intent.getStringExtra("image");

        textViewName.setText(name);
        last_name.setText(lastname);
        Glide.with(this).load(image).error(R.drawable.ic_launcher_foreground).into(this.image);

        back.setOnClickListener(view -> goToFeed());
    }

    private void goToFeed() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

}