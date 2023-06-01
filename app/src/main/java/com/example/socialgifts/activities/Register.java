package com.example.socialgifts.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.socialgifts.R;

public class Register extends AppCompatActivity {

    EditText userNameEditText;
    EditText userLastNameEditText;
    EditText emailEditText;
    EditText passwordEditText;
    Button registerButton;
    TextView logInTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        userNameEditText = findViewById(R.id.register_user_name);
        userLastNameEditText = findViewById(R.id.register_user_last_name);
        emailEditText = findViewById(R.id.register_mail);
        passwordEditText = findViewById(R.id.register_password);
        registerButton = findViewById(R.id.register_button_signup);
        logInTextView = findViewById(R.id.register_button_login);
        registerButton.setOnClickListener(view -> makePost());
        logInTextView.setOnClickListener(view -> goToLogin());
    }

    private void makePost(){
        Intent intent = new Intent(this, Login.class);
        startActivity(intent);
    }

    private void goToLogin(){
        Intent intent = new Intent(this, Login.class);
        startActivity(intent);
    }
}