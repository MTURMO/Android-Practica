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
        if(!messagesMain.isEmpty() && !messagesFriend.isEmpty()){
            Message message = messagesMain.get(position);
            if(message.getUser_id_send()==id){
                return TYPE_MAIN;
            } else{
                return TYPE_FRIEND;
            }
        } else{
            return TYPE_MAIN;
        }
    }
    public void setMessages(List<Message> messages){
        messagesMain.clear();
        messagesFriend.clear();

        for(Message message : messages){
            if(message.getUser_id_send()==id){
                messagesMain.add(message);
            } else{
                messagesFriend.add(message);
            }
        }
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view;
        if(viewType==TYPE_MAIN){
             view = inflater.inflate(R.layout.main_user_message, parent, false);
        }
        else {
            view = inflater.inflate(R.layout.friend_message, parent,false);
        }
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Message message;
        int viewType = getItemViewType(position);
        if(!messagesMain.isEmpty() && !messagesFriend.isEmpty()){
            if(viewType==TYPE_MAIN){
                message = messagesMain.get(position);
                holder.missatgeMain.setText(message.getContent());
            } else if(viewType==TYPE_FRIEND){
                message = messagesFriend.get(position-messagesMain.size());
                holder.missatgeFriend.setText(message.getContent());
            }
        }

    }

    @Override
    public int getItemCount() {
        return messagesMain.size() + messagesFriend.size();
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