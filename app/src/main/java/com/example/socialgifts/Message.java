package com.example.socialgifts;

import org.json.JSONException;
import org.json.JSONObject;

public class Message {
    private String content;
    private int user_id_send;
    private int user_id_recived;
    private String user_name_send;
    private String user_name_recived;
    private String date;

    public Message(String content, int user_id_send, int user_id_recived,String date) {
        this.content = content;
        this.user_id_send = user_id_send;
        this.user_id_recived = user_id_recived;
        this.user_name_send="";
        this.date = date;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
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

    public void setUser_name_recived(String user_name_recived) {
        this.user_name_recived = user_name_recived;
    }
    public void setUser_name_send(String user_name_send) {
        this.user_name_send = user_name_send;
    }

    public String getUser_name_recived() {

        return user_name_recived;
    }
    public String getUser_name_send() {

        return user_name_send;
    }
}
