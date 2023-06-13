package com.example.socialgifts.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Response;
import com.example.socialgifts.ApiCalls;
import com.example.socialgifts.R;
import com.example.socialgifts.User;

import org.json.JSONArray;
import org.json.JSONObject;

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
        return !userName.isEmpty() && !password.isEmpty();
    }
    private void makePost(){
        String userName = userNameEditText.getText().toString();
        String password = passwordEditText.getText().toString();

        User user = new User(userName, password);

        ApiCalls apiCalls = new ApiCalls(this);
        apiCalls.loginUser(user,this);

        SharedPreferences sharedPreferences = this.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);

        String accessToken = sharedPreferences.getString("accessToken", "");

        apiCalls.searchUser(accessToken,userName, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                getUserId(response,userName);}
        });

        goToMain();
    }

    private void goToRegister(){
        Intent intent = new Intent(this, Register.class);
        startActivity(intent);
    }

    public void goToMain(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    private void getUserId(JSONArray response, String email){
        for (int i = 0; i < response.length(); i++) {
            try {
                JSONObject categoryObject = response.getJSONObject(i);
                int id = categoryObject.getInt("id");
                String emailAux = categoryObject.getString("email");

                if (emailAux.equals(email)) {
                    SharedPreferences sharedPreferences = this.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();

                    editor.putString("id", String.valueOf(id));
                    editor.apply();

                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


}