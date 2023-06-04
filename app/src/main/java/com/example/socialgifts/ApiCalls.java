package com.example.socialgifts;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.socialgifts.activities.MainActivity;
import com.example.socialgifts.adapters.FeedAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ApiCalls {
    private Object MainActivity;
    private String accessToken;
    private Context context;
    private FeedAdapter adapter; // Asegúrate de declarar el objeto adapter aquí

    public ApiCalls(Context context, FeedAdapter adapter) {
        this.context = context;
        this.adapter = adapter;
    }


    public ApiCalls(Object mainActivity) {
        MainActivity = mainActivity;

    }


    public Object getMainActivity() {
        return MainActivity;
    }

    public void setMainActivity(Object mainActivity) {
        MainActivity = mainActivity;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    /******************************
     * CRIDES API USER
     ******************************/

    /*
        Funció per a registrar el usuari
        Se li passa un usuari amb:
            - name
            - last_name
            - email
            - password
            - image
        */
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

    /*
    Funció per fer LogIn
    Se li passa un usuari amb:
        - email
        - password
    S'obté el accessToken
     */
    public void  loginUser(User user,Context context) {
        RequestQueue queue = Volley.newRequestQueue((context));
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.POST, "https://balandrau.salle.url.edu/i3/socialgift/api/v1/users/login", user.getUsuariLogin(), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    accessToken = response.get("accessToken").toString();
                    SharedPreferences sharedPreferences = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("accessToken", accessToken);
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


    /*
    Funció per obtenir un usuari en funcio del id
    Cal haver fet LogIn per obtenir el accessToken
    A la funcio se li passa:
        - accessToken
        - id
    La funcio retorna
        - User
     */
    public void getUserById(String accessToken, int id) {
        RequestQueue queue = Volley.newRequestQueue((Context) MainActivity);
        String url = "https://balandrau.salle.url.edu/i3/socialgift/api/v1/users/" + id;

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

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

    /*
    Funció per obtenir tots els usuaris
    Cal haver fet LogIn per obtenir el accessToken
    A la funcio se li passa:
        - accessToken
    La funcio retorna
        - Array de users
     */
    public void getAllUsers(String accessToken) {
        RequestQueue queue = Volley.newRequestQueue((Context) MainActivity);
        String url = "https://balandrau.salle.url.edu/i3/socialgift/api/v1/users";

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

    /*
    Funció per buscar un usuari
    Cal haver fet LogIn per obtenir el accessToken
    A la funcio se li passa:
        - accessToken
        - String per a la cerca
    La funcio retorna
        - Array de users
     */
    public void searchUser(String accessToken, String search) {
        RequestQueue queue = Volley.newRequestQueue((Context) MainActivity);
        String url = "https://balandrau.salle.url.edu/i3/socialgift/api/v1/users/search?s=" + search;

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

    /*
    Funció per obtenir els friends d'un usuari en funcio del seu id
    Cal haver fet LogIn per obtenir el accessToken
    A la funcio se li passa:
        - accessToken
        - id
    La funcio retorna
        - Array de users
     */
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

    /*
    Funció per obtenir els regals que un usuari ha reservat en funcio del id de l'usuari
    Cal haver fet LogIn per obtenir el accessToken
    A la funcio se li passa:
        - accessToken
        - id
    La funcio retorna
        - Array de gifts
     */
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

    /*
    Funció per obtenir tota la wishlist en funcio del id de l'usuari en questio
    Cal haver fet LogIn per obtenir el accessToken
    A la funcio se li passa:
        - accessToken
        - id
    La funcio retorna
        - Array de wishlists
     */
    public void getAllUserWhishlist(String accessToken, int id) {
        RequestQueue queue = Volley.newRequestQueue((Context) MainActivity);
        String url = "https://balandrau.salle.url.edu/i3/socialgift/api/v1/users/" + id + "/wishlists";

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

    /*
    Funció per eliminar l'usuari que ha fet login
    Cal haver fet LogIn per obtenir el accessToken
    A la funcio se li passa:
        - accessToken
     */
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

    /*
    Funció per editar l'usuari que ha fet login
    Cal haver fet LogIn per obtenir el accessToken
    A la funcio se li passa:
        - accessToken
        - new User
     */
    public void editUser(String accessToken, User user) {
        RequestQueue queue = Volley.newRequestQueue((Context) MainActivity);
        String url = "https://balandrau.salle.url.edu/i3/socialgift/api/v1/users";

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.PUT, url, user.getUsuariTotal(), new Response.Listener<JSONObject>() {

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

    /******************************
     * CRIDES API GIFTS
     ******************************/

    /******************************
     * CRIDES API GIFTS
     ******************************/

    public void createGift(String accessToken, Gift gift) {
        RequestQueue queue = Volley.newRequestQueue((Context) MainActivity);
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

    /*
    Funció per obtenir tots els regals
    Cal haver fet LogIn per obtenir el accessToken
    A la funcio se li passa:
        - accessToken
     */
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

    /*
    Funció per obtenir l'usuari en funcio del id d'un gift
    Cal haver fet LogIn per obtenir el accessToken
    A la funcio se li passa:
        - accessToken
        - id
     */
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

    /*
    Funció per obtenir un Gift en funció del seu id
    Cal haver fet LogIn per obtenir el accessToken
    A la funcio se li passa:
        - accessToken
        - id
     */
    public void getGiftById(String accessToken, int id) {
        RequestQueue queue = Volley.newRequestQueue((Context) MainActivity);
        String url ="https://balandrau.salle.url.edu/i3/socialgift/api/v1/gifts/" + id;

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

    /*
    Funció per eliminar un Gift en funció del seu id
    Cal haver fet LogIn per obtenir el accessToken
    A la funcio se li passa:
        - accessToken
        - id
     */
    public void deleteGiftById(String accessToken, int id) {
        RequestQueue queue = Volley.newRequestQueue((Context) MainActivity);
        String url ="https://balandrau.salle.url.edu/i3/socialgift/api/v1/gifts/" + id;

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.DELETE, url, null, new Response.Listener<JSONObject>() {

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

    /******************************
     * CRIDES API WISHLISTS
     ******************************/

    /*
    Funció per crear una wishlist
    Cal haver fet LogIn per obtenir el accessToken
    A la funcio se li passa:
        - accessToken
        - new wishlist
     */
    public void createWishList(String accessToken, WishList wishList) {
        RequestQueue queue = Volley.newRequestQueue((Context) MainActivity);
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

    /*
    Funció per obtenir totes les wishlists
    Cal haver fet LogIn per obtenir el accessToken
    A la funcio se li passa:
        - accessToken
     */
    public void getAllWishList(String accessToken) {
        RequestQueue queue = Volley.newRequestQueue((Context) MainActivity);
        String url = "https://balandrau.salle.url.edu/i3/socialgift/api/v1/wishlists";

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

    /*
    Funció per obtenir una wishlist en funcio del seu id
    Cal haver fet LogIn per obtenir el accessToken
    A la funcio se li passa:
        - accessToken
        - int id
     */
    public void getWishlistById(String accessToken, int id) {
        RequestQueue queue = Volley.newRequestQueue((Context) MainActivity);
        String url = "https://balandrau.salle.url.edu/i3/socialgift/api/v1/wishlists/" + id;

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

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
    Funció per eliminar una wishlist
    Cal haver fet LogIn per obtenir el accessToken
    A la funcio se li passa:
        - accessToken
        - int id
     */
    public void deleteWishlistById(String accessToken, int id) {
        RequestQueue queue = Volley.newRequestQueue((Context) MainActivity);
        String url = "https://balandrau.salle.url.edu/i3/socialgift/api/v1/wishlists/" + id;

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

    /*
    Funció per editar una wishlist
    Cal haver fet LogIn per obtenir el accessToken
    A la funcio se li passa:
        - accessToken
        - int id
     */
    public void editWishlist(String accessToken, int id, WishList wishList) {
        RequestQueue queue = Volley.newRequestQueue((Context) MainActivity);
        String url = "https://balandrau.salle.url.edu/i3/socialgift/api/v1/wishlists/" + id;

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.PUT, url, wishList. getWishListEdit(), new Response.Listener<JSONObject>() {

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

    /******************************
     * CRIDES API MESSAGES
     ******************************/

    /*
    Funció per crear un missatge
    Cal haver fet LogIn per obtenir el accessToken
    A la funcio se li passa:
        - accessToken
        - new Message
     */
    public void newMessage(String accessToken, Message message) {
        RequestQueue queue = Volley.newRequestQueue((Context) MainActivity);
        String url = "https://balandrau.salle.url.edu/i3/socialgift/api/v1/messages";

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.POST, url, message.getMessagePost(), new Response.Listener<JSONObject>() {

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

    /*
    Funció per obtenir tots els usuaris als que s'ha enviat un missatge
    Cal haver fet LogIn per obtenir el accessToken
    A la funcio se li passa:
        - accessToken
        - new Message
     */
    public void getUsersMessaged(String accessToken) {
        RequestQueue queue = Volley.newRequestQueue((Context) MainActivity);
        String url = "https://balandrau.salle.url.edu/i3/socialgift/api/v1/messages/users";

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

    /*
    Funció per obtenir els missatges que s'han intercanviat amb un altre usuari en
    funcio del seu id
    Cal haver fet LogIn per obtenir el accessToken
    A la funcio se li passa:
        - accessToken
        - int id
     */
    public void getMessagesById(String accessToken, int id) {
        RequestQueue queue = Volley.newRequestQueue((Context) MainActivity);
        String url = "https://balandrau.salle.url.edu/i3/socialgift/api/v1/messages/" + id;

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

    /******************************
     * CRIDES API MESSAGES
     ******************************/

    /*
    Funció per crear un missatge
    Cal haver fet LogIn per obtenir el accessToken
    A la funcio se li passa:
        - accessToken
        - new Message
     */
    public void getFriends(String accessToken) {
        RequestQueue queue = Volley.newRequestQueue((Context) MainActivity);
        String url = "https://balandrau.salle.url.edu/i3/socialgift/api/v1/friends";

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

    /*
    Funció per crear un missatge
    Cal haver fet LogIn per obtenir el accessToken
    A la funcio se li passa:
        - accessToken
        - new Message
     */
    public void getFriendsRequest(String accessToken) {
        RequestQueue queue = Volley.newRequestQueue((Context) MainActivity);
        String url = "https://balandrau.salle.url.edu/i3/socialgift/api/v1/friends/requests";

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

    /*
    Funció per crear un missatge
    Cal haver fet LogIn per obtenir el accessToken
    A la funcio se li passa:
        - accessToken
        - new Message
     */
    public void createFriendRequest(String accessToken, int id) {
        RequestQueue queue = Volley.newRequestQueue((Context) MainActivity);
        String url = "https://balandrau.salle.url.edu/i3/socialgift/api/v1/friends/" + id;

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.POST, url, null, new Response.Listener<JSONObject>() {

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
    public void acceptFriendRequest(String accessToken, int id) {
        RequestQueue queue = Volley.newRequestQueue((Context) MainActivity);
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
    public void rejectFriendRequest(String accessToken, int id) {
        RequestQueue queue = Volley.newRequestQueue((Context) MainActivity);
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

    /**
     * TODO CALL PRODUCTE
     */


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

                                String productName = jsonProduct.getString("name");
                                String productDescription = jsonProduct.getString("description");
                                String photo = jsonProduct.getString("photo");
                                String link = jsonProduct.getString("link");
                                int categoria = jsonProduct.optInt("category", 0);
                                float productPrice = Float.parseFloat(jsonProduct.getString("price"));

                                Product product = new Product(productName, productDescription, productPrice, photo, link, categoria);
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
    public void getProductByID(String accessToken, int productId) {
        RequestQueue queue = Volley.newRequestQueue((Context) MainActivity);
        String url = "https://balandrau.salle.url.edu/i3/mercadoexpress/api/v1/products/" + productId;

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

    public void getCategoryList(String accessToken) {
        RequestQueue queue = Volley.newRequestQueue((Context) MainActivity);
        String url = "https://balandrau.salle.url.edu/i3/mercadoexpress/api/v1/categories";

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

}
