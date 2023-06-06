package com.example.socialgifts;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.socialgifts.activities.MainActivity;

public class UsersActivity extends AppCompatActivity {

    private TextView textViewName;
    private TextView email;
    private Button back;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        textViewName = findViewById(R.id.user_name);
        email = findViewById(R.id.user_email);
        back = findViewById(R.id.back);


        Intent intent = getIntent();
        String name = intent.getStringExtra("name");
        String description = intent.getStringExtra("email");

        textViewName.setText(name);
        email.setText(description);

        back.setOnClickListener(view -> goToFeed());
    }

    private void goToFeed() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

}