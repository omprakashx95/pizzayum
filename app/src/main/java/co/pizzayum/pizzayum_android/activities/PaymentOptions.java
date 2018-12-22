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
        final String authorization_value = "Bearer  eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiIsImp0aSI6ImI0MjNlNDg2ZTBkM2ZhNzNmNzJlNzc1NTk5YjdmZGJlZGQ3NmFkNWRhYjBiYmJiZWI1MjZmNjI0MjcwNDE3ZmQyMDliNjA1Yzc3ZDc2Y2E1In0.eyJhdWQiOiIyIiwianRpIjoiYjQyM2U0ODZlMGQzZmE3M2Y3MmU3NzU1OTliN2ZkYmVkZDc2YWQ1ZGFiMGJiYmJlYjUyNmY2MjQyNzA0MTdmZDIwOWI2MDVjNzdkNzZjYTUiLCJpYXQiOjE1NDI4NTQyMjQsIm5iZiI6MTU0Mjg1NDIyNCwiZXhwIjoxNTc0MzkwMjIzLCJzdWIiOiI0Iiwic2NvcGVzIjpbXX0.Q4zdNWIhiF-wpf_HmNjw01pho4QyldsQsDsb0GYIWjaxekpFqJ5s2Bb3cRPtbviIQIVTl_2vAjdNt3Dy-qVAgokY-AJXuRJlu3q_iugwUXu6VsRaYwT3-Q3zz4GWPjbzskvL_dGHE7zj3_W-wFmR-RHwI1rMtg5TK2WbP5j_dupwGBIBvl9eouVjiUxSj4LuAT1UjW7UP_dnuomiv-jPkAfGvAPPp4HSoyraOEyT7BbqIKS_BE2bKvyUBjg61UA7km-sXEgXyR6WJcYy5txnrn3T52KffGh9EvFbV_u9nnMq1tT_inAA-KkDcvVTCKFNTI7VxD_8aFAAc0SkIAdQQ-O6YLGYsVglGWKXsL_X9GkDadz5k9ZJNW-TVizy1Fb37HZgOKvx6ILN51RD2AmWr5q7VLPcFM3-W8c5Xoox2WWEbIbJIJmT8ReT4B0jy63lJCXaTNopCVZetmTv63MGUQpPKilTEOCE9dxv3lNp_qMH9Ny_1XR8eUzotnz5Athg-DMNm2a1peCCqxa8hZAD-pCiBd4lnH2U3CzOimXKiTDOAlZcUuzjpYtSAdGCtpb4PxOeFZGXzW4-USDBGLnI0mrMRwJXquqGyIeAMEtdXvHsoNguT-9r86CKPeHAfje-2_VrmE9E_wWY6KxOF6-x9UrY8NtzWNRDkWAxfT_TGas";
        String url = "http://www.pizzayum.co/api/deleteorder";
        final ProgressDialog loading = new ProgressDialog(this);
        loading.setCancelable(false);
        loading.setMessage("Processing");
        loading.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        loading.show();
        try {
            JSONObject jsonBody = new JSONObject();
            jsonBody.put("order_id", PizzaConstants.ORDER_ID);
            jsonBody.put("email", "rishabh19910623@gmail.com");
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
                    headers.put("Authorization", authorization_value);
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
