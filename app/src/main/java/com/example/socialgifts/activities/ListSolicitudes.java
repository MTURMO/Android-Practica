package com.example.socialgifts.activities;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.socialgifts.ApiCalls;
import com.example.socialgifts.R;
import com.example.socialgifts.User;
import com.example.socialgifts.adapters.SolicitudesAdapter;
import com.example.socialgifts.adapters.XatsAdapter;

import java.util.ArrayList;
import java.util.List;

public class ListSolicitudes extends AppCompatActivity {
    private Button accept,reject;
    private Button back;
    private SolicitudesAdapter solicitudesAdapter;

    private final List<User> users = new ArrayList<>();
    private final List<Integer> userIds = new ArrayList<>();
    private RecyclerView recyclerView;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_solicitudes);
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        String accessToken = sharedPreferences.getString("accessToken", "");

        accept = findViewById(R.id.button_accept);
        reject = findViewById(R.id.button_decline);

        recyclerView = findViewById(R.id.user_list_view2);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 1));
        solicitudesAdapter = new SolicitudesAdapter(users, this,userIds);
        recyclerView.setAdapter(solicitudesAdapter);


        ApiCalls apiCalls = new ApiCalls(this, solicitudesAdapter);
        apiCalls.getFriendsRequest(accessToken,this);

        back = findViewById(R.id.back2);
        back.setOnClickListener(v -> {
            finish();
        });

    }
}
