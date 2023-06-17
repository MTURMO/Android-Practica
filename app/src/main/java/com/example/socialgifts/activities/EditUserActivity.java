package com.example.socialgifts.activities;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.socialgifts.ApiCalls;
import com.example.socialgifts.R;
import com.example.socialgifts.User;
import com.example.socialgifts.WishList;

import java.nio.Buffer;

public class EditUserActivity extends AppCompatActivity {

    private EditText name, last_name, email, photo,password;
    private Button edit;
    private Button back;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_user_perfil);

        name = findViewById(R.id.main_new_firstname2);
        last_name = findViewById(R.id.main_new_lastname2);
        password = findViewById(R.id.main_pass);
        email = findViewById(R.id.main_new_email2);
        photo = findViewById(R.id.main_new_photo_link2);
        edit = findViewById(R.id.user_edit_button);

        Intent intent = getIntent();
        int id = intent.getIntExtra("id",0);
        String name2 = intent.getStringExtra("name");
        String lastName = intent.getStringExtra("last_name");
        String email2 = intent.getStringExtra("email");
        String photo2 = intent.getStringExtra("image");
        String password2 = intent.getStringExtra("password");

        name.setText(name2);
        last_name.setText(lastName);
        email.setText(email2);
        photo.setText(photo2);
        password.setText(password2);



        edit.setOnClickListener(view -> {
            makePost();


        });
        back = findViewById(R.id.user_back_button);
        back.setOnClickListener(view -> {
            Intent intent2 = new Intent(this, MainActivity.class);
            startActivity(intent2);

        });
    }

    private void makePost(){
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        String accessToken = sharedPreferences.getString("accessToken", "");

        String name2 = name.getText().toString();
        String lastname = last_name.getText().toString();
        String email2 = email.getText().toString();
        String photo2 = photo.getText().toString();
        String password2 = password.getText().toString();


        User user = new User(name2,lastname,email2,password2,photo2);

        ApiCalls apiCalls = new ApiCalls(this);
        apiCalls.editUser(accessToken,user,this);

    }

}
