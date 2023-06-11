package com.example.socialgifts.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.socialgifts.ApiCalls;
import com.example.socialgifts.R;
import com.example.socialgifts.User;
import com.example.socialgifts.adapters.FriendsAdapter;

import java.util.ArrayList;

public class FriendsFragment extends Fragment {


    private ArrayList<User> users;
    private FriendsAdapter adapter;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_friends, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.friends_recycle_view);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 1));
        adapter= new FriendsAdapter(users, getContext(), "all");
        recyclerView.setAdapter(adapter);

        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        String accessToken = sharedPreferences.getString("accessToken", "");

        ApiCalls apiCalls = new ApiCalls(getContext(), adapter);
        apiCalls.getAllUsers(accessToken,this.getContext());
    }
}