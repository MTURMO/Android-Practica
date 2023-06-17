package com.example.socialgifts.fragments;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.example.socialgifts.ApiCalls;
import com.example.socialgifts.Message;
import com.example.socialgifts.Product;
import com.example.socialgifts.R;
import com.example.socialgifts.activities.CreateProductActivity;
import com.example.socialgifts.adapters.ChatAdapter;
import com.example.socialgifts.adapters.FeedAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ChatFragment extends Fragment  {

    private ChatAdapter adapterMain ;
    private List<Message> messagesMain;
    private RecyclerView recyclerViewMain;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_chat, container, false);

        recyclerViewMain = view.findViewById(R.id.chat_recycle_view_main);

        recyclerViewMain.setLayoutManager(new GridLayoutManager(getContext(), 1));

        Intent intent = requireActivity().getIntent();
        int id= intent.getIntExtra("id", 0);

        adapterMain = new ChatAdapter(messagesMain, getContext(), id);

        recyclerViewMain.setAdapter(adapterMain);

        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        String accessToken = sharedPreferences.getString("accessToken", "");

        Intent intent = getActivity().getIntent();
        int id= intent.getIntExtra("id", 0);

        ApiCalls apiCallsMain = new ApiCalls(getContext(), adapterMain);
        apiCallsMain.getMessagesById(accessToken,id,this.getContext());
    }





}