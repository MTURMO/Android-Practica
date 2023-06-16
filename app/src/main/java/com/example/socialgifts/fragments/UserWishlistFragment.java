package com.example.socialgifts.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.socialgifts.ApiCalls;
import com.example.socialgifts.R;
import com.example.socialgifts.WishList;
import com.example.socialgifts.adapters.WishListAdapterUser;

import java.util.ArrayList;

public class UserWishlistFragment  extends Fragment {
    private ArrayList<WishList> wishLists;
    private WishListAdapterUser adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        wishLists = new ArrayList<>();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.user_perfil_fragment_wishlist, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.user_list_view);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 1));

        adapter = new WishListAdapterUser(wishLists, getContext());
        recyclerView.setAdapter(adapter);


        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        String accessToken = sharedPreferences.getString("accessToken", "");

            Intent intent = getActivity().getIntent();
            int id= intent.getIntExtra("id",0);

        ApiCalls apiCalls = new ApiCalls(getContext(), adapter);
        apiCalls.getAllUserWhishlistPerfilUser(accessToken,id,getContext());
    }
}
