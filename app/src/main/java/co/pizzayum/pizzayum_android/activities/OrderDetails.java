package co.pizzayum.pizzayum_android.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

import co.pizzayum.pizzayum_android.R;
import co.pizzayum.pizzayum_android.adapters.HistoryBilling;
import co.pizzayum.pizzayum_android.utility.PizzaConstants;

public class OrderDetails extends AppCompatActivity {
    RecyclerView bill_slider_viw;
    HistoryBilling billing_adapter;

    TextView date_text_view, d_time, agent_name, d_loc, order_number;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.history_details);
        historySlider();

        date_text_view = findViewById(R.id.order_date);
        d_time = findViewById(R.id.d_time);
        agent_name = findViewById(R.id.agent_name);
        d_loc = findViewById(R.id.d_location);
        order_number = findViewById(R.id.order_number);

        if (PizzaConstants.history_slider_data_sorted.
                get(PizzaConstants.HISTORY_ROW_POSITION).getHistory_slider_data().size() > 0) {

            String time = PizzaConstants.history_slider_data_sorted.
                    get(PizzaConstants.HISTORY_ROW_POSITION).getHistory_slider_data().get(0).getCreatedAt();
            date_text_view.setText(dateFormatter(time));
            String t = "Order Delivered on "+ dateFormatter(time);
            d_time.setText(t);
            String d_boy = "by " + PizzaConstants.history_slider_data_sorted.
                    get(PizzaConstants.HISTORY_ROW_POSITION).getHistory_slider_data().get(0).getDeliveryAgent();
            agent_name.setText(d_boy);
            d_loc.setText(PizzaConstants.HISTORY_RESPONSE.getUser().getAddress());
            order_number.setText(PizzaConstants.history_slider_data_sorted.
                    get(PizzaConstants.HISTORY_ROW_POSITION).getHistory_slider_data().get(0).getOrderId());
        }
    }

    void historySlider() {
        bill_slider_viw = findViewById(R.id.billing_slider);
        bill_slider_viw.setHasFixedSize(false);
        bill_slider_viw.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,
                false));
        billing_adapter = new HistoryBilling(this, PizzaConstants.history_slider_data_sorted.
                get(PizzaConstants.HISTORY_ROW_POSITION).getHistory_slider_data());
        bill_slider_viw.setItemAnimator(new DefaultItemAnimator());
        bill_slider_viw.setAdapter(billing_adapter);
        billing_adapter.notifyDataSetChanged();
    }

    String dateFormatter(String time) {
        String dummy_date = "";
        try {
            SimpleDateFormat ft = new SimpleDateFormat("yyyy-M-dd hh:mm:ss", Locale.getDefault());
            java.util.Date t = ft.parse(time);
            ft.applyPattern("MMMM dd, yyyy");
            dummy_date += ft.format(t);

            ft.applyPattern("hh:mm");
            dummy_date += " at " + ft.format(t);

            ft.applyPattern("HH");
            dummy_date += " " + getMeridian((ft.format(t)));
            return dummy_date;

        } catch (
                Exception e) {
            System.out.println("Excep" + e);
        }
        return dummy_date;
    }

    private String getMeridian(String a) {
        if ((Integer.parseInt(a) > 12))
            return "PM";
        else
            return "AM";
    }
}
