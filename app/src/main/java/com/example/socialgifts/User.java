package com.example.socialgifts;

import org.json.JSONException;
import org.json.JSONObject;

public class User {
    private int id;
    private String name;
    private String last_name;
    private String email;
    private String password;
    private String image;

    public User(String name, String last_name, String email, String password, String image) {
        this.name = name;
        this.last_name = last_name;
        this.email = email;
        this.password = password;
        this.image = image;
    }
    public User(int id,String name, String last_name, String email,  String image) {
       this.id=id;
        this.name = name;
        this.last_name = last_name;
        this.email = email;
        this.image = image;
    }

    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public JSONObject getUsuariTotal() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("name", this.getName());
            jsonObject.put("last_name", this.getLast_name());
            jsonObject.put("email", this.getEmail());
            jsonObject.put("password", this.getPassword());
            jsonObject.put("image", this.getImage());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

    public JSONObject getUsuariLogin() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("email", this.getEmail());
            jsonObject.put("password", this.getPassword());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }
}
