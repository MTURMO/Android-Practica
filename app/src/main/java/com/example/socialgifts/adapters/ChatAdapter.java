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

    private List<Message> messagesMain, messagesFriend;
    private Context context;
    private SharedPreferences sharedPreferences;
    private int id;
    private static final int TYPE_MAIN = 1;
    private static final int TYPE_FRIEND = 2;

    public ChatAdapter(List<Message> messages,Context context,int id){
        if(messagesMain == null){
            this.messagesMain = new ArrayList<>();
        } else{
            this.messagesMain = messages;
        }
        if(messagesFriend == null){
            this.messagesFriend = new ArrayList<>();
        } else{
            this.messagesFriend = messages;
        }
        this.context = context;
        this.sharedPreferences = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        this.id = id;
    }

    @Override
    public int getItemViewType(int position) {
        Message message = messagesMain.get(position);
        if(message.getUser_id_send()==id){
            return TYPE_MAIN;
        } else{
            return TYPE_FRIEND;
        }
    }
    @SuppressLint("NotifyDataSetChanged")
    public void setMessagesMain(List<Message> messages){
        this.messagesMain = messages; ;
        notifyDataSetChanged();
    }
    @SuppressLint("NotifyDataSetChanged")
    public void setMessagesFriend(List<Message> messages){
        this.messagesFriend = messages; ;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        if(viewType==TYPE_MAIN){
            View view = inflater.inflate(R.layout.main_user_message, parent, false);
         return new ViewHolder(view);
        }
        else if(viewType==TYPE_FRIEND){
            View view = inflater.inflate(R.layout.friend_message, parent,false);
            return new ViewHolder(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull ChatAdapter.ViewHolder holder, int position) {
        Message message = messagesMain.get(position);
        int viewType = getItemViewType(position);
        if(viewType==TYPE_MAIN){
            holder.missatgeMain.setText(message.getContent());
        } else if(viewType==TYPE_FRIEND){
            holder.missatgeFriend.setText(message.getContent());
        }
    }

    @Override
    public int getItemCount() {
        return messagesMain.size();
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