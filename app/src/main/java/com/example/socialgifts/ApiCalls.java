package com.example.socialgifts;

import static android.content.Context.MODE_PRIVATE;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Build;
import android.util.Log;
import android.widget.Button;

import androidx.annotation.RequiresApi;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.socialgifts.activities.MainActivity;
import com.example.socialgifts.adapters.ChatAdapter;
import com.example.socialgifts.adapters.FeedAdapter;
import com.example.socialgifts.adapters.FriendsAdapter;
import com.example.socialgifts.adapters.GiftAdapterFriend;
import com.example.socialgifts.adapters.GiftAdapterMain;
import com.example.socialgifts.adapters.SolicitudesAdapter;
import com.example.socialgifts.adapters.WishListAdapter;
import com.example.socialgifts.adapters.WishListAdapterFriend;
import com.example.socialgifts.adapters.WishListAdapterUser;
import com.example.socialgifts.adapters.XatsAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ApiCalls {
    private Object MainActivity;
    private String accessToken;
    private Context context;
    private FeedAdapter adapter; // Asegúrate de declarar el objeto adapter aquí
    private FriendsAdapter friendsAdapter; // Asegúrate de declarar el objeto adapter aquí
    private WishListAdapter wishListAdapter;
    private GiftAdapterMain adapterGift;
    private ChatAdapter adapterChatMain, adapterChatFriend;
    private WishListAdapterUser adapterWishListUser;
    private GiftAdapterFriend adapterGiftFriend;
    private WishListAdapterFriend adapterWishListFriend;
    private XatsAdapter adapterXats;
    private SolicitudesAdapter solicitudesAdapter;

    public ApiCalls(Context context, SolicitudesAdapter solicitudesAdapter) {
        this.context = context;
        this.solicitudesAdapter = solicitudesAdapter;
    }

    public ApiCalls(Context context, WishListAdapterFriend adapter) {
        this.context = context;
        this.adapterWishListFriend = adapter;
    }
    public ApiCalls (Context context, XatsAdapter adapter){
        this.context = context;
        this.adapterXats = adapter;
    }

    public ApiCalls(Context context, GiftAdapterFriend adapter) {
        this.context = context;
        this.adapterGiftFriend = adapter;
    }

    public ApiCalls(Context context, WishListAdapterUser adapter) {
        this.context = context;
        this.adapterWishListUser = adapter;
    }
    public ApiCalls(Context context, GiftAdapterMain adapter) {
        this.context = context;
        this.adapterGift = adapter;
    }
    public ApiCalls(Context context, FeedAdapter adapter) {
        this.context = context;
        this.adapter = adapter;
    }
    public ApiCalls(Context context, FriendsAdapter adapter) {
        this.context = context;
        this.friendsAdapter = adapter;
    }
    public ApiCalls(Context context, ChatAdapter adapter) {
        this.context = context;
        this.adapterChatMain = adapter;
    }
    public ApiCalls(Context context, WishListAdapter adapter) {
        this.context = context;
        this.wishListAdapter = adapter;
    }


    public ApiCalls(Object mainActivity) {
        MainActivity = mainActivity;

    }
    public interface MessageCallback {
        void onSuccess(Message message);
        void onFailure(String error);
    }


    public String getAccessToken() {
        return accessToken;
    }


    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public void registerUser(User user) {
        RequestQueue queue = Volley.newRequestQueue((Context) MainActivity);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.POST, "https://balandrau.salle.url.edu/i3/socialgift/api/v1/users", user.getUsuariTotal(), new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                try {
                    accessToken = response.get("accessToken").toString();
                    Log.e("onResponse: ",accessToken);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Content-Type", "application/json");

                return params;
            }
        };
        queue.add(jsonObjectRequest);
    }
    public void  loginUser(User user,Context context) {
        RequestQueue queue = Volley.newRequestQueue((context));
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.POST, "https://balandrau.salle.url.edu/i3/socialgift/api/v1/users/login", user.getUsuariLogin(), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    accessToken = response.get("accessToken").toString();
                    SharedPreferences sharedPreferences = context.getSharedPreferences("MyPrefs", MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("accessToken", accessToken);
                    editor.putString("password", user.getPassword());
                    editor.apply();


                    Intent intent = new Intent(context, MainActivity.class);
                    context.startActivity(intent);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Content-Type", "application/json");
                return params;
            }
        };
        queue.add(jsonObjectRequest);
    }
    public void getUserById(String accessToken, int id,Context context) {
        RequestQueue queue = Volley.newRequestQueue(context);
        String url = "https://balandrau.salle.url.edu/i3/socialgift/api/v1/users/" + id;

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Log.e("resposta", "La resposta es: " + response.toString());
                        //Obtenim tots els usuaris en format json
                        String name = null;
                        try {
                            name = response.getString("name");
                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }
                        adapterChatMain.setUserNameForMessage(id,name);

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("resposta", "Hi ha hagut un error:" + error);
                    }
                }
                ) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                headers.put("Authorization", "Bearer " + accessToken);
                return headers;
            }
        };
        queue.add(jsonObjectRequest);
    }
    public void searchUser(String accessToken, String search,Response.Listener<JSONArray> listener) {
        RequestQueue queue = Volley.newRequestQueue((Context) MainActivity);
        String url = "https://balandrau.salle.url.edu/i3/socialgift/api/v1/users/search?s=" + search;

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONArray>() {

                    @Override
                    public void onResponse(JSONArray response) {
                        listener.onResponse(response);                        //Obtenim tots els usuaris en format json

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("resposta", "Hi ha hagut un error:" + error);
                    }
                }
                ) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                headers.put("Authorization", "Bearer " + accessToken);
                return headers;
            }
        };
        queue.add(jsonArrayRequest);
    }
    public void searchUserBuscador(String accessToken,Context context, String search,Response.Listener<List<User>> listener) {
        RequestQueue queue = Volley.newRequestQueue((context));
        String url = "https://balandrau.salle.url.edu/i3/socialgift/api/v1/users/search?s=" + search;

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONArray>() {

                    @Override
                    public void onResponse(JSONArray response) {
                        List<User> users = new ArrayList<>();
                        try{
                            for (int i = 0; i < response.length(); i++) {
                                JSONObject user = response.getJSONObject(i);
                                users.add(new User(
                                        user.getInt("id"),
                                        user.getString("name"),
                                        user.getString("last_name"),
                                        user.getString("email"),
                                        user.getString("image")));
                            }
                            listener.onResponse(users);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("resposta", "Hi ha hagut un error:" + error);
                    }
                }
                ) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                headers.put("Authorization", "Bearer " + accessToken);
                return headers;
            }
        };
        queue.add(jsonArrayRequest);
    }
    public void getUserFriends(String accessToken, int id) {
        RequestQueue queue = Volley.newRequestQueue((Context) MainActivity);
        String url = "https://balandrau.salle.url.edu/i3/socialgift/api/v1/users/" + id + "/friends";

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONArray>() {

                    @Override
                    public void onResponse(JSONArray response) {
                        Log.e("resposta", "La resposta es: " + response.toString());
                        //Obtenim tots els usuaris en format json

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("resposta", "Hi ha hagut un error:" + error);
                    }
                }
                ) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                headers.put("Authorization", "Bearer " + accessToken);
                return headers;
            }
        };
        queue.add(jsonArrayRequest);
    }
    public void getGiftReserved(String accessToken, int id) {
        RequestQueue queue = Volley.newRequestQueue((Context) MainActivity);
        String url = "https://balandrau.salle.url.edu/i3/socialgift/api/v1/users/" + id + "/gifts/reserved";

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONArray>() {

                    @Override
                    public void onResponse(JSONArray response) {
                        Log.e("resposta", "La resposta es: " + response.toString());
                        //Obtenim tots els usuaris en format json

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("resposta", "Hi ha hagut un error:" + error);
                    }
                }
                ) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                headers.put("Authorization", "Bearer " + accessToken);
                return headers;
            }
        };
        queue.add(jsonArrayRequest);
    }

    public void getAllUserWhishlistSpinnerProduct(String accessToken, int id,Response.Listener<JSONArray> listener) {
        RequestQueue queue = Volley.newRequestQueue((Context) MainActivity);
        String url = "https://balandrau.salle.url.edu/i3/socialgift/api/v1/users/" + id + "/wishlists";

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONArray>() {

                    @Override
                    public void onResponse(JSONArray response) {
                        Log.e("resposta", "La resposta es: " + response.toString());
                        //Obtenim tots els usuaris en format json
                        listener.onResponse(response);

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("resposta", "Hi ha hagut un error:" + error);
                    }
                }
                ) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                headers.put("Authorization", "Bearer " + accessToken);
                return headers;
            }
        };
        queue.add(jsonArrayRequest);
    }
    public void getAllUserWhishlistFragment(String accessToken, int id, Context context) {
        RequestQueue queue = Volley.newRequestQueue(context);
        String url = "https://balandrau.salle.url.edu/i3/socialgift/api/v1/users/" + id + "/wishlists";

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONArray>() {

                    @Override
                    public void onResponse(JSONArray response) {
                        Log.e("respostaCorrecta", "La resposta es: " + response.toString());
                        //Obtenim tots els usuaris en format json
                        ArrayList<WishList> wishLists = new ArrayList<>();

                        try {
                            for (int i = 0; i < response.length(); i++) {
                                JSONObject jsonProduct = response.getJSONObject(i);

                                int id = jsonProduct.getInt("id");
                                String name = jsonProduct.getString("name");
                                String description = jsonProduct.getString("description");
                                int user_id = jsonProduct.getInt("user_id");

                                String creation = jsonProduct.getString("creation_date");
                                String end = jsonProduct.getString("end_date");

                                JSONArray gift= jsonProduct.getJSONArray("gifts");

                                ArrayList<Gift> gifts = new ArrayList<>();
                                try {
                                    for (int j = 0; j < gift.length(); j++) {
                                        JSONObject jsonGift = gift.getJSONObject(j);

                                        int id_gift = jsonGift.getInt("id");
                                        int id_wish = jsonGift.getInt("wishlist_id");
                                        String product_url = jsonGift.getString("product_url");
                                        int priority = jsonGift.getInt("priority");
                                        int booked = Integer.parseInt(jsonGift.getString("booked"));

                                        Gift gift2= new Gift(id_gift, id_wish, product_url, priority, booked);

                                        gifts.add(gift2);
                                    }
                                }catch (JSONException e) {

                                }
                                    WishList wishList = new WishList(id, name, description, user_id,gifts, creation, end);

                                wishLists.add(wishList);

                            }

                            wishListAdapter.setWishLists(wishLists);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("resposta", "Hi ha hagut un error:" + error);
                    }
                }
                ) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                headers.put("Authorization", "Bearer " + accessToken);
                return headers;
            }
        };
        queue.add(jsonArrayRequest);
    }
    private boolean fechaEndPasada(String endDate){
        if (endDate == null|| endDate.equalsIgnoreCase("null")) {
            // Si endDate es null, aceptar en este caso
            return true;
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
            LocalDateTime currentDate = LocalDateTime.now();
            LocalDateTime endDateDate =LocalDateTime.parse(endDate, formatter);
            return endDateDate.isBefore(currentDate);
        }
        return false;
    }
    public void getAllUserWhishlistPerfilUser(String accessToken, int id, Context context) {
        RequestQueue queue = Volley.newRequestQueue(context);
        String url = "https://balandrau.salle.url.edu/i3/socialgift/api/v1/users/" + id + "/wishlists";

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONArray>() {

                    @Override
                    public void onResponse(JSONArray response) {
                        Log.e("respostaCorrecta", "La resposta es: " + response.toString());
                        //Obtenim tots els usuaris en format json
                        ArrayList<WishList> wishLists = new ArrayList<>();

                        try {
                            for (int i = 0; i < response.length(); i++) {
                                JSONObject jsonProduct = response.getJSONObject(i);

                                int id = jsonProduct.getInt("id");
                                String name = jsonProduct.getString("name");
                                String description = jsonProduct.getString("description");
                                int user_id = jsonProduct.getInt("user_id");

                                String creation = jsonProduct.getString("creation_date");
                                String end = jsonProduct.getString("end_date");

                                JSONArray gift= jsonProduct.getJSONArray("gifts");
                                ArrayList<Gift> gifts = new ArrayList<>();
                                try {
                                    for (int j = 0; j < gift.length(); j++) {
                                        JSONObject jsonGift = gift.getJSONObject(j);

                                        int id_gift = jsonGift.getInt("id");
                                        int id_wish = jsonGift.getInt("wishlist_id");
                                        String product_url = jsonGift.getString("product_url");
                                        int priority = jsonGift.getInt("priority");
                                        int booked = Integer.parseInt(jsonGift.getString("booked"));

                                        Gift gift2= new Gift(id_gift, id_wish, product_url, priority, booked);
                                        gifts.add(gift2);
                                    }
                                }catch (JSONException e) {

                                }

                                WishList wishList = new WishList(id, name, description, user_id,gifts, creation, end);
                                Log.e("respostaCorrecta", wishList.getGiftsArrayList().toString());
                                if(!fechaEndPasada(end)){
                                    wishLists.add(wishList);
                                }                            }

                            adapterWishListUser.setWishLists(wishLists);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("resposta", "Hi ha hagut un error:" + error);
                    }
                }
                ) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                headers.put("Authorization", "Bearer " + accessToken);
                return headers;
            }
        };
        queue.add(jsonArrayRequest);
    }
    public void getAllUserWhishlistPerfilFriend(String accessToken, int id, Context context) {
        RequestQueue queue = Volley.newRequestQueue(context);
        String url = "https://balandrau.salle.url.edu/i3/socialgift/api/v1/users/" + id + "/wishlists";

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONArray>() {

                    @Override
                    public void onResponse(JSONArray response) {
                        Log.e("respostaCorrecta", "La resposta es: " + response.toString());
                        //Obtenim tots els usuaris en format json
                        ArrayList<WishList> wishLists = new ArrayList<>();

                        try {
                            for (int i = 0; i < response.length(); i++) {
                                JSONObject jsonProduct = response.getJSONObject(i);

                                int id = jsonProduct.getInt("id");
                                String name = jsonProduct.getString("name");

                                String description = jsonProduct.getString("description");
                                int user_id = jsonProduct.getInt("user_id");

                                String creation = jsonProduct.getString("creation_date");
                                String end = jsonProduct.getString("end_date");

                                JSONArray gift= jsonProduct.getJSONArray("gifts");
                                ArrayList<Gift> gifts = new ArrayList<>();
                                try {
                                    for (int j = 0; j < gift.length(); j++) {
                                        JSONObject jsonGift = gift.getJSONObject(j);

                                        int id_gift = jsonGift.getInt("id");
                                        int id_wish = jsonGift.getInt("wishlist_id");
                                        String product_url = jsonGift.getString("product_url");
                                        int priority = jsonGift.getInt("priority");
                                        int booked = Integer.parseInt(jsonGift.getString("booked"));

                                        Gift gift2= new Gift(id_gift, id_wish, product_url, priority, booked);
                                        gifts.add(gift2);
                                    }
                                }catch (JSONException e) {

                                }

                                WishList wishList = new WishList(id, name, description, user_id,gifts, creation, end);
                                Log.e("respostaCorrecta", wishList.getGiftsArrayList().toString());

                                if(!fechaEndPasada(end)){
                                    wishLists.add(wishList);
                                }
                            }

                            adapterWishListFriend.setWishLists(wishLists);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("resposta", "Hi ha hagut un error:" + error);
                    }
                }
                ) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                headers.put("Authorization", "Bearer " + accessToken);
                return headers;
            }
        };
        queue.add(jsonArrayRequest);
    }
    public void editUser(String accessToken, User user,Context context) {
        RequestQueue queue = Volley.newRequestQueue((context));
        String url = "https://balandrau.salle.url.edu/i3/socialgift/api/v1/users";
        Log.e("resposta", "La resposta es: " + user.getUsuariTotal().toString());
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.PUT, url, user.getUsuariTotal(), new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Log.e("resposta", "La resposta es: " + response.toString());
                        //Obtenim tots els usuaris en format json
                        SharedPreferences sharedPreferences = context.getSharedPreferences("MyPrefs", MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();

                        editor.putString("password", user.getPassword());
                        editor.apply();

                        editor.putString("name", user.getName());
                        editor.putString("last_name", user.getLast_name());
                        editor.putString("image", user.getImage());
                        editor.putString("email", user.getEmail());
                        editor.putString("id", String.valueOf(user.getId()));
                        editor.apply();

                        Intent intent = new Intent(context, MainActivity.class);
                        context.startActivity(intent);
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("resposta", "Hi ha hagut un error:" + error);
                    }
                }
                ) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                headers.put("Authorization", "Bearer " + accessToken);
                return headers;
            }
        };
        queue.add(jsonObjectRequest);
    }

    public void createGift(String accessToken, Gift gift, Context context) {
        RequestQueue queue = Volley.newRequestQueue(context);
        String url ="https://balandrau.salle.url.edu/i3/socialgift/api/v1/gifts";

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.POST, url, gift.getGiftTotal(), new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Log.e("resposta", "La resposta es: " + response.toString());
                        //Obtenim tots els usuaris en format json

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("resposta", "Hi ha hagut un error: " + error.networkResponse.statusCode);
                    }
                }
                ) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                headers.put("Authorization", "Bearer " + accessToken);
                return headers;
            }
        };
        queue.add(jsonObjectRequest);
    }
    public void getGiftByIdForFriend(String accessToken, int id, Context context) {
        RequestQueue queue = Volley.newRequestQueue((context));
        String url ="https://balandrau.salle.url.edu/i3/socialgift/api/v1/gifts/" + id;

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Log.e("resposta", "La resposta es: "+ response.toString());
                        //Obtenim tots els usuaris en format json
                        Gift product;
                        try {
                            product = new Gift(
                                    response.getInt("id"),
                                    response.getInt("wishlist_id"),
                                    response.getString("product_url"),
                                    response.getInt("priority"),
                                    Integer.parseInt(response.getString("booked")));
                            adapterGiftFriend.addGifts(product);
                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("resposta", "Hi ha hagut un error:" + error);
                    }
                }
                ) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                headers.put("Authorization", "Bearer " + accessToken);
                return headers;
            }
        };
        queue.add(jsonObjectRequest);
    }
    public void getGiftById(String accessToken, int id, Context context) {
        RequestQueue queue = Volley.newRequestQueue((context));
        String url ="https://balandrau.salle.url.edu/i3/socialgift/api/v1/gifts/" + id;

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Log.e("resposta", "La resposta es: "+ response.toString());
                        //Obtenim tots els usuaris en format json
                        Gift product;
                        try {
                            product = new Gift(
                                    response.getInt("id"),
                                    response.getInt("wishlist_id"),
                                    response.getString("product_url"),
                                    response.getInt("priority"),
                                    Integer.parseInt(response.getString("booked")));
                            adapterGift.addGifts(product);
                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("resposta", "Hi ha hagut un error:" + error);
                    }
                }
                ) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                headers.put("Authorization", "Bearer " + accessToken);
                return headers;
            }
        };
        queue.add(jsonObjectRequest);
    }

    /*
    Funció per editar Gift en funció del seu ID
    Cal haver fet LogIn per obtenir el accessToken
    A la funcio se li passa:
        - accessToken
        - id
        - Gift
     */
    public void editGiftsById(String accessToken, int id, Gift gift) {
        RequestQueue queue = Volley.newRequestQueue((Context) MainActivity);
        String url ="https://balandrau.salle.url.edu/i3/socialgift/api/v1/gifts/" + id;

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.PUT, url, gift.getGifttoEdit(), new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Log.e("resposta", "La resposta es: "+ response.toString());
                        //Obtenim tots els usuaris en format json

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("resposta", "Hi ha hagut un error:" + error);
                    }
                }
                ) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                headers.put("Authorization", "Bearer " + accessToken);
                return headers;
            }
        };
        queue.add(jsonObjectRequest);
    }
    public void deleteGiftById(String accessToken, int id, Context context,Response.Listener<JSONObject> listener) {
        RequestQueue queue = Volley.newRequestQueue((context) );
        String url ="https://balandrau.salle.url.edu/i3/socialgift/api/v1/gifts/" + id;

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.DELETE, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Log.e("resposta", "La resposta es: "+ response.toString());
                        //Obtenim tots els usuaris en format json
                        listener.onResponse(response);

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("resposta", "Hi ha hagut un error:" + error);
                    }
                }
                ) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                headers.put("Authorization", "Bearer " + accessToken);
                return headers;
            }
        };
        queue.add(jsonObjectRequest);
    }


    public void createWishList(String accessToken, WishList wishList,Context context) {
        RequestQueue queue = Volley.newRequestQueue((context));
        String url = "https://balandrau.salle.url.edu/i3/socialgift/api/v1/wishlists";

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.POST, url, wishList.getWishListPost(), new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Log.e("resposta", "La resposta es: " + response.toString());
                        //Obtenim tots els usuaris en format json

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("resposta", "Hi ha hagut un error: " + error);
                    }
                }
                ) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                headers.put("Authorization", "Bearer " + accessToken);
                return headers;
            }
        };
        queue.add(jsonObjectRequest);
    }

    public void getWishlistById(String accessToken, int id,Context MainActivity,Response.Listener<JSONObject> listener) {
        RequestQueue queue = Volley.newRequestQueue( MainActivity);
        String url = "https://balandrau.salle.url.edu/i3/socialgift/api/v1/wishlists/" + id;

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Log.e("resposta", "La resposta es: " + response.toString());
                        //Obtenim tots els usuaris en format json
                        listener.onResponse(response);

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("resposta", "Hi ha hagut un error: " + error);
                    }
                }
                ) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                headers.put("Authorization", "Bearer " + accessToken);
                return headers;
            }
        };
        queue.add(jsonObjectRequest);
    }

    /*
    Funció per eliminar una wishlist
    Cal haver fet LogIn per obtenir el accessToken
    A la funcio se li passa:
        - accessToken
        - int id
     */
    public void deleteWishlistById(String accessToken, int id, Context context,Response.Listener listener2 ,Response.ErrorListener listener) {
        RequestQueue queue = Volley.newRequestQueue((context));
        String url = "https://balandrau.salle.url.edu/i3/socialgift/api/v1/wishlists/" + id;

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.DELETE, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Log.e("resposta", "La resposta es: " + response.toString());
                        //Obtenim tots els usuaris en format json
                        listener2.onResponse(response);

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        listener.onErrorResponse(error);
                        Log.e("resposta", "Hi ha hagut un error: " + error + id);
                    }
                }
                ) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                headers.put("Authorization", "Bearer " + accessToken);
                return headers;
            }
        };
        queue.add(jsonObjectRequest);
    }


    public void editWishlist(String accessToken, int id, WishList wishList,Context context) {
        RequestQueue queue = Volley.newRequestQueue((context));
        String url = "https://balandrau.salle.url.edu/i3/socialgift/api/v1/wishlists/" + id;

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.PUT, url, wishList. getWishListEdit2(), new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Log.e("resposta", "La resposta es: " + response.toString());
                        //Obtenim tots els usuaris en format json

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("resposta", "Hi ha hagut un error: " + error);
                    }
                }
                ) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                headers.put("Authorization", "Bearer " + accessToken);
                return headers;
            }
        };
        queue.add(jsonObjectRequest);
    }

    public void newMessage(String accessToken, Message message,Context context,final MessageCallback callback) {
        RequestQueue queue = Volley.newRequestQueue((context));
        String url = "https://balandrau.salle.url.edu/i3/socialgift/api/v1/messages";

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.POST, url, message.getMessagePost(), new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Log.e("resposta", "La resposta es: " + response.toString());
                        callback.onSuccess(message);
                        //Obtenim tots els usuaris en format json

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("resposta", "Hi ha hagut un error: " + error);
                        callback.onFailure("Error al crear el missatge");
                    }
                }
                ) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                headers.put("Authorization", "Bearer " + accessToken);
                return headers;
            }
        };
        queue.add(jsonObjectRequest);
    }

    /*
    Funció per obtenir informacio del servidor
    Cal haver fet LogIn per obtenir el accessToken
    A la funcio se li passa:
        - accessToken
     */
    public void getServerInfo(String accessToken) {
        RequestQueue queue = Volley.newRequestQueue((Context) MainActivity);
        String url = "https://balandrau.salle.url.edu/i3/socialgift/api/v1/messages/ServerInfo";

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONArray>() {

                    @Override
                    public void onResponse(JSONArray response) {
                        Log.e("resposta", "La resposta es: " + response.toString());
                        //Obtenim tots els usuaris en format json

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("resposta", "Hi ha hagut un error: " + error);
                    }
                }
                ) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                headers.put("Authorization", "Bearer " + accessToken);
                return headers;
            }
        };
        queue.add(jsonArrayRequest);
    }

    public void getUsersMessaged(String accessToken,Context context) {
        RequestQueue queue = Volley.newRequestQueue((context));
        String url = "https://balandrau.salle.url.edu/i3/socialgift/api/v1/messages/users";

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONArray>() {

                    @Override
                    public void onResponse(JSONArray response) {
                        Log.e("resposta", "La resposta es: " + response.toString());
                        //Obtenim tots els usuaris en format json
                        ArrayList<User> user = new ArrayList<>();
                        try {
                            for (int i = 0; i < response.length(); i++) {
                                JSONObject jsonProduct = response.getJSONObject(i);

                                int id_user = jsonProduct.getInt("id");
                                String name = jsonProduct.getString("name");
                                String last_name = jsonProduct.getString("last_name");
                                String email = jsonProduct.getString("email");
                                String image = jsonProduct.getString("image");

                                User user1 = new User(id_user,name,last_name,email,image);

                                user.add(user1);

                            }
                            adapterXats.setUsersMain(user);

                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("resposta", "Hi ha hagut un error: " + error);
                    }
                }
                ) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                headers.put("Authorization", "Bearer " + accessToken);
                return headers;
            }
        };
        queue.add(jsonArrayRequest);
    }

    /*
    Funció per crear un missatge
    Cal haver fet LogIn per obtenir el accessToken
    A la funcio se li passa:
        - accessToken
        - new Message
     */
    public void getFriends(String accessToken,Context context) {
        RequestQueue queue = Volley.newRequestQueue((context) );
        String url = "https://balandrau.salle.url.edu/i3/socialgift/api/v1/friends";

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONArray>() {

                    @Override
                    public void onResponse(JSONArray response) {
                        Log.e("resposta", "La resposta es: " + response.toString());
                        //Obtenim tots els usuaris en format json
                        List<User> userList = new ArrayList<>();

                        try {
                            for (int i = 0; i < response.length(); i++) {
                                JSONObject jsonObject = response.getJSONObject(i);

                                int id = 0;
                                id = jsonObject.getInt("id");
                                String name = jsonObject.getString("name");
                                String last_name = jsonObject.getString("last_name");
                                String email = jsonObject.getString("email");
                                String image = jsonObject.getString("image");
                                User user = new User(id, name, last_name, email, image);
                                userList.add(user);
                            }
                            friendsAdapter.setUser(userList);
                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("resposta", "Hi ha hagut un error: " + error);
                    }
                }
                ) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                headers.put("Authorization", "Bearer " + accessToken);
                return headers;
            }
        };
        queue.add(jsonArrayRequest);
    }
    public void getFriends2(String accessToken,Context context,Response.Listener<JSONArray> listener) {
        RequestQueue queue = Volley.newRequestQueue((context) );
        String url = "https://balandrau.salle.url.edu/i3/socialgift/api/v1/friends";

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONArray>() {

                    @Override
                    public void onResponse(JSONArray response) {
                        Log.e("resposta", "La resposta es : " + response.toString());
                        listener.onResponse(response);
                        //Obtenim tots els usuaris en format json

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("resposta", "Hi ha hagut un error: " + error);
                    }
                }
                ) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                headers.put("Authorization", "Bearer " + accessToken);
                return headers;
            }
        };
        queue.add(jsonArrayRequest);
    }

    /*
    Funció per crear un missatge
    Cal haver fet LogIn per obtenir el accessToken
    A la funcio se li passa:
        - accessToken
        - new Message
     */
    public void getFriendsRequest(String accessToken,Context context) {
        RequestQueue queue = Volley.newRequestQueue((context));
        String url = "https://balandrau.salle.url.edu/i3/socialgift/api/v1/friends/requests";

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONArray>() {

                    @Override
                    public void onResponse(JSONArray response) {
                        Log.e("resposta", "La resposta es: " + response.toString());
                        //Obtenim tots els usuaris en format json
                        List<User> userList = new ArrayList<>();
                        List<Integer> idList = new ArrayList<>();

                        try {
                            for (int i = 0; i < response.length(); i++) {
                                JSONObject jsonObject = response.getJSONObject(i);

                                int id = 0;
                                id = jsonObject.getInt("id");
                                String name = jsonObject.getString("name");
                                String last_name = jsonObject.getString("last_name");
                                String email = jsonObject.getString("email");
                                String image = jsonObject.getString("image");
                                User user = new User(id, name, last_name, email, image);
                                userList.add(user);
                                idList.add(id);
                            }
                            solicitudesAdapter.setUser(userList);
                            solicitudesAdapter.setId(idList);
                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("resposta", "Hi ha hagut un error: " + error);
                    }
                }
                ) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                headers.put("Authorization", "Bearer " + accessToken);
                return headers;
            }
        };
        queue.add(jsonArrayRequest);
    }
    public void getFriendsRequest2(String accessToken,Context context,Response.Listener<JSONArray> listener) {
        RequestQueue queue = Volley.newRequestQueue((context));
        String url = "https://balandrau.salle.url.edu/i3/socialgift/api/v1/friends/requests";

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONArray>() {

                    @Override
                    public void onResponse(JSONArray response) {
                        Log.e("resposta", "La resposta es: " + response.toString());
                        //Obtenim tots els usuaris en format json
                        listener.onResponse(response);

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("resposta", "Hi ha hagut un error: " + error);
                    }
                }
                ) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                headers.put("Authorization", "Bearer " + accessToken);
                return headers;
            }
        };
        queue.add(jsonArrayRequest);
    }

    /*
    Funció per crear un missatge
    Cal haver fet LogIn per obtenir el accessToken
    A la funcio se li passa:
        - accessToken
        - new Message
     */
    public void createFriendRequest(String accessToken, int id, Button followButton,Context context,Response.Listener<JSONObject> listener,Response.ErrorListener errorListener) {
        Resources resources = context.getResources();

        RequestQueue queue = Volley.newRequestQueue(context);
        String url = "https://balandrau.salle.url.edu/i3/socialgift/api/v1/friends/" + id;

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.POST, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Log.e("resposta", "La resposta es: " + response.toString());
                        //Obtenim tots els usuaris en format json
                        followButton.setText("Solicitado");
                        followButton.setBackgroundColor(resources.getColor(android.R.color.darker_gray));


                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("resposta", "Hi ha hagut un error: " + error);
                        errorListener.onErrorResponse(error);
                    }
                }
                ) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                headers.put("Authorization", "Bearer " + accessToken);
                return headers;
            }
        };
        queue.add(jsonObjectRequest);
    }
    public void bookGift(String accessToken, int id, Context context,Response.Listener<JSONObject> listener) {
        Resources resources = context.getResources();

        RequestQueue queue = Volley.newRequestQueue(context);
        String url = "https://balandrau.salle.url.edu/i3/socialgift/api/v1/gifts/"+id+"/book";

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.POST, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Log.e("resposta", "La resposta es: " + response.toString());
                        //Obtenim tots els usuaris en format json
                        listener.onResponse(response);



                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("resposta", "Hi ha hagut un error: " + error);
                    }
                }
                ) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                headers.put("Authorization", "Bearer " + accessToken);
                return headers;
            }
        };
        queue.add(jsonObjectRequest);
    }

    /*
    Funció per crear un missatge
    Cal haver fet LogIn per obtenir el accessToken
    A la funcio se li passa:
        - accessToken
        - new Message
     */
    public void acceptFriendRequest(String accessToken, int id,Context context) {
        RequestQueue queue = Volley.newRequestQueue((context));
        String url = "https://balandrau.salle.url.edu/i3/socialgift/api/v1/friends/" + id;

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.PUT, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Log.e("resposta", "La resposta es: " + response.toString());
                        //Obtenim tots els usuaris en format json

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("resposta", "Hi ha hagut un error: " + error);
                    }
                }
                ) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                headers.put("Authorization", "Bearer " + accessToken);
                return headers;
            }
        };
        queue.add(jsonObjectRequest);
    }

    /*
    Funció per crear un missatge
    Cal haver fet LogIn per obtenir el accessToken
    A la funcio se li passa:
        - accessToken
        - new Message
     */
    public void rejectFriendRequest(String accessToken, int id,Context context) {
        RequestQueue queue = Volley.newRequestQueue((context) );
        String url = "https://balandrau.salle.url.edu/i3/socialgift/api/v1/friends/" + id;

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.DELETE, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Log.e("resposta", "La resposta es: " + response.toString());
                        //Obtenim tots els usuaris en format json

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("resposta", "Hi ha hagut un error: " + error);
                    }
                }
                ) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                headers.put("Authorization", "Bearer " + accessToken);
                return headers;
            }
        };
        queue.add(jsonObjectRequest);
    }


    public void getAllUsers(String accessToken,Context context) {
        RequestQueue queue = Volley.newRequestQueue(context);
        String url = "https://balandrau.salle.url.edu/i3/socialgift/api/v1/users";

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONArray>() {

                    @Override
                    public void onResponse(JSONArray response) {
                        Log.e("resposta", "La resposta es: " + response.toString());
                        //Obtenim tots els usuaris en format json
                        List<User> userList = new ArrayList<>();

                        try {
                            for (int i = 0; i < response.length(); i++) {
                                JSONObject jsonObject = response.getJSONObject(i);

                                int id= jsonObject.getInt("id");
                                String name = jsonObject.getString("name");
                                String last_name = jsonObject.getString("last_name");
                                String email = jsonObject.getString("email");
                                String image = jsonObject.getString("image");
                                User user = new User(id,name, last_name, email, image);
                                userList.add(user);
                            }

                            friendsAdapter.setUser(userList);


                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("resposta", "Hi ha hagut un error:" + error);
                    }
                }
                ) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                headers.put("Authorization", "Bearer " + accessToken);
                return headers;
            }
        };
        queue.add(jsonArrayRequest);
    }

    public void getProductList(String accessToken,Context context) {
        RequestQueue queue = Volley.newRequestQueue(context);
        String url = "https://balandrau.salle.url.edu/i3/mercadoexpress/api/v1/products";

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {

                        ArrayList<Product> productList = new ArrayList<>();

                        try {
                            for (int i = 0; i < response.length(); i++) {
                                JSONObject jsonProduct = response.getJSONObject(i);

                                int id= jsonProduct.getInt("id");
                                String productName = jsonProduct.getString("name");
                                String productDescription = jsonProduct.getString("description");
                                String photo = jsonProduct.getString("photo");
                                String link = jsonProduct.getString("link");
                                int[] categoriaArray = new int[0];

                                if(jsonProduct.getJSONArray("categoryIds")==null){
                                    categoriaArray = new int[0];
                                    categoriaArray[0]=(0);
                                }else{
                                    JSONArray categoria = jsonProduct.getJSONArray("categoryIds");
                                    categoriaArray = new int[categoria.length()];
                                    for (int j = 0; j < categoria.length(); j++) {
                                        categoriaArray[j] = categoria.getInt(j);
                                    }
                                }


                                float productPrice = Float.parseFloat(jsonProduct.getString("price"));

                                Product product = new Product(id,productName, productDescription, productPrice, photo, link, categoriaArray);
                                productList.add(product);
                            }

                            adapter.setProducts(productList);
                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Aquí puedes manejar el error de la solicitud
                    }
                }
        ) {
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> headers = new HashMap<>();
                headers.put("Authorization", "Bearer " + accessToken);
                return headers;
            }
        };

        queue.add(jsonArrayRequest);
    }
    public void getMessagesById(String accessToken, int id,Context context) {
        RequestQueue queue = Volley.newRequestQueue((context) );
        String url = "https://balandrau.salle.url.edu/i3/socialgift/api/v1/messages/" + id;

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONArray>() {

                    @Override
                    public void onResponse(JSONArray response) {
                        Log.e("resposta", "La resposta es: " + response.toString());
                        //Obtenim tots els usuaris en format json
                        ArrayList<Message> productList = new ArrayList<>();
                        List<Integer> userIds = new ArrayList<>();

                        try {
                            for (int i = 0; i < response.length(); i++) {
                                JSONObject jsonProduct = response.getJSONObject(i);

                                int id_user = jsonProduct.getInt("user_id_send");
                                String content = jsonProduct.getString("content");
                                int friend  = jsonProduct.getInt("user_id_recived");
                                String date = jsonProduct.getString("timeStamp");

                                Message message = new Message(content,id_user,friend,date);

                                productList.add(message);
                                userIds.add(id_user);

                            }
                                adapterChatMain.setMessages(productList);
                                getUserNamesForMessage(accessToken,userIds,context);



                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("resposta", "Hi ha hagut un error: " + error);
                    }
                }
                ) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                headers.put("Authorization", "Bearer " + accessToken);
                return headers;
            }
        };
        queue.add(jsonArrayRequest);
    }
    private void getUserNamesForMessage(String accessToken, List<Integer> ids,Context context){
        ApiCalls apiCalls = new ApiCalls(context,adapterChatMain);
        for(int id: ids){
            apiCalls.getUserById(accessToken,id,context);
        }

    }

    public void getProductByID(String accessToken, int productId, Context context) {
        RequestQueue queue = Volley.newRequestQueue(context);
        String url = "https://balandrau.salle.url.edu/i3/mercadoexpress/api/v1/products/" + productId;

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        JSONObject jsonProduct = response;
                        try {
                            Log.e("resposta", "La resposta es: " + response.toString());
                            Product product;
                            int[] categoriaArray = new int[0];

                            if(jsonProduct.getJSONArray("categoryIds")==null){
                                categoriaArray = new int[0];
                                categoriaArray[0]=(0);
                            }else{
                                JSONArray categoria = jsonProduct.getJSONArray("categoryIds");
                                categoriaArray = new int[categoria.length()];
                                for (int j = 0; j < categoria.length(); j++) {
                                    categoriaArray[j] = categoria.getInt(j);
                                }
                            }
                            product = new Product(
                                    jsonProduct.getInt("id"),
                                    jsonProduct.getString("name"),
                                    jsonProduct.getString("description"),
                                    Float.parseFloat(jsonProduct.getString("price")),
                                    jsonProduct.getString("link"),
                                    jsonProduct.getString("photo"),
                                    categoriaArray);
                            adapter.addProducts(product);
                        } catch (JSONException e) {
                            throw new RuntimeException(e);

                        }

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("resposta", "Hi ha hagut un error: " + error);
                    }
                }
                ) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                headers.put("Authorization", "Bearer " + accessToken);
                return headers;
            }
        };
        queue.add(jsonObjectRequest);
    }
    public void getProductByID2(String accessToken, int productId, Context context) {
        RequestQueue queue = Volley.newRequestQueue(context);
        String url = "https://balandrau.salle.url.edu/i3/mercadoexpress/api/v1/products/" + productId;

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        JSONObject jsonProduct = response;
                        try {
                            Product product;
                            int[] categoriaArray = new int[0];

                            if(jsonProduct.getJSONArray("categoryIds")==null){
                                categoriaArray = new int[0];
                                categoriaArray[0]=(0);
                            }else{
                                JSONArray categoria = jsonProduct.getJSONArray("categoryIds");
                                categoriaArray = new int[categoria.length()];
                                for (int j = 0; j < categoria.length(); j++) {
                                    categoriaArray[j] = categoria.getInt(j);
                                }
                            }
                            product = new Product(
                                    jsonProduct.getInt("id"),
                                    jsonProduct.getString("name"),
                                    jsonProduct.getString("description"),
                                    Float.parseFloat(jsonProduct.getString("price")),
                                    jsonProduct.getString("link"),
                                    jsonProduct.getString("photo"),
                                    categoriaArray);

                            adapterGift.addProducts(product);

                        } catch (JSONException e) {
                            throw new RuntimeException(e);

                        }

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("resposta", "Hi ha hagut un error: " + error);
                    }
                }
                ) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                headers.put("Authorization", "Bearer " + accessToken);
                return headers;
            }
        };
        queue.add(jsonObjectRequest);
    }
    public void getProductByIDForFriend(String accessToken, int productId, Context context) {
        RequestQueue queue = Volley.newRequestQueue(context);
        String url = "https://balandrau.salle.url.edu/i3/mercadoexpress/api/v1/products/" + productId;

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        JSONObject jsonProduct = response;
                        try {
                            Product product;
                            int[] categoriaArray = new int[0];

                            if(jsonProduct.getJSONArray("categoryIds")==null){
                                categoriaArray = new int[0];
                                categoriaArray[0]=(0);
                            }else{
                                JSONArray categoria = jsonProduct.getJSONArray("categoryIds");
                                categoriaArray = new int[categoria.length()];
                                for (int j = 0; j < categoria.length(); j++) {
                                    categoriaArray[j] = categoria.getInt(j);
                                }
                            }
                            product = new Product(
                                    jsonProduct.getInt("id"),
                                    jsonProduct.getString("name"),
                                    jsonProduct.getString("description"),
                                    Float.parseFloat(jsonProduct.getString("price")),
                                    jsonProduct.getString("link"),
                                    jsonProduct.getString("photo"),
                                    categoriaArray);

                            adapterGiftFriend.addProducts(product);

                        } catch (JSONException e) {
                            throw new RuntimeException(e);

                        }

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("resposta", "Hi ha hagut un error: " + error);
                    }
                }
                ) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                headers.put("Authorization", "Bearer " + accessToken);
                return headers;
            }
        };
        queue.add(jsonObjectRequest);
    }
    public void createProduct(String accessToken, Product product,Context context) {
        RequestQueue queue = Volley.newRequestQueue((Context) MainActivity);
        String url = "https://balandrau.salle.url.edu/i3/mercadoexpress/api/v1/products";

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.POST, url, product.getProduct(), new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Log.e("resposta", "La resposta es: " + response.toString());
                        //Obtenim tots els usuaris en format json
                        Intent intent = new Intent(context, MainActivity.class);
                        context.startActivity(intent);

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("resposta", "Hi ha hagut un error: " + error.networkResponse.statusCode);
                    }
                }
                ) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                headers.put("Authorization", "Bearer " + accessToken);
                return headers;
            }
        };
        queue.add(jsonObjectRequest);
    }

    public void getCategoryList(String accessToken, Response.Listener<JSONArray> listener) {
        RequestQueue queue = Volley.newRequestQueue((Context) MainActivity);
        String url = "https://balandrau.salle.url.edu/i3/mercadoexpress/api/v1/categories";

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONArray>() {

                    @Override
                    public void onResponse(JSONArray response) {
                        listener.onResponse(response);
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("resposta", "Hi ha hagut un error: " + error.networkResponse.statusCode);
                    }
                }
                ) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                headers.put("Authorization", "Bearer " + accessToken);
                return headers;
            }
        };
        queue.add(jsonArrayRequest);
    }
