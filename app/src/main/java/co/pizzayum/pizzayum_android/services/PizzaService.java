package co.pizzayum.pizzayum_android.services;

import android.app.Activity;
import android.app.ProgressDialog;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import co.pizzayum.pizzayum_android.adapters.PizzaListAdapter;
import co.pizzayum.pizzayum_android.adapters.TabListAdapter;
import co.pizzayum.pizzayum_android.models.PizzaDetailsModel;
import co.pizzayum.pizzayum_android.models.SelectedCatResponse;
import co.pizzayum.pizzayum_android.utility.AppController;
import co.pizzayum.pizzayum_android.utility.PizzaConstants;
import co.pizzayum.pizzayum_android.utility.SessionManager;

import static com.android.volley.VolleyLog.TAG;

public class PizzaService {

    private ProgressDialog loading = null;
    private Activity activity;
    private SessionManager session;

    /**
     * This is class constructor where we are initialising some instance variable
     *
     * @param activity Activity - class instance
     */
    public PizzaService(Activity activity) {
        Log.e("Reponse", "Service Called");
        this.activity = activity;
        session = new SessionManager(activity);
    }

    /**
     * This method is used here to make a http request to fetch the pizza list from the server
     */
    public void fetchingSelectedCat(final TabListAdapter adapter, final List<String> list_data,
                                    final PizzaListAdapter list_adapter, final List<PizzaDetailsModel> data,
                                    final RelativeLayout loader_container) {

        // loader
//        loading = new ProgressDialog(activity);
//        loading.setCancelable(false);
//        loading.setMessage("Processing");
//        loading.setProgressStyle(ProgressDialog.STYLE_SPINNER);
//        loading.show();
        loader_container.setVisibility(View.VISIBLE);
        String tag_json_arry = "JSON Array Request";
        String url = "http://www.pizzayum.co/api/pizza";
        // jason object request
        JsonArrayRequest jsonObjReq = new JsonArrayRequest(Request.Method.GET,
                url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d("Reponse", response.toString());
                        loader_container.setVisibility(View.INVISIBLE);
                        //Initialize gson obj to process jason response
                        GsonBuilder gsonBuilder = new GsonBuilder();
                        Gson gson = gsonBuilder.create();

                        // assigning data in model class, we initializing this class as a array type
                        // because the response is in array format
                        PizzaConstants.getResult = gson.fromJson(response.toString(), SelectedCatResponse[].class);

                        for (SelectedCatResponse a : PizzaConstants.getResult) {
                            if (!list_data.contains(a.getCategory())) {
                                list_data.add(a.getCategory());
                            }

                            list_data.remove("Crust");
                            list_data.remove("Extra Topping");
                        }
                        adapter.notifyDataSetChanged();

                        if (PizzaConstants.getResult.length > 1) {
                            for (SelectedCatResponse a : PizzaConstants.getResult) {
                                if (a.getCategory().equals(list_data.get(0))) {
                                    PizzaDetailsModel model = new PizzaDetailsModel(
                                            a.getId(), a.getName(), a.getUrl(), a.getRegular(),
                                            a.getMedium(), a.getLarge(), a.getContent(), a.getCategory(),
                                            a.getVeg(), " ", "");
                                    data.add(model);
                                }
                            }
                            list_adapter.notifyDataSetChanged();
                        }

                        list_adapter.notifyDataSetChanged();

                        Log.e("Pizza Cat Response:", "Result Length" + PizzaConstants.getResult.length
                                + " demo array size: " + data.size());

                        loader_container.setVisibility(View.INVISIBLE);
                        //loading.dismiss();
                    }

                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                Log.e("Custom", "Request Error" + error);
                loader_container.setVisibility(View.INVISIBLE);
                //loading.dismiss();
            }
        }) {

            // Passing values
            @Override
            public Map<String, String> getHeaders() {
                HashMap<String, String> headers = new HashMap<>();
                headers.put("Accept", "application/json");
                headers.put("Authorization", session.returnToken());
                return headers;
            }
        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(jsonObjReq, tag_json_arry);
    }
}
