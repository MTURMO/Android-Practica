package com.example.socialgifts.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.example.socialgifts.WishList;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class WishListAdapter extends RecyclerView.Adapter<WishListAdapter.ViewHolder>{

    private final ArrayList<WishList> wishLists;
    private final Context context;

    public WishListAdapter(ArrayList<WishList> wishLists, Context context){
        this.wishLists = wishLists;
        this.context = context;
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
        return wishLists.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
