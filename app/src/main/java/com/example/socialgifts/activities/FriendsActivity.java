package com.example.socialgifts.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import com.bumptech.glide.Glide;
import com.example.socialgifts.ApiCalls;
import com.example.socialgifts.R;
import com.example.socialgifts.fragments.ChatFragment;
import com.example.socialgifts.fragments.FriendWishlistFragment;
import com.example.socialgifts.fragments.UserWishlistFragment;

public class FriendsActivity extends AppCompatActivity {

        private TextView textViewName;
        public TextView last_name;
        public ImageView image;
        private ImageButton back;
        private ImageButton chat;
        private Button follow;
        private boolean isFollowed = false;
        private SharedPreferences sharedPreferences;

        @SuppressLint("MissingInflatedId")
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.user_perfil);

            image = findViewById(R.id.user_image);
            textViewName = findViewById(R.id.user_name);
            last_name = findViewById(R.id.user_last_name);
            back = findViewById(R.id.back);
            chat = findViewById(R.id.user_activity_chat);
            follow = findViewById(R.id.user_activity_follow);

            Intent intent = getIntent();
            String name = intent.getStringExtra("name");
            String lastname = intent.getStringExtra("last_name");
            String image = intent.getStringExtra("image");
            int id = intent.getIntExtra("id",0);

            textViewName.setText(name);
            last_name.setText(lastname);

            Glide.with(this).load(image).error(R.drawable.ic_launcher_foreground).into(this.image);


            FriendWishlistFragment userWishlistFragment = new FriendWishlistFragment();
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.fragment_container_user, userWishlistFragment)
                    .commit();

            back.setOnClickListener(view -> goToFeed());
            chat.setOnClickListener(view ->{
                Intent intent2 = new Intent(this, ChatActivity.class);
                intent2.putExtra("name", intent.getStringExtra("name"));
                intent2.putExtra("last_name", intent.getStringExtra("last_name"));
                intent2.putExtra("image", intent.getStringExtra("image"));
                intent2.putExtra("id", intent.getIntExtra("id", 0));
                startActivity(intent2);});
            follow.setOnClickListener(view -> {
                if(!isFollowed){
                    requestPost();
                }
            });

            updateFollowButton();
        }
        private void updateFollowButton() {
            if (isFollowed ) {
                follow.setText("Solicitado");
                follow.setBackgroundColor(getResources().getColor(android.R.color.darker_gray));
            }
        }
        private void requestPost() {


            sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
            String accesToken = sharedPreferences.getString("accessToken", "");

            Log.e("requestPost", String.valueOf(getIntent().getIntExtra("id", 0)));
            ApiCalls apiCalls = new ApiCalls(this);
            apiCalls.createFriendRequest(accesToken, getIntent().getIntExtra("id", 0),follow,this);
        }

        private void goToChat() {
            Intent intent = new Intent(this, ChatFragment.class);
            intent.putExtra("name", textViewName.getText().toString());
            intent.putExtra("last_name", last_name.getText().toString());
            intent.putExtra("image", image.toString());
            intent.putExtra("id", 0);
            startActivity(intent);
        }

        private void goToFeed() {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }


}
