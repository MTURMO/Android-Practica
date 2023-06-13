package com.example.socialgifts.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.socialgifts.Message;
import com.example.socialgifts.Product;
import com.example.socialgifts.R;
import com.example.socialgifts.activities.ProductActivity;

import java.util.ArrayList;
import java.util.List;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ViewHolder> {

    private List<Message> messages;
    private Context context;
    private SharedPreferences sharedPreferences;

    public ChatAdapter(List<Message> messages,Context context){
        if(messages == null){
            this.messages = new ArrayList<>();
        } else{
            this.messages = messages;
        }
        this.context = context;
        this.sharedPreferences = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);

    }

    @SuppressLint("NotifyDataSetChanged")
    public void setMessages(List<Message> messages){
        this.messages = messages; ;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ChatAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.fragment_chat, parent, false);
        return new ChatAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChatAdapter.ViewHolder holder, int position) {
        Message message = messages.get(position);


        int id = sharedPreferences.getInt("id", 0);

        if(message.getUser_id_send()==id){
            holder.missatgeMain.setText(message.getContent());
        }else{
            holder.missatgeFriend.setText(message.getContent());
        }


    }

    @Override
    public int getItemCount() {

        return messages.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView missatgeMain;
        TextView missatgeFriend;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            missatgeMain = itemView.findViewById(R.id.chat_main_user_message);
            missatgeFriend = itemView.findViewById(R.id.chat_friend_message);

        }
    }

}