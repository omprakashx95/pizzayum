package co.pizzayum.pizzayum_android.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;

import co.pizzayum.pizzayum_android.R;
import co.pizzayum.pizzayum_android.models.AddressResponse;
import co.pizzayum.pizzayum_android.utility.PizzaConstants;
import co.pizzayum.pizzayum_android.utility.SessionManager;

public class PaymentOptions extends AppCompatActivity {
    LinearLayout cash_method_view;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.payment_options);
        cash_method_view = findViewById(R.id.cash_method);

        cash_method_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(PaymentOptions.this, Invoice.class));
                finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
        deleteOrder();
    }

    void deleteOrder() {
        final SessionManager sessionManager = new SessionManager(this);
        String url = "http://www.pizzayum.co/api/deleteorder";
        final ProgressDialog loading = new ProgressDialog(this);
        loading.setCancelable(false);
        loading.setMessage("Processing");
        loading.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        loading.show();
        try {
            JSONObject jsonBody = new JSONObject();
            jsonBody.put("order_id", PizzaConstants.ORDER_ID);
            jsonBody.put("email", sessionManager.returnEmail());
            final String requestBody = jsonBody.toString();
            Log.e("Log", "auth: " + requestBody);
            JsonObjectRequest request = new JsonObjectRequest(
                    Request.Method.POST,        // Post method
                    url,
                    null, // Parameters
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            loading.dismiss();
                            finish();
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.e("Response", "Error: " + error);
                        }
                    }) {

                @Override
                public java.util.Map<String, String> getHeaders() {
                    HashMap<String, String> headers = new HashMap<>();
                    headers.put("Accept", "application/json");
                    headers.put("Authorization", sessionManager.returnToken());
                    return headers;
                }

                @Override
                public String getBodyContentType() {
                    return "application/json; charset=utf-8";
                }

                @Override
                public byte[] getBody() {
                    try {
                        return requestBody == null ? null : requestBody.getBytes("utf-8");
                    } catch (UnsupportedEncodingException uee) {
                        VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s", requestBody, "utf-8");
                        return null;
                    }
                }
            };
            RequestQueue queue = Volley.newRequestQueue(this);
            queue.add(request);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
