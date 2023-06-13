package com.example.socialgifts.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import com.example.socialgifts.ApiCalls;
import com.example.socialgifts.R;
import com.example.socialgifts.WishList;
import com.example.socialgifts.activities.CreateProductActivity;
import com.example.socialgifts.activities.MainActivity;
import com.example.socialgifts.adapters.FriendsAdapter;
import com.example.socialgifts.adapters.WishListAdapter;

import java.util.ArrayList;

public class WishListFragment extends Fragment {

    private ArrayList<WishList> wishLists;
    private WishListAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_wish_list, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.wishlist_recycle_view);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 1));
        adapter = new WishListAdapter(wishLists, getContext());
        recyclerView.setAdapter(adapter);


        ImageButton createWishListButton = view.findViewById(R.id.wishlist_create_wishlist);
        createWishListButton.setOnClickListener(view1 -> {
            Intent intent = new Intent(getContext(), MainActivity.class);
            startActivity(intent);
            //Navigate to create wishlist activity
        });
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        String accessToken = sharedPreferences.getString("accessToken", "");
        int id = Integer.parseInt(sharedPreferences.getString("id", ""));

        ApiCalls apiCalls = new ApiCalls(getContext(), adapter);
        apiCalls.getAllUserWhishlistFragment(accessToken,id,this.getContext());
    }
}