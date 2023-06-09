package com.example.socialgifts.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.socialgifts.R;
import com.example.socialgifts.WishList;
import com.example.socialgifts.adapters.FriendsAdapter;
import com.example.socialgifts.adapters.WishListAdapter;

import java.util.ArrayList;

public class WishListFragment extends Fragment {

    private ArrayList<WishList> wishLists;

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
        WishListAdapter adapter = new WishListAdapter(wishLists, getContext());
        recyclerView.setAdapter(adapter);
        return view;
    }
}