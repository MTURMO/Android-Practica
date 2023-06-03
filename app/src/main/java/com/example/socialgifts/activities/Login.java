package com.example.socialgifts.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.socialgifts.ApiCalls;
import com.example.socialgifts.R;
import com.example.socialgifts.User;

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

        logInButton.setOnClickListener(view ->{
            if(userCorrect()){
                makePost();
            }
        });
        registerTextView.setOnClickListener(view -> goToRegister());
    }


    private boolean userCorrect(){
        String userName = userNameEditText.getText().toString();
        String password = passwordEditText.getText().toString();
        if(!userName.isEmpty()  && !password.isEmpty()){
            return true;
        } else {
            return false;
        }
    }
    private void makePost(){
        String userName = userNameEditText.getText().toString();
        String password = passwordEditText.getText().toString();

        User user = new User(null, null, userName, password, "");

        ApiCalls apiCalls = new ApiCalls(this);
        apiCalls.loginUser(user);

        if(apiCalls.getAccessToken()!=null){
            goToMain();
        }

    }

    private void goToRegister(){
        Intent intent = new Intent(this, Register.class);
        startActivity(intent);
    }
    private void goToMain(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }



}