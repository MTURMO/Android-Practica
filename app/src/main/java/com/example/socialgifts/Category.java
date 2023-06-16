package com.example.socialgifts;

import org.json.JSONException;
import org.json.JSONObject;

public class Category {

    private int id;
    private String name;
    private String description;

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

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    private String photo;
    private int categoryId;

    public Category(int id, String name, String description, String photo) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.photo = photo;
    }
    public Category(int id, String name) {
        this.id = id;
        this.name = name;

    }

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
