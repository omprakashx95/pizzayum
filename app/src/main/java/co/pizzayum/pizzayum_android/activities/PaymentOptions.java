package co.pizzayum.pizzayum_android.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

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
import co.pizzayum.pizzayum_android.models.InvoiceModel;
import co.pizzayum.pizzayum_android.models.OrderResponse;
import co.pizzayum.pizzayum_android.models.PizzaOrderTableModel;
import co.pizzayum.pizzayum_android.utility.DatabaseHelper;
import co.pizzayum.pizzayum_android.utility.PizzaConstants;
import co.pizzayum.pizzayum_android.utility.SessionManager;

public class PaymentOptions extends AppCompatActivity implements View.OnClickListener {
    LinearLayout cash_method_view;
    ImageView back_button;
    DatabaseHelper db ;
    SessionManager session ;
    RelativeLayout custom_loader_container;
    ImageView loader_img_view;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.payment_options);
        cash_method_view = findViewById(R.id.cash_method);
        back_button = findViewById(R.id.back_button);
        db = new DatabaseHelper(this);
        session = new SessionManager(this);


        custom_loader_container = findViewById(R.id.custom_loader);
        loader_img_view = findViewById(R.id.loader_view);
        pizzaCustomLoader();
        custom_loader_container.setVisibility(View.INVISIBLE);

        cash_method_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createOrder();

            }
        });

        back_button.setOnClickListener(this);
    }

//    void deleteOrder() {
//        final SessionManager sessionManager = new SessionManager(this);
//        String url = "http://www.pizzayum.co/api/deleteorder";
//        final ProgressDialog loading = new ProgressDialog(this);
//        loading.setCancelable(false);
//        loading.setMessage("Processing");
//        loading.setProgressStyle(ProgressDialog.STYLE_SPINNER);
//        loading.show();
//        try {
//            JSONObject jsonBody = new JSONObject();
//            jsonBody.put("order_id", PizzaConstants.ORDER_ID);
//            jsonBody.put("email", sessionManager.returnEmail());
//            final String requestBody = jsonBody.toString();
//            Log.e("Log", "auth: " + requestBody);
//            JsonObjectRequest request = new JsonObjectRequest(
//                    Request.Method.POST,        // Post method
//                    url,
//                    null, // Parameters
//                    new Response.Listener<JSONObject>() {
//                        @Override
//                        public void onResponse(JSONObject response) {
//                            loading.dismiss();
//                            finish();
//                        }
//                    },
//                    new Response.ErrorListener() {
//                        @Override
//                        public void onErrorResponse(VolleyError error) {
//                            Log.e("Response", "Error: " + error);
//                        }
//                    }) {
//
//                @Override
//                public java.util.Map<String, String> getHeaders() {
//                    HashMap<String, String> headers = new HashMap<>();
//                    headers.put("Accept", "application/json");
//                    headers.put("Authorization", sessionManager.returnToken());
//                    return headers;
//                }
//
//                @Override
//                public String getBodyContentType() {
//                    return "application/json; charset=utf-8";
//                }
//
//                @Override
//                public byte[] getBody() {
//                    try {
//                        return requestBody == null ? null : requestBody.getBytes("utf-8");
//                    } catch (UnsupportedEncodingException uee) {
//                        VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s", requestBody, "utf-8");
//                        return null;
//                    }
//                }
//            };
//            RequestQueue queue = Volley.newRequestQueue(this);
//            queue.add(request);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

    void pizzaCustomLoader() {
        Animation rotation = AnimationUtils.loadAnimation(this, R.anim.rotation_animation);
        loader_img_view.startAnimation(rotation);
    }

    String requestJsonGenerator() {
        String temp = "[";
        for (int i = 0; i < db.getAllOrders().size(); i++) {
            PizzaOrderTableModel model = db.getAllOrders().get(i);
            temp += "{";
            temp += "\"productID\":" + model.getProduct_id();
            temp += ",\"size\":\"" + model.getSize() + "\"";
            temp += ",\"quantity\":" + model.getProduct_quantity();

            if (nullChecker(model.getCrust_id()))
                temp += ",\"crust_id\":" + model.getCrust_id();
            if (nullChecker(model.getTopping_id())) {
                temp += ",\"topping_id\":" + model.getTopping_id();
                temp += ",\"topping_detail\":\"" + model.getTopping_details() + "\"";
                temp += ",\"topping_qty\":" + model.getTopping_counter();
            }
            if (nullChecker(model.getExtra_cheese_id()))
                temp += ",\"extra_cheese\":" + model.getExtra_cheese_id();

            if (i == db.getAllOrders().size() - 1) {
                temp += "}";
            } else {
                temp += "},";
            }
        }
        temp += "]";

        Log.e("generated", "Created Record: " + temp);
        return temp;
        // return "[{\"productID\": 1,\"size\":\"regular\",\"quantity\":2}]";
    }

    boolean nullChecker(String a) {
        if (a == null) {
            return false;
        } else {
            return true;
        }
    }

    void createOrder() {
        custom_loader_container.setVisibility(View.VISIBLE);

        String url = "http://www.pizzayum.co/api/order";

        try {
            JSONObject jsonBody = new JSONObject();
            jsonBody.put("data", requestJsonGenerator());
            jsonBody.put("email", session.returnEmail());
            final String requestBody = jsonBody.toString();
            Log.e("Log", "auth: " + requestBody);
            JsonObjectRequest request = new JsonObjectRequest(
                    Request.Method.POST,        // Post method
                    url,
                    null, // Parameters
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            GsonBuilder gsonBuilder = new GsonBuilder();
                            Gson gson = gsonBuilder.create();

                            // assigning data in model class, we initializing this class as a array type
                            // because the response is in array format
                            OrderResponse model = gson.fromJson(response.toString(), OrderResponse.class);
                            PizzaConstants.ORDER_ID = model.getOrderId();
                            Log.e("ORDERID", "ORDERID:" + model.getOrderId());
                            //getActivity().startActivity(new Intent(getActivity(), PaymentOptions.class));

                            createInvoice();
                            Log.e("Response", "Response: " + response.toString());
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
                    headers.put("Authorization", session.returnToken());
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

    void createInvoice() {
        String url = "http://www.pizzayum.co/api/invoice";
        try {
            JSONObject jsonBody = new JSONObject();
            jsonBody.put("order_id", PizzaConstants.ORDER_ID);
            jsonBody.put("email", session.returnEmail());
            final String requestBody = jsonBody.toString();
            Log.e("Log", "auth: " + requestBody);
            //progress_bar_view.setVisibility(View.VISIBLE);
            JsonObjectRequest request = new JsonObjectRequest(
                    Request.Method.POST,        // Post method
                    url,
                    null, // Parameters
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            Log.e("Response", "Response: " + response.toString());
                            //Initialize gson obj to process jason response
                            GsonBuilder gsonBuilder = new GsonBuilder();
                            Gson gson = gsonBuilder.create();
                            custom_loader_container.setVisibility(View.INVISIBLE);
                            //InvoiceModel model = gson.fromJson(response.toString(), InvoiceModel.class);
                            PizzaConstants.INVOICE_MODEL = gson.fromJson(response.toString(), InvoiceModel.class);

                            startActivity(new Intent(PaymentOptions.this, Invoice.class));
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
                    headers.put("Authorization", session.returnToken());
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

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.back_button:
                finish();

        }
    }
}
