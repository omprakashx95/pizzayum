package co.pizzayum.pizzayum_android.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import co.pizzayum.pizzayum_android.R;
import co.pizzayum.pizzayum_android.adapters.InvoiceAdapter;
import co.pizzayum.pizzayum_android.models.OrderDetailItem;
import co.pizzayum.pizzayum_android.utility.DatabaseHelper;
import co.pizzayum.pizzayum_android.utility.PizzaConstants;

public class Invoice extends AppCompatActivity implements View.OnClickListener {
    DatabaseHelper db;
    TextView order_total_view;
    RecyclerView invoice_slider_view;
    InvoiceAdapter invoiceAdapter;
    List<OrderDetailItem> invoice_slider_data;
    TextView toolbar_title_view;
    TextView token_no_view, d_loc, order_placed_time;
    ImageView cross_button;

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
        order_placed_time = findViewById(R.id.order_placed_time);

        cross_button.setOnClickListener(this);
        itemsSlider();

        orderTime();
    }

    void orderTime() {
        String dummy_date = "";
        try {
            SimpleDateFormat ft = new SimpleDateFormat("yyyy-M-dd hh:mm:ss", Locale.getDefault());
            java.util.Date t = ft.parse(PizzaConstants.INVOICE_MODEL.getInvoiceDetail().getCreatedAt());
            ft.applyPattern("MMMM dd, yyyy");
            dummy_date += ft.format(t);

            ft.applyPattern("hh:mm");
            dummy_date += " at " + ft.format(t);

            ft.applyPattern("HH");
            dummy_date += " " + getMeridian((ft.format(t)));
        } catch (
                Exception e) {
            System.out.println("Excep" + e);
        }

        String order_place_time_lbl = "Order placed on " + dummy_date;
        order_placed_time.setText(order_place_time_lbl);
    }

    private String getMeridian(String a) {
        if ((Integer.parseInt(a) > 12))
            return "PM";
        else
            return "AM";
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
        order_total_view.setText("ORDER TOTAL: " + PizzaConstants.INVOICE_MODEL.getInvoiceDetail().getOrderTotal());
        token_no_view.setText("Token No: " + PizzaConstants.INVOICE_MODEL.getOrderDetail().get(0).getTokenNo());
        toolbar_title_view.setText("Order Invoice: #" + PizzaConstants.INVOICE_MODEL.getInvoiceDetail().getOrderId());
        d_loc.setText(PizzaConstants.INVOICE_MODEL.getUser().getAddress());
        invoiceAdapter.notifyDataSetChanged();
    }

    @Override
    public void onBackPressed() {
        DatabaseHelper db = new DatabaseHelper(this);
        db.clearCart();

        startHomePage();
    }

    void startHomePage() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);

        db.clearCart();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.cross_button:
                startHomePage();
        }
    }
}
