package co.pizzayum.pizzayum_android.classes;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import co.pizzayum.pizzayum_android.R;
import co.pizzayum.pizzayum_android.utility.AppController;

public class Login extends AppCompatActivity implements View.OnClickListener {

    // Layout Views
    Button login_button;
    TextView email_label, password_label, continue_with_label, fb_button_label,
            google_button_label, signup_url;
    EditText user_email, user_password;
    LinearLayout fb_login, google_login;

    Typeface customFontStyle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_login);

        // initialize layout views
        // Button
        login_button = findViewById(R.id.login_button);

        // TextViews
        password_label = findViewById(R.id.password_label);
        email_label = findViewById(R.id.email_label);
        continue_with_label = findViewById(R.id.continue_with_label);
        fb_button_label = findViewById(R.id.fb_button_label);
        google_button_label = findViewById(R.id.google_button_label);
        signup_url = findViewById(R.id.signup_url);

        // EditTexts
        user_email = findViewById(R.id.user_email);
        user_password = findViewById(R.id.user_password);

        // Linear Layouts
        fb_login = findViewById(R.id.fb_login);
        google_login = findViewById(R.id.google_login);

        // initialise custom fonts
        initializeCustomFonts();

        login_button.setOnClickListener(this);
        signup_url.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.signup_url:
                signupUser();
                break;
            case R.id.login_button:
                Toast.makeText(this, "LoginButtonClicked", Toast.LENGTH_SHORT).show();
                loginUser();
                break;
        }
    }

    /**
     * Login user after validation process
     */
    void loginUser(){
        String email = user_email.getText().toString();
        String password = user_password.getText().toString();
        Log.e("TAG","String");
        apiCall();
    }

    void apiCall() {
        //Toast.makeText(this, "ApiCall", Toast.LENGTH_SHORT).show();
        String  tag_string_req = "string_req";

        String url = "http://www.pizzayum.co/api/login";

        final ProgressDialog pDialog = new ProgressDialog(this);
        pDialog.setMessage("Loading...");
        pDialog.show();

        StringRequest strReq = new StringRequest(Request.Method.POST,
                url, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d("Login", response.toString());
                pDialog.hide();
                Toast.makeText(Login.this, "Login Success", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(Login.this, HomePage.class));
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("Login", "Error: " + error.getMessage());
                Toast.makeText(Login.this, "Sorry Login Faild" + error.getMessage(), Toast.LENGTH_SHORT).show();
                pDialog.hide();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("email", user_email.getText().toString());
                params.put("password", user_password.getText().toString());
                return params;
            }
        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }

    /**
     * Signup Usr will redirect user to Signup Page
     */
    void signupUser(){
        startActivity(new Intent(Login.this, Signup.class));
    }

    /**
     * Assigning custom fonts style and set that custom font to textViews
     */
    void initializeCustomFonts() {
        customFontStyle = Typeface.createFromAsset(getAssets(), "fonts/Athiti-Regular.ttf");

        // textViews
        password_label.setTypeface(customFontStyle);
        email_label.setTypeface(customFontStyle);
        continue_with_label.setTypeface(customFontStyle);
        fb_button_label.setTypeface(customFontStyle);
        signup_url.setTypeface(customFontStyle);

        // EditTexts
        user_email.setTypeface(customFontStyle);
        user_password.setTypeface(customFontStyle);

        customFontStyle = Typeface.createFromAsset(getAssets(), "fonts/Athiti-SemiBold.ttf");
        fb_button_label.setTypeface(customFontStyle);
        google_button_label.setTypeface(customFontStyle);

        // click listeners
        signup_url.setOnClickListener(this);
        login_button.setOnClickListener(this);
    }

}
