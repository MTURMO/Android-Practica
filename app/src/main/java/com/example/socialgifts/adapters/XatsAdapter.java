package com.example.socialgifts.adapters;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.socialgifts.ApiCalls;
import com.example.socialgifts.Message;
import com.example.socialgifts.R;
import com.example.socialgifts.User;
import com.example.socialgifts.activities.ChatActivity;

import java.util.ArrayList;
import java.util.List;

public class XatsAdapter extends RecyclerView.Adapter<XatsAdapter.ViewHolder>{

    private List<User> usersMain;
    private final Context context;
    private final SharedPreferences sharedPreferences;
    private int id;
    private final ApiCalls apiCallsMain;


    public XatsAdapter(List<User> users, Context context){
        apiCallsMain = new ApiCalls(context,this);

        if(usersMain == null){
            this.usersMain = new ArrayList<>();
        } else{
            this.usersMain = users;
        }

        this.context = context;
        this.sharedPreferences = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
    }

    public void setUsersMain(List<User> users){
        this.usersMain = users;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view= inflater.inflate(R.layout.feed_user_card, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull XatsAdapter.ViewHolder holder, int position) {
        User user = usersMain.get(position);

        holder.nameTextView.setText(user.getName());
        holder.dateTextView.setText(user.getEmail());

        holder.cardView.setOnClickListener(view->{
            Intent intent = new Intent(context, ChatActivity.class);
            intent.putExtra("id",user.getId());
            intent.putExtra("name",user.getName());
            context.startActivity(intent);
        });
    }


    @Override
    public int getItemCount() {
        return usersMain.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        CardView cardView;
        TextView nameTextView;
        TextView dateTextView;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.feed_user_card);
            nameTextView = itemView.findViewById(R.id.feed_user_card_name);
            dateTextView = itemView.findViewById(R.id.feed_user_card_email);

        }
    }
}