/*
    public void getCategoryById(String accessToken, int category) {
        RequestQueue queue = Volley.newRequestQueue((Context) MainActivity);
        String url = "https://balandrau.salle.url.edu/i3/mercadoexpress/api/v1/categories/" + category;

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Log.e("resposta", "La resposta es: " + response.toString());

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("resposta", "Hi ha hagut un error: " + error);
                    }
                }
                ) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                headers.put("Authorization", "Bearer " + accessToken);
                return headers;
            }
        };
        queue.add(jsonObjectRequest);
    }


   public void editProduct(String accessToken, Product product) {
        RequestQueue queue = Volley.newRequestQueue((Context) MainActivity);
        String url = "https://balandrau.salle.url.edu/i3/mercadoexpress/api/v1/products";

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.PUT, url,  product.editProduct(), new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Log.e("resposta", "La resposta es: "+ response.toString());
                        //Obtenim tots els usuaris en format json

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("resposta", "Hi ha hagut un error:" + error);
                    }
                }
                ) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                headers.put("Authorization", "Bearer " + accessToken);
                return headers;
            }
        };
        queue.add(jsonObjectRequest);
    }


    Funció per obtenir tots els regals
    Cal haver fet LogIn per obtenir el accessToken
    A la funcio se li passa:
        - accessToken

   public void getAllGifts(String accessToken) {
       RequestQueue queue = Volley.newRequestQueue((Context) MainActivity);
       String url ="https://balandrau.salle.url.edu/i3/socialgift/api/v1/gifts";

       JsonArrayRequest jsonArrayRequest = new JsonArrayRequest
               (Request.Method.GET, url, null, new Response.Listener<JSONArray>() {

                   @Override
                   public void onResponse(JSONArray response) {
                       Log.e("resposta", "La resposta es: "+ response.toString());
                       //Obtenim tots els usuaris en format json

                   }
               }, new Response.ErrorListener() {
                   @Override
                   public void onErrorResponse(VolleyError error) {
                       Log.e("resposta", "Hi ha hagut un error:" + error);
                   }
               }
               ) {
           @Override
           public Map<String, String> getHeaders() throws AuthFailureError {
               Map<String, String> headers = new HashMap<>();
               headers.put("Authorization", "Bearer " + accessToken);
               return headers;
           }
       };
       queue.add(jsonArrayRequest);
   }


    Funció per obtenir l'usuari en funcio del id d'un gift
    Cal haver fet LogIn per obtenir el accessToken
    A la funcio se li passa:
        - accessToken
        - id

    public void getUserByGiftId(String accessToken, int id) {
        RequestQueue queue = Volley.newRequestQueue((Context) MainActivity);
        String url ="https://balandrau.salle.url.edu/i3/socialgift/api/v1/gifts/" + id + "/user";

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Log.e("resposta", "La resposta es: "+ response.toString());
                        //Obtenim tots els usuaris en format json

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("resposta", "Hi ha hagut un error:" + error);
                    }
                }
                ) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                headers.put("Authorization", "Bearer " + accessToken);
                return headers;
            }
        };
        queue.add(jsonObjectRequest);
    }
      public void deleteProductById(String accessToken, int id) {
        RequestQueue queue = Volley.newRequestQueue((Context) MainActivity);
        String url = "https://balandrau.salle.url.edu/i3/mercadoexpress/api/v1/products"+ id;

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.DELETE, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Log.e("resposta", "La resposta es: " + response.toString());
                        //Obtenim tots els usuaris en format json

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("resposta", "Hi ha hagut un error: " + error);
                    }
                }
                ) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                headers.put("Authorization", "Bearer " + accessToken);
                return headers;
            }
        };
        queue.add(jsonObjectRequest);
    }

     public void searchProduct(String accessToken, String search) {
        RequestQueue queue = Volley.newRequestQueue((Context) MainActivity);
        String url = "https://balandrau.salle.url.edu/i3/mercadoexpress/api/v1/products/search?s=" + search;


        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONArray>() {

                    @Override
                    public void onResponse(JSONArray response) {
                        Log.e("resposta", "La resposta es: " + response.toString());
                        //Obtenim tots els usuaris en format json

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("resposta", "Hi ha hagut un error:" + error);
                    }
                }
                ) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                headers.put("Authorization", "Bearer " + accessToken);
                return headers;
            }
        };
        queue.add(jsonArrayRequest);
    }
    public void getAllWishList(String accessToken,Response.Listener<JSONArray> listener) {
        RequestQueue queue = Volley.newRequestQueue((Context) MainActivity);
        String url = "https://balandrau.salle.url.edu/i3/socialgift/api/v1/wishlists";

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONArray>() {

                    @Override
                    public void onResponse(JSONArray response) {
                        Log.e("resposta", "La resposta es: " + response.toString());
                        //Obtenim tots els usuaris en format json
                        listener.onResponse(response);

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("resposta", "Hi ha hagut un error: " + error);
                    }
                }
                ) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                headers.put("Authorization", "Bearer " + accessToken);
                return headers;
            }
        };
        queue.add(jsonArrayRequest);
    }
   public void deleteUser(String accessToken) {
       RequestQueue queue = Volley.newRequestQueue((Context) MainActivity);
       String url = "https://balandrau.salle.url.edu/i3/socialgift/api/v1/users";

       JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
               (Request.Method.DELETE, url, null, new Response.Listener<JSONObject>() {

                   @Override
                   public void onResponse(JSONObject response) {
                       Log.e("resposta", "La resposta es: " + response.toString());
                       //Obtenim tots els usuaris en format json

                   }
               }, new Response.ErrorListener() {
                   @Override
                   public void onErrorResponse(VolleyError error) {
                       Log.e("resposta", "Hi ha hagut un error:" + error);
                   }
               }
               ) {
           @Override
           public Map<String, String> getHeaders() throws AuthFailureError {
               Map<String, String> headers = new HashMap<>();
               headers.put("Authorization", "Bearer " + accessToken);
               return headers;
           }
       };
       queue.add(jsonObjectRequest);
   }

    public void createCategory  (String accessToken, Category category) {
        RequestQueue queue = Volley.newRequestQueue((Context) MainActivity);
        String url = "https://balandrau.salle.url.edu/i3/mercadoexpress/api/v1/categories";

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.POST, url, category.getCategory(), new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Log.e("resposta", "La resposta es: " + response.toString());
                        //Obtenim tots els usuaris en format json

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("resposta", "Hi ha hagut un error: " + error.networkResponse.statusCode);
                    }
                }
                ) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                headers.put("Authorization", "Bearer " + accessToken);
                return headers;
            }
        };
        queue.add(jsonObjectRequest);
    }
    public void editCategory(String accessToken, Category category) {
        RequestQueue queue = Volley.newRequestQueue((Context) MainActivity);
        String url = "https://balandrau.salle.url.edu/i3/mercadoexpress/api/v1/categories";

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.PUT, url, category.getCategory(), new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Log.e("resposta", "La resposta es: "+ response.toString());
                        //Obtenim tots els usuaris en format json

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("resposta", "Hi ha hagut un error:" + error);
                    }
                }
                ) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                headers.put("Authorization", "Bearer " + accessToken);
                return headers;
            }
        };
        queue.add(jsonObjectRequest);
    }
    public void deleteCategoryById(String accessToken, int id) {
        RequestQueue queue = Volley.newRequestQueue((Context) MainActivity);
        String url = "https://balandrau.salle.url.edu/i3/mercadoexpress/api/v1/categories"+ id;

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.DELETE, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Log.e("resposta", "La resposta es: " + response.toString());
                        //Obtenim tots els usuaris en format json

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("resposta", "Hi ha hagut un error: " + error);
                    }
                }
                ) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                headers.put("Authorization", "Bearer " + accessToken);
                return headers;
            }
        };
        queue.add(jsonObjectRequest);
    }
*/

}
