package com.example.socialgifts.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.example.socialgifts.User;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class FriendsAdapter extends RecyclerView.Adapter<FriendsAdapter.ViewHolder>{


    private final ArrayList<User> users;
    private final Context context;
    private final String searchUserName;

    public FriendsAdapter(ArrayList<User> users, Context context, String searchUserName){
        this.users = users;
        this.context = context;
        this.searchUserName = searchUserName;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
