package com.example.socialgifts.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;

import com.android.volley.Response;
import com.bumptech.glide.Glide;
import com.example.socialgifts.ApiCalls;
import com.example.socialgifts.R;
import com.example.socialgifts.User;
import com.example.socialgifts.activities.FriendsActivity;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class FriendsAdapter extends RecyclerView.Adapter<FriendsAdapter.ViewHolder>{


    private List<User> users;
    private final Context context;
    private  String searchUserName;
    boolean trobat = false;

    public FriendsAdapter(List<User> users, Context context, String searchUserName){
        if(users==null){
            this.users=new ArrayList<>();
        } else{
            this.users = users;

        }
        this.context = context;
        this.searchUserName = searchUserName;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setUser(List<User> user){
        this.users=user;
        notifyDataSetChanged();
    }
    public void setString(String string) {
        this.searchUserName = string;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.feed_user_card, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        User user = users.get(position);

        boolean match = searchUserName.equals("all") || user.getName().toLowerCase().contains(searchUserName.toLowerCase())
                || user.getLast_name().toLowerCase().contains(searchUserName.toLowerCase())
                || user.getEmail().toLowerCase().contains(searchUserName.toLowerCase());

        if(match){
                holder.itemView.setVisibility(View.VISIBLE);
                holder.userName.setText(user.getName());
                holder.email.setText(user.getEmail());
                Glide.with(context).load(user.getImage()).error(R.drawable.imagemissing_92832).into(holder.userImage);
        }else{
            holder.itemView.setVisibility(View.GONE);
        }
        holder.userName.setText(user.getName());
        holder.email.setText(user.getEmail());


        holder.itemView.setOnClickListener(view -> {
            ApiCalls apiCalls = new ApiCalls(this);
            SharedPreferences sharedPreferences = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
            apiCalls.getFriends2(sharedPreferences.getString("accessToken", ""), context,new Response.Listener<JSONArray>()  {

                @Override
                public void onResponse(JSONArray response) {
                    int[] isFollowed = new int[1];
                    isFollowed[0] = 0;

                    try {
                        for(int i = 0; i < response.length(); i++){
                            JSONObject jsonObject = response.getJSONObject(i);
                            if(jsonObject.getInt("id") == user.getId()){
                                isFollowed[0] = 1;
                                break;
                            }
                        }



                                Intent intent = new Intent(context, FriendsActivity.class);
                                intent.putExtra("id", user.getId());
                                intent.putExtra("name", user.getName());
                                intent.putExtra("last_name", user.getLast_name());
                                intent.putExtra("image", user.getImage());
                                intent.putExtra("isFollowed", isFollowed[0]);
                                context.startActivity(intent);


                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    });
}

    @Override
    public int getItemCount() {
        return users.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView userName;
        TextView email;
        ImageView userImage;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            userName = itemView.findViewById(R.id.feed_user_card_name);
            email = itemView.findViewById(R.id.feed_user_card_email);
            userImage = itemView.findViewById(R.id.feed_user_card_image);
        }
    }
}