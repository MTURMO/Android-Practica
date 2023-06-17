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

import com.android.volley.Response;
import com.example.socialgifts.ApiCalls;
import com.example.socialgifts.Gift;
import com.example.socialgifts.Product;
import com.example.socialgifts.R;
import com.example.socialgifts.adapters.GiftAdapterMain;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WishProductFragmentMain extends Fragment{

    private ArrayList<Product> products;
    private ArrayList<Gift> gifts;
    private GiftAdapterMain adapter;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        products = new ArrayList<>();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_product_wish_main, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.wishlist_activity_recycle);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 1));

        adapter= new GiftAdapterMain(products,gifts, getContext());
        recyclerView.setAdapter(adapter);

        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        String accessToken = sharedPreferences.getString("accessToken", "");

        Intent intent = getActivity().getIntent();
        int id = intent.getIntExtra("id", 0);

        ApiCalls apiCalls = new ApiCalls(getContext());
        apiCalls.getWishlistById(accessToken,id, this.getContext(), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                parsear(response);
            }
        }) ;

    }

    private void parsear(JSONObject response) {
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        String accessToken = sharedPreferences.getString("accessToken", "");

        try {
            JSONArray giftsArray = response.getJSONArray("gifts");
            for (int i = 0; i < giftsArray.length(); i++) {
                JSONObject giftObject = giftsArray.getJSONObject(i);

                String url = giftObject.getString("product_url");
                String name = extractNumberFromUrl(url);

                int number = Integer.parseInt(name);

                ApiCalls apiCalls = new ApiCalls(getContext(), adapter);
                apiCalls.getProductByID2(accessToken, number, getContext());;
                apiCalls.getGiftById(accessToken, giftObject.getInt("id"), getContext());
            }

            } catch (Exception e) {
                    e.printStackTrace();
        }

    }

    private String extractNumberFromUrl(String url) {
        // Expresión regular para buscar el número al final de la URL
        String regex = "\\d+$";

        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(url);

        if (matcher.find()) {
            return matcher.group();
        } else {
            return "";
        }
    }


}