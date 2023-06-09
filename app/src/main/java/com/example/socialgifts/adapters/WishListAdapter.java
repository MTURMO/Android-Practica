package com.example.socialgifts.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.socialgifts.R;
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
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.wishlist_card, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        WishList wishList = wishLists.get(position);
        holder.nameTextView.setText(wishList.getName());
        holder.numGiftsTextView.setText(wishList.getGifts().length);
        holder.creationDateTextView.setText(wishList.getCreation_date());
        holder.itemView.setOnClickListener(view -> {
            //navigation to WishList Activity
        });
    }

    @Override
    public int getItemCount() {
        return wishLists.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView nameTextView;
        TextView numGiftsTextView;
        TextView creationDateTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.wishlist_card_name);
            numGiftsTextView = itemView.findViewById(R.id.wishlist_card_num_gifts);
            creationDateTextView = itemView.findViewById(R.id.wishlist_card_creation_date);
        }
    }
}
