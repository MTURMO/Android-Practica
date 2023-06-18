package com.example.socialgifts;

import org.json.JSONException;
import org.json.JSONObject;

public class Gift {
    private int id;
    private int wishlist_id;
    private String product_url;
    private int priority;
    private int booked;
    private Product product;

    public Gift(int wishlist_id, String product_url, int priority) {
        this.wishlist_id = wishlist_id;
        this.product_url = product_url;
        this.priority = priority;
    }
    public Gift(int id, Product product){
        this.id = id;
        this.product = product;
    };
    public Gift(int id,int wishlist_id, String product_url, int priority,int booked) {
        this.id = id;
        this.wishlist_id = wishlist_id;
        this.product_url = product_url;
        this.priority = priority;
        this.booked = booked;
    }

    public int getId() {
        return id;
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

    public int getBooked() {
        return booked;
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
