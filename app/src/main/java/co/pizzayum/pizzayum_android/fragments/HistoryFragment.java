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
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
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
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import co.pizzayum.pizzayum_android.R;
import co.pizzayum.pizzayum_android.activities.OrderDetails;
import co.pizzayum.pizzayum_android.adapters.HistoryAdapter;
import co.pizzayum.pizzayum_android.models.HistoryResponse;
import co.pizzayum.pizzayum_android.models.HistorySortedModel;
import co.pizzayum.pizzayum_android.models.OrderDetailItem;
import co.pizzayum.pizzayum_android.utility.CustomItemClickListener;
import co.pizzayum.pizzayum_android.utility.PizzaConstants;
import co.pizzayum.pizzayum_android.utility.RecyclerTouchListener;
import co.pizzayum.pizzayum_android.utility.SessionManager;

//Our class extending fragment
public class HistoryFragment extends Fragment {

    RecyclerView history_slider_view;
    HistoryAdapter historyAdapter;
    List<OrderDetailItem> history_slider_data, history_slider_data1;
    ;
    RelativeLayout custom_loader_container;
    ImageView loader_img_view;
    String selected_id;

    public static HistoryFragment newInstance() {
        return new HistoryFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.history_frag, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        custom_loader_container = view.findViewById(R.id.custom_loader);
        loader_img_view = view.findViewById(R.id.loader_view);
        pizzaCustomLoader();
        custom_loader_container.setVisibility(View.INVISIBLE);

        historySlider(view);
        createQuote();

        history_slider_view.addOnItemTouchListener(new RecyclerTouchListener(getContext(),
                history_slider_view, new CustomItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                PizzaConstants.HISTORY_ROW_POSITION = position;
                startActivity(new Intent(getActivity(), OrderDetails.class));
            }
        }));
    }

    void pizzaCustomLoader() {
        Animation rotation = AnimationUtils.loadAnimation(getActivity(), R.anim.rotation_animation);
        loader_img_view.startAnimation(rotation);
    }

    void historySlider(View v) {
        history_slider_data = new ArrayList<>();
        history_slider_data1 = new ArrayList<>();
        PizzaConstants.history_slider_data_sorted = new ArrayList<>();
        history_slider_view = v.findViewById(R.id.history_slider);
        history_slider_view.setHasFixedSize(false);
        history_slider_view.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL,
                false));
        historyAdapter = new HistoryAdapter(getActivity(), PizzaConstants.history_slider_data_sorted);
        history_slider_view.setItemAnimator(new DefaultItemAnimator());
        history_slider_view.setAdapter(historyAdapter);
        historyAdapter.notifyDataSetChanged();
    }


    void createQuote() {
        final SessionManager session = new SessionManager(getActivity());
        custom_loader_container.setVisibility(View.VISIBLE);
        String url = "http://www.pizzayum.co/api/order_history";

        try {
            JSONObject jsonBody = new JSONObject();
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
                            custom_loader_container.setVisibility(View.INVISIBLE);
                            Log.e("Response", "Response: " + response.toString());
                            //Initialize gson obj to process jason response
                            GsonBuilder gsonBuilder = new GsonBuilder();
                            Gson gson = gsonBuilder.create();
                            HistoryResponse model = gson.fromJson(response.toString(), HistoryResponse.class);
                            List<OrderDetailItem> data = new ArrayList<>();

                            data.addAll(model.getOrderDetail());
                            selected_id = "0";
                            for (int i = 0; i < data.size(); i++) {
                                if (!(selected_id.equals(data.get(i).getOrderId()))) {
                                    selected_id = data.get(i).getOrderId();
                                    if (history_slider_data.size() > 0) {
                                        HistorySortedModel m = new HistorySortedModel(history_slider_data);
                                        PizzaConstants.history_slider_data_sorted.add(m);
                                        history_slider_data = new ArrayList<>();
                                    }
                                }
                                history_slider_data.add(data.get(i));
                                if (i == data.size()-1) {
                                    HistorySortedModel m = new HistorySortedModel(history_slider_data);
                                    PizzaConstants.history_slider_data_sorted.add(m);
                                }
                            }

                            Collections.reverse(PizzaConstants.history_slider_data_sorted);
                            historyAdapter.notifyDataSetChanged();
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            custom_loader_container.setVisibility(View.INVISIBLE);
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
