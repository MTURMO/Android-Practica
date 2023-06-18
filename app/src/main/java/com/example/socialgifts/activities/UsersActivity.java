package com.example.socialgifts.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import com.bumptech.glide.Glide;
import com.example.socialgifts.R;

public class UsersActivity extends AppCompatActivity {

    private TextView textViewName;
    public TextView last_name,email;
    public ImageView image;
    private Button logout;
    private Button edit;
    private Button back;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_user_perfil);

        image = findViewById(R.id.main_user_image);

        textViewName = findViewById(R.id.main_user_name);
        last_name = findViewById(R.id.main_user_last_name);
        email = findViewById(R.id.main_user_email);
        logout = findViewById(R.id.logout_button);
        edit = findViewById(R.id.user_edit);
        back = findViewById(R.id.back_user);
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        String name = sharedPreferences.getString("name", "");
        String lastname = sharedPreferences.getString("last_name", "");
        String email2 = sharedPreferences.getString("email", "");
        String image = sharedPreferences.getString("image","" );
        String id = sharedPreferences.getString("id", "");
        String pas = sharedPreferences.getString("password", "");

        textViewName.setText(name);
        last_name.setText(lastname);
        email.setText(email2);

        Glide.with(this).load(image).error(R.drawable.imagemissing_92832).into(this.image);



        logout.setOnClickListener(view -> {
            Intent intent1 = new Intent(this, Login.class);
            startActivity(intent1);
        });
        edit.setOnClickListener(view ->{
                Intent intent2 = new Intent(this, EditUserActivity.class);
                    intent2.putExtra("name", name);
                    intent2.putExtra("last_name", lastname);
                    intent2.putExtra("email", email2);
                    intent2.putExtra("image", image);
                    intent2.putExtra("password", pas);
                    intent2.putExtra("id", id);
                startActivity(intent2);});

        back.setOnClickListener(view -> {
          finish();
        });
    }





}