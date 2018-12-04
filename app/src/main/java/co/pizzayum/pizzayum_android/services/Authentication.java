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

import java.util.HashMap;
import java.util.Map;

import co.pizzayum.pizzayum_android.fragments.HomeFragment;
import co.pizzayum.pizzayum_android.utility.AppController;

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
                        if (which_request == 1 && response.contains("access_token")) {
                            Toast.makeText(activity, "MainActivity Success", Toast.LENGTH_SHORT).show();
                            activity.startActivity(new Intent(activity, HomeFragment.class));
                            activity.finish();
                        } else if (which_request == 2 && response.contains("access_token")) {
                            Toast.makeText(activity, "Congratulations !! " + username + " Your account is created", Toast.LENGTH_SHORT).show();
                            activity.startActivity(new Intent(activity, HomeFragment.class));
                            activity.finish();
                        } else {
                            Toast.makeText(activity, "Authentication Failed", Toast.LENGTH_SHORT).show();
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
