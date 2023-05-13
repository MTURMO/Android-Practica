package com.example.socialgifts;

import org.json.JSONException;
import org.json.JSONObject;

public class Product {

    private int id;
    private final String name;
    private final String description;
    private final String product_url, photo_url;
    private final float price;

    private final int categoryId;

    public Product(String name, String description, String product_url, String photo_url, float price, int categoryId){
        this.name=name;
        this.description=description;
        this.product_url=product_url;
        this.photo_url=photo_url;
        this.price=price;
        this.categoryId=categoryId;
    }

    public JSONObject getProduct() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("name", name);
            jsonObject.put("description", description);
            jsonObject.put("link", product_url);
            jsonObject.put("photo", photo_url);
            jsonObject.put("price", price);
            jsonObject.put("category", categoryId);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

    public JSONObject editProduct() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("name", name);
            jsonObject.put("description", description);
            jsonObject.put("link", product_url);
            jsonObject.put("photo", photo_url);
            jsonObject.put("price", price);
            jsonObject.put("category", categoryId);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

}
