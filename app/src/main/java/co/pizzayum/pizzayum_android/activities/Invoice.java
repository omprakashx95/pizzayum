package co.pizzayum.pizzayum_android.activities;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
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

public class Invoice extends AppCompatActivity implements View.OnClickListener{
    DatabaseHelper db;
    TextView order_total_view;
    RecyclerView invoice_slider_view;
    InvoiceAdapter invoiceAdapter;
    List<OrderDetailItem> invoice_slider_data ;
    TextView toolbar_title_view;
    TextView token_no_view, d_loc;
    ImageView cross_button ;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        db = new DatabaseHelper(this);
        invoice_slider_data = new ArrayList<>();
        setContentView(R.layout.invoice);

        order_total_view = findViewById(R.id.order_total);
        toolbar_title_view = findViewById(R.id.toolbar_title);
        token_no_view = findViewById(R.id.token_no);
        d_loc = findViewById(R.id.d_location);
        cross_button = findViewById(R.id.cross_button);
        cross_button.setOnClickListener(this);
        itemsSlider();
    }

    private void itemsSlider() {
        invoice_slider_view = findViewById(R.id.invoice_slider);
        invoice_slider_view.setHasFixedSize(false);
        invoice_slider_view.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,
                false));
        invoiceAdapter = new InvoiceAdapter(this, invoice_slider_data);
        invoice_slider_view.setItemAnimator(new DefaultItemAnimator());
        invoice_slider_view.setAdapter(invoiceAdapter);
        invoiceAdapter.notifyDataSetChanged();

        invoice_slider_data.addAll(PizzaConstants.INVOICE_MODEL.getOrderDetail());
        order_total_view.setText("ORDER TOTAL: "+PizzaConstants.INVOICE_MODEL.getInvoiceDetail().getOrderTotal());
        token_no_view.setText("Token No: "+PizzaConstants.INVOICE_MODEL.getOrderDetail().get(0).getTokenNo());
        toolbar_title_view.setText("Order Invoice: #"+PizzaConstants.INVOICE_MODEL.getInvoiceDetail().getOrderId());
        d_loc.setText(PizzaConstants.INVOICE_MODEL.getUser().getAddress());
        invoiceAdapter.notifyDataSetChanged();
    }

    @Override
    public void onBackPressed() {
        DatabaseHelper db = new DatabaseHelper(this);
        db.clearCart();

       startHomePage();
    }

    void startHomePage(){
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);

        db.clearCart();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.cross_button:
                startHomePage();
        }
    }
}
