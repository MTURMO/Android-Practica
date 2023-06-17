package com.example.socialgifts.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.socialgifts.Product;
import com.example.socialgifts.activities.ProductActivity;
import com.example.socialgifts.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class FeedAdapter extends RecyclerView.Adapter<FeedAdapter.ViewHolder> {

    private List<Product> products;
    private final Context context;
    private final String filter;

    public FeedAdapter(List<Product> products,  Context context, String filter){
        if(products==null){
            this.products=new ArrayList<>();
        } else{
            this.products = products;
        }
        this.context = context;
        this.filter = filter;


    }
    @SuppressLint("NotifyDataSetChanged")
    public void setProducts(List<Product> products){
        this.products=products;
        notifyDataSetChanged();
    }
    public void addProducts(Product products){
        this.products.add(products);
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.product_view_from_feed, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Product product = products.get(position);
        holder.productName.setText(product.getName());
        holder.productDescription.setText(product.getDescription());
        holder.productPrice.setText(String.valueOf(product.getPrice()));

        Glide.with(context).load(product.getPhoto_url()).error(R.drawable.ic_launcher_foreground).into(holder.productImage);


        holder.itemView.setOnClickListener(view -> {
            Intent intent = new Intent(context, ProductActivity.class);
            intent.putExtra("id", product.getId());
            intent.putExtra("name", product.getName());
            intent.putExtra("description", product.getDescription());
            intent.putExtra("product_url", product.getProduct_url());
            intent.putExtra("price", product.getPrice());
            intent.putExtra("category", Arrays.toString(product.getCategoryId()));
            intent.putExtra("image", product.getPhoto_url());

            context.startActivity(intent);
        });


    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView productDescription;
        TextView productName;
        TextView productPrice;
        ImageView productImage;

        public ViewHolder(@NonNull View itemView) {

            super(itemView);
            productDescription = itemView.findViewById(R.id.feed_product_card_category);
            productName = itemView.findViewById(R.id.feed_product_card_name);
            productPrice = itemView.findViewById(R.id.feed_product_card_price);
            productImage = itemView.findViewById(R.id.feed_product_card_image);
        }
    }

}