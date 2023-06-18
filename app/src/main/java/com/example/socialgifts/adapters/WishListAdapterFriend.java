package com.example.socialgifts.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.socialgifts.R;
import com.example.socialgifts.WishList;
import com.example.socialgifts.activities.WishlistActivityFriend;

import java.util.ArrayList;

public class WishListAdapterFriend extends RecyclerView.Adapter<WishListAdapterFriend.ViewHolder>{

    private ArrayList<WishList> wishLists;
    private final Context context;
    private final String nameUser;
    private final String lastname;
    public WishListAdapterFriend(ArrayList<WishList> wishLists, Context context,String nameUser, String lastname ){
        this.wishLists = wishLists != null ? wishLists : new ArrayList<>();
        this.nameUser = nameUser;
        this.lastname = lastname;
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
        View view = inflater.inflate(R.layout.user_perfil_wishlist_card, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        WishList wishList = wishLists.get(position);

        holder.nameTextView.setText(wishList.getName());
        holder.numGiftsTextView.setText(String.valueOf(wishList.getNumGifts()));

        holder.itemView.setOnClickListener(view -> {

            Intent intent = new Intent(context, WishlistActivityFriend.class);
            intent.putExtra("id", wishList.getId());
            intent.putExtra("name", wishList.getName());
            intent.putExtra("description", wishList.getDescription());
            intent.putExtra("user_id", wishList.getUser_id());
            intent.putExtra("end_date", wishList.getEnd_date());
            intent.putExtra("nameUser", nameUser);
            intent.putExtra("lastname", lastname);

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


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.wishlist_card_name_user);
            numGiftsTextView = itemView.findViewById(R.id.wishlist_card_num_gifts_user);

        }
    }
}
