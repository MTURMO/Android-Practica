package com.example.socialgifts.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.socialgifts.R;
import com.example.socialgifts.WishList;
import com.example.socialgifts.activities.WishlistActivityMain;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class WishListAdapter extends RecyclerView.Adapter<WishListAdapter.ViewHolder>{

    private ArrayList<WishList> wishLists;
    private final Context context;

    public WishListAdapter(ArrayList<WishList> wishLists, Context context){
        this.wishLists = wishLists != null ? wishLists : new ArrayList<>();

        this.context = context;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setWishLists(ArrayList<WishList> wishLists){
        this.wishLists = wishLists != null ? wishLists : new ArrayList<>();
        notifyDataSetChanged();
    }
    public void addWishList(WishList wishList){
        this.wishLists.add(wishList);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.wishlist_card_basic, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        WishList wishList = wishLists.get(position);

        holder.nameTextView.setText(wishList.getName());
        holder.numGiftsTextView.setText(String.valueOf(wishList.getGiftsArrayList().size()));
        holder.creationDateTextView.setText(wishList.getCreation_date());

        holder.itemView.setOnClickListener(view -> {
            Intent intent = new Intent(context, WishlistActivityMain.class);
            intent.putExtra("id", wishList.getId());
            intent.putExtra("name", wishList.getName());
            intent.putExtra("description", wishList.getDescription());
            intent.putExtra("user_id", wishList.getUser_id());
            intent.putExtra("creation_date", wishList.getCreation_date());

            context.startActivity(intent);
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
        TextView endDateTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.wishlist_card_name);
            numGiftsTextView = itemView.findViewById(R.id.wishlist_card_num_gifts);
            creationDateTextView = itemView.findViewById(R.id.wishlist_card_creation_date);
            endDateTextView = itemView.findViewById(R.id.wishlist_card_end_date);
        }
    }
}
