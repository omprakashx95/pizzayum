package co.pizzayum.pizzayum_android.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

import co.pizzayum.pizzayum_android.R;
import co.pizzayum.pizzayum_android.adapters.HistoryBilling;
import co.pizzayum.pizzayum_android.utility.PizzaConstants;

public class CustomLoader extends AppCompatActivity {
    RecyclerView bill_slider_viw;
    HistoryBilling billing_adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.history_details);

        //PizzaConstants.history_slider_data_sorted = new ArrayList<>();
        historySlider();
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
}
