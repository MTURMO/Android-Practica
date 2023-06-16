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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.example.socialgifts.ApiCalls;
import com.example.socialgifts.Product;
import com.example.socialgifts.activities.ProductActivity;
import com.example.socialgifts.R;
import com.example.socialgifts.activities.CreateProductActivity;
import com.example.socialgifts.adapters.FeedAdapter;

import java.util.ArrayList;

public class FeedFragment extends Fragment{

    private ArrayList<Product> products;
    private FeedAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState);
        products = new ArrayList<>();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_feed, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.feed_recycle_view);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 1));
        adapter= new FeedAdapter(products, getContext(), "all");
        recyclerView.setAdapter(adapter);

        Button createButton = view.findViewById(R.id.feed_create);
        createButton.setOnClickListener(view1 -> {
            Intent intent = new Intent(getContext(), CreateProductActivity.class);
            startActivity(intent);
        });


        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        String accessToken = sharedPreferences.getString("accessToken", "");

        ApiCalls apiCalls = new ApiCalls(getContext(), adapter);
        apiCalls.getProductList(accessToken,this.getContext());
    }

}