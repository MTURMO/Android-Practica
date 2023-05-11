package com.example.socialgifts;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class Login extends AppCompatActivity {

    EditText userNameEditText;
    EditText passwordEditText;
    Button logInButton;
    TextView registerTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        userNameEditText = findViewById(R.id.login_user_name);
        passwordEditText = findViewById(R.id.login_password);
        logInButton = findViewById(R.id.login_button_access);
        registerTextView = findViewById(R.id.login_button_register);

        logInButton.setOnClickListener(view -> makePost());
        registerTextView.setOnClickListener(view -> goToRegister());
    }

    private void makePost(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    private void goToRegister(){
        Intent intent = new Intent(this, Register.class);
        startActivity(intent);
    }
}