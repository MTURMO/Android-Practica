package com.example.socialgifts.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.example.socialgifts.Product;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class FeedAdapter extends RecyclerView.Adapter<FeedAdapter.ViewHolder> {

    private final ArrayList<Product> products;
    private final Context context;
    private final String filter;

    public FeedAdapter(ArrayList<Product> products, Context context, String filter){
        this.products = products;
        this.context = context;
        this.filter = filter;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

}
