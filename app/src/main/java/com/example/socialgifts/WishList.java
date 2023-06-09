package com.example.socialgifts;

import org.json.JSONException;
import org.json.JSONObject;

public class WishList {
    private int id;
    private String name;
    private String description;
    private int user_id;
    private Gift[] gifts;
    private String creation_date;
    private String end_date;

    public WishList(String name, String description, String end_date) {
        this.name = name;
        this.description = description;
        this.end_date = end_date;
    }

    public WishList(int id, String name, String description, int user_id, Gift[] gifts, String creation_date, String end_date) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.user_id = user_id;
        this.gifts = gifts;
        this.creation_date = creation_date;
        this.end_date = end_date;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public Gift[] getGifts() {
        return gifts;
    }

    public void setGifts(Gift[] gifts) {
        this.gifts = gifts;
    }

    public String getCreation_date() {
        return creation_date;
    }

    public void setCreation_date(String creation_date) {
        this.creation_date = creation_date;
    }

    public String getEnd_date() {
        return end_date;
    }

    public void setEnd_date(String end_date) {
        this.end_date = end_date;
    }

    public JSONObject getWishListPost() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("name", id);
            jsonObject.put("description", description);
            jsonObject.put("end_date", end_date);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

    public JSONObject getWishListEdit() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("id", id);
            jsonObject.put("name", name);
            jsonObject.put("description", description);
            jsonObject.put("user_id", user_id);
            jsonObject.put("gifts", gifts);
            jsonObject.put("creation_date", creation_date);
            jsonObject.put("end_date", end_date);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }
}
