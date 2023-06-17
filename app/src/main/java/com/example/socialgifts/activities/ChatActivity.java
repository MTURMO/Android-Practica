package com.example.socialgifts.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.socialgifts.ApiCalls;
import com.example.socialgifts.Message;
import com.example.socialgifts.R;
import com.example.socialgifts.adapters.ChatAdapter;

import java.util.ArrayList;
import java.util.List;

public class ChatActivity extends AppCompatActivity {

    private Button sendButton;
    private ChatAdapter adapterMain ;
    private List<Message> messagesMain = new ArrayList<>();
    private RecyclerView recyclerViewMain;
    private TextView name;
    private EditText chatInput;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_chat);


        Intent intent = getIntent();
        int id= intent.getIntExtra("id", 0);
        String nameUser = intent.getStringExtra("name");

        recyclerViewMain = findViewById(R.id.chat_recycle_view_main);
        recyclerViewMain.setLayoutManager(new GridLayoutManager(this, 1));
        adapterMain = new ChatAdapter(messagesMain, this, id);
        recyclerViewMain.setAdapter(adapterMain);


        name = findViewById(R.id.chat_friend_name);
        sendButton = findViewById(R.id.send_button);
        chatInput = findViewById(R.id.chat_input);

        name.setText(nameUser);

        sendButton.setOnClickListener(view-> {
                    onSendMessages(chatInput.getText().toString());
                    chatInput.setText("");

        });

        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        String accessToken = sharedPreferences.getString("accessToken", "");

        ApiCalls apiCallsMain = new ApiCalls(this, adapterMain);
        apiCallsMain.getMessagesById(accessToken,id,this);
    }


    public void onSendMessages(String content) {
        // Obtén el mensaje a enviar
        String contentMess = content.trim();

        if (!contentMess.isEmpty()) {

            // Obtiene el token de acceso y el id del usuario
            SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
            String accessToken = sharedPreferences.getString("accessToken", "");
            String id = sharedPreferences.getString("id", "0");

            Intent intent = getIntent();
            int id2 = intent.getIntExtra("id",0);

            Message message = new Message(content,Integer.parseInt(id),id2);

            // Llama al método newMessage() de ApiCalls
            ApiCalls apiCallsMain = new ApiCalls(this, adapterMain);
            apiCallsMain.newMessage(accessToken, message, this, new ApiCalls.MessageCallback() {
                @Override
                public void onSuccess(Message message) {
                    // Llama al método updateChatAdapter() del fragmento para mostrar el mensaje enviado
                    /*runOnUiThread(() -> {
                        updateChatAdapter(message);
                    });*/
                }

                @Override
                public void onFailure(String error) {
                    // Manejar el error en caso de que falle el envío del mensaje
                }
            });

        }
    }

    public void updateChatAdapter(Message message){
        runOnUiThread(() -> {
            messagesMain.add(message);
            adapterMain.notifyItemInserted(messagesMain.size() - 1);
            recyclerViewMain.scrollToPosition(messagesMain.size() - 1);
        });
    }

}
