package com.example.socialgifts.fragments;

import static android.app.Activity.RESULT_OK;

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

import com.example.socialgifts.ApiCalls;
import com.example.socialgifts.R;
import com.example.socialgifts.WishList;
import com.example.socialgifts.activities.CreateWishlistActivity;
import com.example.socialgifts.adapters.WishListAdapter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Date;

public class WishListFragment extends Fragment {

    private ArrayList<WishList> wishLists;
    private WishListAdapter adapter;
    private static final int CREATE_WISHLIST_REQUEST_CODE = 1;
    private String nameUser;
    private String lastname;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    public void setName(String name) {
        this.nameUser = name;
    }

    public void setLastName(String lastName) {
        this.lastname = lastName;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_wish_list, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.wishlist_recycle_view);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 1));
        adapter = new WishListAdapter(wishLists, getContext(),nameUser,lastname);
        recyclerView.setAdapter(adapter);


        Button createWishListButton = view.findViewById(R.id.wish_create);
        createWishListButton.setOnClickListener(view1 -> {
            Intent intent = new Intent(getContext(), CreateWishlistActivity.class);
            startActivityForResult(intent,CREATE_WISHLIST_REQUEST_CODE);
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
        apiCalls.getAllUserWhishlistFragment(accessToken, id, this.getContext());

    }





}