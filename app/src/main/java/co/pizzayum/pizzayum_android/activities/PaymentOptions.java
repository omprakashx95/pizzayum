package co.pizzayum.pizzayum_android.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;

import co.pizzayum.pizzayum_android.R;

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
}
