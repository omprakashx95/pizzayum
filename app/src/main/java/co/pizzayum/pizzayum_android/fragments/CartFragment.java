package co.pizzayum.pizzayum_android.fragments;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
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
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

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
import co.pizzayum.pizzayum_android.models.AddressResponse;
import co.pizzayum.pizzayum_android.models.OrderResponse;
import co.pizzayum.pizzayum_android.models.PizzaOrderTableModel;
import co.pizzayum.pizzayum_android.models.SavedAddressResponse;
import co.pizzayum.pizzayum_android.utility.DatabaseHelper;
import co.pizzayum.pizzayum_android.utility.PizzaConstants;
import co.pizzayum.pizzayum_android.utility.SessionManager;

//Our class extending fragment
public class CartFragment extends Fragment implements View.OnClickListener {

    List<PizzaOrderTableModel> cart_model_list;
    RecyclerView cart_slider_view;
    CartAdapter cart_adapter;
    DatabaseHelper db;
    TextView total_bill, address_edit_btn_view, delivery_address_view;
    RelativeLayout proceed_payment_view;
    ProgressBar progress_bar_view;
    Dialog myDialog;
    SessionManager session ;
    RelativeLayout custom_loader_container;
    ImageView loader_img_view;
    RelativeLayout empty_cart_container ;

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

        empty_cart_container = view.findViewById(R.id.empty_cart_container);
        session = new SessionManager(getActivity());
        db = new DatabaseHelper(getActivity());

        custom_loader_container = view.findViewById(R.id.custom_loader);
        loader_img_view = view.findViewById(R.id.loader_view);
        pizzaCustomLoader();
        custom_loader_container.setVisibility(View.INVISIBLE);

        total_bill = view.findViewById(R.id.total_bill);
        cart_model_list = new ArrayList<>();
        cart_slider_view = view.findViewById(R.id.recycler_view);
        address_edit_btn_view = view.findViewById(R.id.address_edit_btn);
        //progress_bar_view = view.findViewById(R.id.progress_bar);
        //progress_bar_view.setVisibility(View.INVISIBLE);
        delivery_address_view = view.findViewById(R.id.delivery_address);
        sizeSlider();

        proceed_payment_view = view.findViewById(R.id.proceed_payment);
        proceed_payment_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // createQuote();
                startActivity(new Intent(getActivity(), PaymentOptions.class));
            }
        });
        address_edit_btn_view.setOnClickListener(this);
        delivery_address_view.setText(PizzaConstants.DELIVERY_ADDRESS);
        myDialog = new Dialog(getActivity());

        userAddress();

        empty_cart_container.setVisibility(View.INVISIBLE);
        showEmptyCart();
    }

    void showEmptyCart(){
        if (! (db.getAllOrders().size()>0)){
            empty_cart_container.setVisibility(View.VISIBLE);
        }
    }

    void pizzaCustomLoader() {
        Animation rotation = AnimationUtils.loadAnimation(getActivity(), R.anim.rotation_animation);
        loader_img_view.startAnimation(rotation);
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
                showEmptyCart();
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
        PizzaConstants.TOTAL_BILL = getActivity().getString(R.string.Rs) + bill;
        Log.e("Cart Fragment", "");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.address_edit_btn:
                ShowPopup();
                break;
        }
    }

    public void ShowPopup() {
        final TextView locality, house, landmark;
        Button register_button;

        myDialog.setContentView(R.layout.address_manager);
        locality = myDialog.findViewById(R.id.locality);
        house = myDialog.findViewById(R.id.house_no);
        landmark = myDialog.findViewById(R.id.landmark);
        register_button = myDialog.findViewById(R.id.register_button);
        register_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!(locality.getText().toString().isEmpty() && locality.getText().toString().isEmpty()
                        || locality.getText().toString().isEmpty())) {
                    String s = locality.getText().toString() + " " + house.getText().toString() + " " +
                            landmark.getText().toString();
                    saveAddress(s);
                } else {
                    Toast.makeText(getActivity(), "All Fields are required", Toast.LENGTH_SHORT).show();
                }
            }
        });
        myDialog.setCancelable(false);
        myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        myDialog.show();
    }

    void saveAddress(String s) {
        String url = "http://www.pizzayum.co/api/address";
        custom_loader_container.setVisibility(View.VISIBLE);
        try {
            JSONObject jsonBody = new JSONObject();
            jsonBody.put("address", s);
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
                            AddressResponse address_data = gson.fromJson(response.toString(), AddressResponse.class);
                            delivery_address_view.setText(address_data.getAddress());

                            custom_loader_container.setVisibility(View.INVISIBLE);
                            myDialog.dismiss();
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
            RequestQueue queue = Volley.newRequestQueue(getActivity());
            queue.add(request);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void userAddress() {

        // loader
        custom_loader_container.setVisibility(View.VISIBLE);

        String url = "http://www.pizzayum.co/api/user";

        try {
            JSONObject jsonBody = new JSONObject();
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
                            custom_loader_container.setVisibility(View.INVISIBLE);
                            GsonBuilder gsonBuilder = new GsonBuilder();
                            Gson gson = gsonBuilder.create();
                            SavedAddressResponse address_data = gson.fromJson(response.toString(), SavedAddressResponse.class);
                            if (address_data.getAddress() == null) {
                                ShowPopup();
                            } else {
                                delivery_address_view.setText(address_data.getAddress().toString());
                            }
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
            RequestQueue queue = Volley.newRequestQueue(getActivity());
            queue.add(request);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
