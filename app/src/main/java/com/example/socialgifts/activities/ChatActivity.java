package com.example.socialgifts.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.FrameLayout;

import com.example.socialgifts.R;
import com.example.socialgifts.fragments.ChatFragment;

public class ChatActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_chat);

        @SuppressLint({"MissingInflatedId", "LocalSuppress"})
        FrameLayout frameLayout = findViewById(R.id.fragment_chat);
        ChatFragment chatFragment = new ChatFragment();
        getSupportFragmentManager().beginTransaction()
                .add(R.id.fragment_chat, chatFragment).commit();
    }
}
