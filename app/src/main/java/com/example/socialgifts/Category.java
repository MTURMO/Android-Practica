package com.example.socialgifts;

import org.json.JSONException;
import org.json.JSONObject;

public class Category {

    private int id;
    private String name;
    private String description;
    private String photo;
    private int categoryId;


    public JSONObject getCategory() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("name", name);
            jsonObject.put("description", description);
            jsonObject.put("photo", photo);
            jsonObject.put("categoryParentId", categoryId);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }
    public JSONObject editCategory() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("name", name);
            jsonObject.put("description", description);
            jsonObject.put("photo", photo);
            jsonObject.put("categoryParentId", categoryId);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }
}
