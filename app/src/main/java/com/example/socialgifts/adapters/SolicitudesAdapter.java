package com.example.socialgifts.adapters;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.socialgifts.ApiCalls;
import com.example.socialgifts.R;
import com.example.socialgifts.User;
import com.example.socialgifts.activities.MainActivity;

import java.util.ArrayList;
import java.util.List;

public class SolicitudesAdapter extends RecyclerView.Adapter<SolicitudesAdapter.ViewHolder> {

    private List<User> users;
    private List<Integer> ids;
    private final Context context;
    private final SharedPreferences sharedPreferences;
    private final ApiCalls apiCallsMain;

    public SolicitudesAdapter(List<User> users, Context context, List<Integer> ids){
        apiCallsMain = new ApiCalls(context,this);

        if(users == null){
            this.users = new ArrayList<>();
        } else{
            this.users = users;
        }
        if (ids == null){
            this.ids = new ArrayList<>();
        } else {
            this.ids = ids;
        }
        this.context = context;
        this.sharedPreferences = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
    }
    public void setUser(List<User> userList) {
        this.users = userList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view= inflater.inflate(R.layout.solicitud_card, parent, false);

        return new ViewHolder(view);
    }



    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        User user = users.get(position);
        int id = ids.get(position);
        holder.nameTextView.setText(user.getName());

        holder.reject.setOnClickListener(view-> {
            apiCallsMain.rejectFriendRequest(sharedPreferences.getString("accessToken", ""),id,context);
            Intent intent = new Intent(context, MainActivity.class);
            context.startActivity(intent);
        });

        holder.accept.setOnClickListener(view-> {
            apiCallsMain.acceptFriendRequest(sharedPreferences.getString("accessToken", ""),id,context);
            Intent intent = new Intent(context, MainActivity.class);
            context.startActivity(intent);
        });

    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    public void setId(List<Integer> idList) {
        this.ids = idList;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        CardView cardView;
        TextView nameTextView;
        Button accept,reject;



        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.solicitud_card);
            nameTextView = itemView.findViewById(R.id.textView_friend_name);
            accept = itemView.findViewById(R.id.button_accept);
            reject = itemView.findViewById(R.id.button_decline);

        }
    }

}
