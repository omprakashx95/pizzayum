package co.pizzayum.pizzayum_android.services;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.HashMap;
import java.util.Map;

import co.pizzayum.pizzayum_android.activities.MainActivity;
import co.pizzayum.pizzayum_android.models.ErrorResponse;
import co.pizzayum.pizzayum_android.models.LoginModel;
import co.pizzayum.pizzayum_android.utility.AppController;
import co.pizzayum.pizzayum_android.utility.SessionManager;

/**
 * This is a service class which is used to make some http requests to the server for making some
 * network calls
 * We are making some calls like:
 * 1- MainActivity Api
 * 2- Register Api
 * 3- Social Network MainActivity Api
 */
public class Authentication {

    private int which_request;
    private String username, email, password;
    private Activity activity;
    private ProgressDialog loading = null;
    SessionManager session;

    /**
     * Class Constructor, here we are save locally some login information and a request identifier
     * to make a correct request
     */
    public Authentication(Activity activity, int which_request, String username, String email,
                          String password) {
        this.activity = activity;
        this.which_request = which_request;
        this.username = username;
        this.email = email;
        this.password = password;
        session = new SessionManager(activity);
    }

    /**
     * This loginUser method is used here to login user in our app after the authentication process
     */
    public void networkCall() {

        // loader
        loading = new ProgressDialog(activity);
        loading.setCancelable(false);
        loading.setMessage("Processing");
        loading.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        loading.show();

        String tag_string_req = "string_req";
        String url = "http://www.pizzayum.co/api/";

        // checking request identifier
        if (which_request == 1) {  // 1 => MainActivity Request
            url = url + "login";
        } else if (which_request == 2) {  // 2 => Register Request
            url = url + "register";
        }

        StringRequest strReq = new StringRequest(
                Request.Method.POST,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("Auth", "" + which_request + " res: " + response);
                        loading.dismiss();

                        //Initialize gson obj to process jason response
                        GsonBuilder gsonBuilder = new GsonBuilder();
                        Gson gson = gsonBuilder.create();

                        // assigning data in model class, we initializing this class as a array type
                        // because the response is in array format
                        if (response.contains("message")) {
                            // show error in form of message
                            ErrorResponse error = gson.fromJson(response, ErrorResponse.class);
                            Toast.makeText(activity, "Message" + error.getMessage(), Toast.LENGTH_SHORT).show();
                        } else {
                            LoginModel getResult = gson.fromJson(response, LoginModel.class);
                            session.createLoginSession(getResult.getAccessToken(), email);
                            activity.startActivity(new Intent(activity, MainActivity.class));
                            activity.finish();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        loading.dismiss();
                        Toast.makeText(activity, "Authentication Failed", Toast.LENGTH_SHORT).show();
                        VolleyLog.d("MainActivity", "Error: " + error.getMessage());
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                if (which_request == 2) {
                    params.put("name", username);
                }
                params.put("email", email);
                params.put("password", password);
                return params;
            }
        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }
}
