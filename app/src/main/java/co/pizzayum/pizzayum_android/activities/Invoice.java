package co.pizzayum.pizzayum_android.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.TextView;

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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import co.pizzayum.pizzayum_android.R;
import co.pizzayum.pizzayum_android.adapters.InvoiceAdapter;
import co.pizzayum.pizzayum_android.models.InvoiceModel;
import co.pizzayum.pizzayum_android.models.OrderDetailItem;
import co.pizzayum.pizzayum_android.utility.DatabaseHelper;
import co.pizzayum.pizzayum_android.utility.PizzaConstants;
import co.pizzayum.pizzayum_android.utility.SessionManager;

public class Invoice extends AppCompatActivity {
    DatabaseHelper db;
    TextView order_total_view;
    RecyclerView invoice_slider_view;
    InvoiceAdapter invoiceAdapter;
    List<OrderDetailItem> invoice_slider_data ;
    TextView toolbar_title_view;
    TextView token_no_view;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        db = new DatabaseHelper(this);
        invoice_slider_data = new ArrayList<>();
        setContentView(R.layout.invoice);

        order_total_view = findViewById(R.id.order_total);
        toolbar_title_view = findViewById(R.id.toolbar_title);
        token_no_view = findViewById(R.id.token_no);

        sizeSlider();
        createQuote();
    }

    private void sizeSlider() {
        invoice_slider_view = findViewById(R.id.invoice_slider);
        invoice_slider_view.setHasFixedSize(false);
        invoice_slider_view.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,
                false));
        invoiceAdapter = new InvoiceAdapter(this, invoice_slider_data);
        invoice_slider_view.setItemAnimator(new DefaultItemAnimator());
        invoice_slider_view.setAdapter(invoiceAdapter);
        invoiceAdapter.notifyDataSetChanged();
    }

    @Override
    public void onBackPressed() {
        DatabaseHelper db = new DatabaseHelper(this);
        db.clearCart();

        Intent intent = new Intent(this, MainActivity.class);
         intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    void createQuote() {
        final SessionManager session = new SessionManager(this);

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

                            InvoiceModel model = gson.fromJson(response.toString(), InvoiceModel.class);
                            invoice_slider_data.addAll(model.getOrderDetail());
                            order_total_view.setText("ORDER TOTAL: "+model.getInvoiceDetail().getOrderTotal());
                            token_no_view.setText("Token No: "+model.getOrderDetail().get(0).getTokenNo());
                            toolbar_title_view.setText("Order Invoice: #"+model.getInvoiceDetail().getOrderId());
                            invoiceAdapter.notifyDataSetChanged();
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
}
