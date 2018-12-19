package co.pizzayum.pizzayum_android.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
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
import co.pizzayum.pizzayum_android.activities.PaymentOptions;
import co.pizzayum.pizzayum_android.adapters.CartAdapter;
import co.pizzayum.pizzayum_android.models.OrderResponse;
import co.pizzayum.pizzayum_android.models.PizzaOrderTableModel;
import co.pizzayum.pizzayum_android.utility.DatabaseHelper;
import co.pizzayum.pizzayum_android.utility.PizzaConstants;

//Our class extending fragment
public class CartFragment extends Fragment {

    List<PizzaOrderTableModel> cart_model_list;
    RecyclerView cart_slider_view;
    CartAdapter cart_adapter;
    DatabaseHelper db;
    TextView total_bill;
    RelativeLayout proceed_payment_view;
    ProgressBar progress_bar_view ;
    public static CartFragment newInstance() {
        return new CartFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.cart_frag, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        db = new DatabaseHelper(getActivity());
        total_bill = view.findViewById(R.id.total_bill);
        cart_model_list = new ArrayList<>();
        cart_slider_view = view.findViewById(R.id.recycler_view);
        progress_bar_view = view.findViewById(R.id.progress_bar);
        progress_bar_view.setVisibility(View.INVISIBLE);
        sizeSlider();

        proceed_payment_view = view.findViewById(R.id.proceed_payment);
        proceed_payment_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createQuote();
            }
        });
    }

    private void sizeSlider() {
        cart_model_list.clear();
        cart_slider_view.setHasFixedSize(false);
        cart_slider_view.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL,
                false));
        cart_adapter = new CartAdapter(getActivity(), cart_model_list);
        cart_slider_view.setItemAnimator(new DefaultItemAnimator());
        cart_slider_view.setAdapter(cart_adapter);
        cart_adapter.notifyDataSetChanged();

        cart_adapter.setOnDataChangeListener(new CartAdapter.OnDataChangeListener() {
            public void onDataChanged(int size) {
                setTotalBill();
            }
        });

        filldata();
    }

    void filldata() {
        cart_model_list.clear();
        cart_model_list.addAll(db.getAllOrders());
        cart_adapter.notifyDataSetChanged();
        Log.e("Size", "Lst Size" + cart_model_list.size());
        setTotalBill();
    }

    void setTotalBill() {
        int bill = 0;
        for (PizzaOrderTableModel m : cart_model_list) {
            bill += Integer.parseInt(m.getBill());
        }
        total_bill.setText(getActivity().getString(R.string.Rs) + bill);
        Log.e("Cart Fragment", "");
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

    void createQuote() {
        final String authorization_value = "Bearer  eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiIsImp0aSI6ImI0MjNlNDg2ZTBkM2ZhNzNmNzJlNzc1NTk5YjdmZGJlZGQ3NmFkNWRhYjBiYmJiZWI1MjZmNjI0MjcwNDE3ZmQyMDliNjA1Yzc3ZDc2Y2E1In0.eyJhdWQiOiIyIiwianRpIjoiYjQyM2U0ODZlMGQzZmE3M2Y3MmU3NzU1OTliN2ZkYmVkZDc2YWQ1ZGFiMGJiYmJlYjUyNmY2MjQyNzA0MTdmZDIwOWI2MDVjNzdkNzZjYTUiLCJpYXQiOjE1NDI4NTQyMjQsIm5iZiI6MTU0Mjg1NDIyNCwiZXhwIjoxNTc0MzkwMjIzLCJzdWIiOiI0Iiwic2NvcGVzIjpbXX0.Q4zdNWIhiF-wpf_HmNjw01pho4QyldsQsDsb0GYIWjaxekpFqJ5s2Bb3cRPtbviIQIVTl_2vAjdNt3Dy-qVAgokY-AJXuRJlu3q_iugwUXu6VsRaYwT3-Q3zz4GWPjbzskvL_dGHE7zj3_W-wFmR-RHwI1rMtg5TK2WbP5j_dupwGBIBvl9eouVjiUxSj4LuAT1UjW7UP_dnuomiv-jPkAfGvAPPp4HSoyraOEyT7BbqIKS_BE2bKvyUBjg61UA7km-sXEgXyR6WJcYy5txnrn3T52KffGh9EvFbV_u9nnMq1tT_inAA-KkDcvVTCKFNTI7VxD_8aFAAc0SkIAdQQ-O6YLGYsVglGWKXsL_X9GkDadz5k9ZJNW-TVizy1Fb37HZgOKvx6ILN51RD2AmWr5q7VLPcFM3-W8c5Xoox2WWEbIbJIJmT8ReT4B0jy63lJCXaTNopCVZetmTv63MGUQpPKilTEOCE9dxv3lNp_qMH9Ny_1XR8eUzotnz5Athg-DMNm2a1peCCqxa8hZAD-pCiBd4lnH2U3CzOimXKiTDOAlZcUuzjpYtSAdGCtpb4PxOeFZGXzW4-USDBGLnI0mrMRwJXquqGyIeAMEtdXvHsoNguT-9r86CKPeHAfje-2_VrmE9E_wWY6KxOF6-x9UrY8NtzWNRDkWAxfT_TGas";
        String url = "http://www.pizzayum.co/api/order";

        try {
            JSONObject jsonBody = new JSONObject();
            jsonBody.put("data", requestJsonGenerator());
            jsonBody.put("email", "rishabh19910623@gmail.com");
            final String requestBody = jsonBody.toString();
            Log.e("Log", "auth: " + requestBody);
            progress_bar_view.setVisibility(View.VISIBLE);
            JsonObjectRequest request = new JsonObjectRequest(
                    Request.Method.POST,        // Post method
                    url,
                    null, // Parameters
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            progress_bar_view.setVisibility(View.INVISIBLE);
                            GsonBuilder gsonBuilder = new GsonBuilder();
                            Gson gson = gsonBuilder.create();

                            // assigning data in model class, we initializing this class as a array type
                            // because the response is in array format
                            OrderResponse model = gson.fromJson(response.toString(), OrderResponse.class);
                            PizzaConstants.ORDER_ID = model.getOrderId();
                            Log.e("ORDERID","ORDERID:" + model.getOrderId());
                            getActivity().startActivity(new Intent(getActivity(), PaymentOptions.class));
                            Log.e("Response", "Response: " + response.toString());
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            progress_bar_view.setVisibility(View.INVISIBLE);
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
            RequestQueue queue = Volley.newRequestQueue(getActivity());
            queue.add(request);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
