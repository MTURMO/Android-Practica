package com.example.socialgifts.adapters;

import static android.app.PendingIntent.getActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.socialgifts.ApiCalls;
import com.example.socialgifts.Message;
import com.example.socialgifts.R;

import java.util.ArrayList;
import java.util.List;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ViewHolder> {

    private List<Message> messagesMain;
    private Context context;
    private SharedPreferences sharedPreferences;
    private int id;
    private ApiCalls apiCallsMain;


    public ChatAdapter(List<Message> messages,Context context,int id){
        apiCallsMain = new ApiCalls(context,this);

        if(messagesMain == null){
            this.messagesMain = new ArrayList<>();
        } else{
            this.messagesMain = messages;
        }

        this.context = context;
        this.sharedPreferences = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        this.id = id;

    }


    public void setMessages(List<Message> messages){
        this.messagesMain = messages;
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view= inflater.inflate(R.layout.main_user_message, parent, false);

        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Message message = messagesMain.get(position);

        holder.nameTextView.setText(message.getUser_name_send());
        holder.dateTextView.setText(message.getDate());
        holder.messageTextView.setText(message.getContent());

        int color = fondoMensaje(message.getUser_id_send());

        holder.cardView.setCardBackgroundColor((color));


    }

    private int fondoMensaje(int color){
        SharedPreferences sharedPreferences = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        String id = sharedPreferences.getString("id", "0");
        if(color== Integer.parseInt(id)){
            return ContextCompat.getColor(context, R.color.miMensaje);
        } else{
            return ContextCompat.getColor(context, R.color.suMensaje);
        }
    }
    public void setUserNameForMessage(int id, String name){
        for (int i = 0; i < messagesMain.size(); i++) {
            Message message = messagesMain.get(i);
            if (message.getUser_id_send() == id) {
                message.setUser_name_send(name);
                notifyItemChanged(i);
            }
        }

    }

    @Override
    public int getItemCount() {
        return messagesMain.size() ;
    }



    public class ViewHolder extends RecyclerView.ViewHolder{

        CardView cardView;
        TextView nameTextView;
        TextView dateTextView;
        TextView messageTextView;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.chat_main_user_message_card);
            nameTextView = itemView.findViewById(R.id.chat_main_user_message_name);
            dateTextView = itemView.findViewById(R.id.chat_main_user_message_date);
            messageTextView = itemView.findViewById(R.id.chat_main_user_message);

        }
    }

}