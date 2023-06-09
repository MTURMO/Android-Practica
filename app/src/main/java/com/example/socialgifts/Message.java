package com.example.socialgifts;

import org.json.JSONException;
import org.json.JSONObject;

public class Message {
    private String content;
    private int user_id_send;
    private int user_id_recived;

    public Message(String content, int user_id_send, int user_id_recived) {
        this.content = content;
        this.user_id_send = user_id_send;
        this.user_id_recived = user_id_recived;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getUser_id_send() {
        return user_id_send;
    }

    public void setUser_id_send(int user_id_send) {
        this.user_id_send = user_id_send;
    }

    public int getUser_id_recived() {
        return user_id_recived;
    }

    public void setUser_id_recived(int user_id_recived) {
        this.user_id_recived = user_id_recived;
    }

    public JSONObject getMessagePost() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("content", content);
            jsonObject.put("user_id_send", user_id_send);
            jsonObject.put("user_id_recived", user_id_recived);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }
}
