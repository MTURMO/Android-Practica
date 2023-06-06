package com.example.socialgifts.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.socialgifts.Product;
import com.example.socialgifts.ProductActivity;
import com.example.socialgifts.R;
import com.example.socialgifts.User;
import com.example.socialgifts.UsersActivity;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class FriendsAdapter extends RecyclerView.Adapter<FriendsAdapter.ViewHolder>{


    private List<User> users;
    private final Context context;
    private final String searchUserName;

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

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.feed_user_card, parent, false);
        return new ViewHolder(view);    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        User user = users.get(position);
        holder.userName.setText(user.getName());
        holder.email.setText(user.getEmail());

        holder.itemView.setOnClickListener(view -> {
            Intent intent = new Intent(context, UsersActivity.class);
            intent.putExtra("id", user.getId());
            intent.putExtra("name", user.getName());
            intent.putExtra("description", user.getEmail());

            context.startActivity(intent);
        });

    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView userName;
        TextView email;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            userName = itemView.findViewById(R.id.feed_user_card_name);
            email = itemView.findViewById(R.id.feed_user_card_email);
        }
    }
}
