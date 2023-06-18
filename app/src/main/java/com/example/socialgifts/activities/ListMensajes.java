package com.example.socialgifts.activities;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.socialgifts.ApiCalls;
import com.example.socialgifts.R;
import com.example.socialgifts.User;
import com.example.socialgifts.adapters.XatsAdapter;

import java.util.ArrayList;
import java.util.List;

public class ListMensajes extends BaseAcivity {

    private Button back;
    private XatsAdapter xatsAdapter;

    private final List<User> users = new ArrayList<>();
    private RecyclerView recyclerView;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.conversaciones);

        back = findViewById(R.id.back_button_convers);
        back.setOnClickListener(view-> {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        });

        recyclerView = findViewById(R.id.conv_recycle_view_main);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 1));
        xatsAdapter = new XatsAdapter(users, this);
        recyclerView.setAdapter(xatsAdapter);

        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        String accessToken = sharedPreferences.getString("accessToken", "");
        ApiCalls apiCalls = new ApiCalls(this, xatsAdapter);
        apiCalls.getUsersMessaged(accessToken,this);
    }
}
