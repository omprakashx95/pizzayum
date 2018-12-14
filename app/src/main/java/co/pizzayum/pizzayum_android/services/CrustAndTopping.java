package co.pizzayum.pizzayum_android.services;

import android.app.Activity;
import android.app.ProgressDialog;
import android.util.Log;

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
import co.pizzayum.pizzayum_android.models.PizzaDetailsModel;
import co.pizzayum.pizzayum_android.models.SelectedCatResponse;
import co.pizzayum.pizzayum_android.utility.AppController;
import co.pizzayum.pizzayum_android.utility.PizzaConstants;

import static com.android.volley.VolleyLog.TAG;

public class CrustAndTopping {

    ProgressDialog loading = null;
    private Activity activity;

    /**
     * This is class constructor where we are initialising some instance variable
     *
     * @param activity Activity - class instance
     */
    public CrustAndTopping(Activity activity) {
        this.activity = activity;
    }

    /**
     * This method is used here to make a http request to fetch the pizza list from the server
     */
    public void fetchingSelectedCat(final PizzaListAdapter adapter, final List<PizzaDetailsModel> pizzaDetailsModelList) {

        final String authorization_value = "Bearer  eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiIsImp0aSI6ImI0MjNlNDg2ZTBkM2ZhNzNmNzJlNzc1NTk5YjdmZGJlZGQ3NmFkNWRhYjBiYmJiZWI1MjZmNjI0MjcwNDE3ZmQyMDliNjA1Yzc3ZDc2Y2E1In0.eyJhdWQiOiIyIiwianRpIjoiYjQyM2U0ODZlMGQzZmE3M2Y3MmU3NzU1OTliN2ZkYmVkZDc2YWQ1ZGFiMGJiYmJlYjUyNmY2MjQyNzA0MTdmZDIwOWI2MDVjNzdkNzZjYTUiLCJpYXQiOjE1NDI4NTQyMjQsIm5iZiI6MTU0Mjg1NDIyNCwiZXhwIjoxNTc0MzkwMjIzLCJzdWIiOiI0Iiwic2NvcGVzIjpbXX0.Q4zdNWIhiF-wpf_HmNjw01pho4QyldsQsDsb0GYIWjaxekpFqJ5s2Bb3cRPtbviIQIVTl_2vAjdNt3Dy-qVAgokY-AJXuRJlu3q_iugwUXu6VsRaYwT3-Q3zz4GWPjbzskvL_dGHE7zj3_W-wFmR-RHwI1rMtg5TK2WbP5j_dupwGBIBvl9eouVjiUxSj4LuAT1UjW7UP_dnuomiv-jPkAfGvAPPp4HSoyraOEyT7BbqIKS_BE2bKvyUBjg61UA7km-sXEgXyR6WJcYy5txnrn3T52KffGh9EvFbV_u9nnMq1tT_inAA-KkDcvVTCKFNTI7VxD_8aFAAc0SkIAdQQ-O6YLGYsVglGWKXsL_X9GkDadz5k9ZJNW-TVizy1Fb37HZgOKvx6ILN51RD2AmWr5q7VLPcFM3-W8c5Xoox2WWEbIbJIJmT8ReT4B0jy63lJCXaTNopCVZetmTv63MGUQpPKilTEOCE9dxv3lNp_qMH9Ny_1XR8eUzotnz5Athg-DMNm2a1peCCqxa8hZAD-pCiBd4lnH2U3CzOimXKiTDOAlZcUuzjpYtSAdGCtpb4PxOeFZGXzW4-USDBGLnI0mrMRwJXquqGyIeAMEtdXvHsoNguT-9r86CKPeHAfje-2_VrmE9E_wWY6KxOF6-x9UrY8NtzWNRDkWAxfT_TGas";

        // loader
        loading = new ProgressDialog(activity);
        loading.setCancelable(false);
        loading.setMessage("Processing");
        loading.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        loading.show();

        String tag_string_req = "string_req";
        String url = PizzaConstants.BASE_URL + "/"+PizzaConstants.CATEGORY+"/"+PizzaConstants.CATEGORY_NAME;
        String tag_json_arry = "JSON Array Request";
        // jason object request
        JsonArrayRequest jsonObjReq = new JsonArrayRequest(Request.Method.GET,
                url, null,
                new Response.Listener<JSONArray>() {

                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d("Reponse", response.toString());

                        //Initialize gson obj to process jason response
                        GsonBuilder gsonBuilder = new GsonBuilder();
                        Gson gson = gsonBuilder.create();

                        // assigning data in model class, we initializing this class as a array type
                        // because the response is in array format
                        SelectedCatResponse[] getResult = gson.fromJson(response.toString(), SelectedCatResponse[].class);

                        for(int i = 0 ; i<getResult.length; i++){
                            PizzaDetailsModel a = new PizzaDetailsModel(getResult[i].getId(), getResult[i].getName(),
                                    getResult[i].getUrl(), getResult[i].getRegular(),
                                    getResult[i].getMedium(), getResult[i].getLarge(),
                                    getResult[i].getContent(), getResult[i].getCategory(),
                                    getResult[i].getVeg(),
                                    "",
                                    "");

                            pizzaDetailsModelList.add(a);
                        }

                        adapter.notifyDataSetChanged();

                        // a = new PizzaDetailsModel("Fresh Veg", "Oniun Crisp Capsicum", covers[1]);
                        Log.e("Pizza Cat Response:", "Result Length" + getResult.length
                        + " demo array size: " +  pizzaDetailsModelList.size());

                        loading.dismiss();
                    }

                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                Log.e("Custom", "Request Error" + error);
                loading.dismiss();
            }
        }) {

            // Passing values
            @Override
            public Map<String, String> getHeaders() {
                HashMap<String, String> headers = new HashMap<>();
                headers.put("Accept", "application/json");
                headers.put("Authorization", authorization_value);
                return headers;
            }
        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(jsonObjReq, tag_json_arry);
    }
}
