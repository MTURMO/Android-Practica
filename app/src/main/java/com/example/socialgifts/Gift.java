package com.example.socialgifts;

import org.json.JSONException;
import org.json.JSONObject;

public class Gift {
    private int id;
    private int wishlist_id;
    private String product_url;
    private int priority;
    private boolean booked;

    public Gift(int wishlist_id, String product_url, int priority) {
        this.wishlist_id = wishlist_id;
        this.product_url = product_url;
        this.priority = priority;
    }

    public int getWishlist_id() {
        return wishlist_id;
    }

    public void setWishlist_id(int wishlist_id) {
        this.wishlist_id = wishlist_id;
    }

    public String getProduct_url() {
        return product_url;
    }

    public void setProduct_url(String product_url) {
        this.product_url = product_url;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public JSONObject getGiftTotal() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("wishlist_id", wishlist_id);
            jsonObject.put("product_url", product_url);
            jsonObject.put("priority", priority);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

    public JSONObject getGifttoEdit() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("id", id);
            jsonObject.put("wishlist_id", wishlist_id);
            jsonObject.put("product_url", product_url);
            jsonObject.put("priority", priority);
            jsonObject.put("booked", booked);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }
}
