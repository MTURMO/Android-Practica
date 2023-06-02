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

        logInButton.setOnClickListener(view -> makePost());
        registerTextView.setOnClickListener(view -> goToRegister());
        setupLoginButton();
    }

    private void makePost(){
        String userName = userNameEditText.getText().toString();
        String password = passwordEditText.getText().toString();

        User user = new User(userName, null, null, password, "");

        ApiCalls apiCalls = new ApiCalls(this);
        apiCalls.loginUser(user,this);

    }

    private void goToRegister(){
        Intent intent = new Intent(this, Register.class);
        startActivity(intent);
    }

    private void setupLoginButton(){
        logInButton.setEnabled(false);

        TextWatcher textWatcher = new TextWatcher(){

                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    logInButton.setEnabled(false);
                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    String userName = userNameEditText.getText().toString().trim();
                    String password = passwordEditText.getText().toString().trim();
                    logInButton.setEnabled(!userName.isEmpty() && !password.isEmpty());
                }

                @Override
                public void afterTextChanged(android.text.Editable editable) {
                }
            };
        userNameEditText.addTextChangedListener(textWatcher);
        passwordEditText.addTextChangedListener(textWatcher);
        }

}