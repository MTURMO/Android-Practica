package com.example.socialgifts.activities;


import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.socialgifts.ApiCalls;
import com.example.socialgifts.R;
import com.example.socialgifts.User;

public class Register extends BaseAcivity {

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

        registerButton.setOnClickListener(view -> {
            if(userCorrect()){
                makePost();
            }
        });
        logInTextView.setOnClickListener(view -> goToLogin());
    }


    private boolean userCorrect(){
        //String emailRegex = "^[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]";
        String userName = userNameEditText.getText().toString();
        String password = passwordEditText.getText().toString();
        String email = emailEditText.getText().toString();
        String userLastName = userLastNameEditText.getText().toString();

        return !userName.isEmpty() /*&& email.matches(emailRegex)*/ && !password.isEmpty() && !email.isEmpty() && !userLastName.isEmpty();

    }
    private void makePost(){
        String userName = userNameEditText.getText().toString();
        String password = passwordEditText.getText().toString();
        String email = emailEditText.getText().toString();
        String userLastName = userLastNameEditText.getText().toString();

        User user = new User(userName, userLastName, email, password, "C:\\Users\\Ardiaca\\OneDrive - La Salle\\2n\\PrPr2\\repo\\Android-Practica\\app\\src\\main\\res\\drawable\\icon_feed.png");

        ApiCalls apiCalls = new ApiCalls(this);
        apiCalls.registerUser(user, this);

        goToLogin();
    }

    private void goToLogin(){
        Intent intent = new Intent(this, Login.class);
        startActivity(intent);
    }
}