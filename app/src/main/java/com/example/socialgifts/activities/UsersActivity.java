package com.example.socialgifts.activities;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.socialgifts.R;
import com.example.socialgifts.activities.MainActivity;

public class UsersActivity extends AppCompatActivity {

    private TextView textViewName;
    public TextView last_name;
    public ImageView image;
    private ImageButton back;
    private ImageButton chat;
    private Button follow;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        image = findViewById(R.id.user_image);
        textViewName = findViewById(R.id.user_name);
        last_name = findViewById(R.id.user_last_name);
        back = findViewById(R.id.back);
        chat = findViewById(R.id.user_activity_chat);
        follow = findViewById(R.id.user_activity_follow);

        Intent intent = getIntent();
        String name = intent.getStringExtra("name");
        String lastname = intent.getStringExtra("last_name");
        String image = intent.getStringExtra("image");

        textViewName.setText(name);
        last_name.setText(lastname);
        Glide.with(this).load(image).error(R.drawable.ic_launcher_foreground).into(this.image);

        back.setOnClickListener(view -> goToFeed());
        chat.setOnClickListener(view -> goToChat());
        follow.setOnClickListener(view -> requestPost());
    }

    private void requestPost() {
    }

    private void goToChat() {

    }

    private void goToFeed() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

}