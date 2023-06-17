package com.example.socialgifts.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.SearchView;

import com.android.volley.Response;
import com.example.socialgifts.ApiCalls;
import com.example.socialgifts.R;
import com.example.socialgifts.User;
import com.example.socialgifts.adapters.FriendsAdapter;

import java.util.ArrayList;
import java.util.List;

public class FriendsFragment extends Fragment {


    private ArrayList<User> users;
    private FriendsAdapter adapter;
    private String string="all";
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
        adapter= new FriendsAdapter(users, getContext(), string);
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

        SearchView searchView = getActivity().findViewById(R.id.friends_search_view);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                string=s;
                apiCalls.searchUserBuscador(accessToken, getContext(), string, new Response.Listener<List<User>>() {
                    @Override
                    public void onResponse(List<User> response) {
                        adapter.setUser(response);
                        adapter.setString(string);
                        adapter.notifyDataSetChanged();
                    }
                });
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                string=newText;
                if(newText.isEmpty()){
                    apiCalls.getAllUsers(accessToken,getContext());
                }else{
                    apiCalls.searchUserBuscador(accessToken, getContext(), string, new Response.Listener<List<User>>() {
                        @Override
                        public void onResponse(List<User> response) {
                            adapter.setUser(response);
                            adapter.setString(string);
                            adapter.notifyDataSetChanged();
                        }
                    });
                }

                return true;
            }
        });


    }
}