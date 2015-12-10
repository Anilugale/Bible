package com.itstest.textselection.util;

import android.content.Context;
import android.nfc.Tag;
import android.util.Log;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.itstest.textselection.AppController;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
   Created by anil on 26/08/2015.
 */
public class NetworkRequest {


    public static String TAG="NW";
    public static String RESULT="result";

    public static String BaseUrl="http://www.getwebcare.in/bible_app/music/";
   public static String SongSrc="http://www.getwebcare.in/bible_app/new_podcast.php";
   public static String PREST="http://www.getwebcare.in/bible_app/bible_priest.php";


    public static Gson gson=new Gson();



    public static void  SimpleJsonRequest(final Context context,
                                          JSONObject jsonRequest,
                                          String url,
                                          final JsonCallBack responce,
                                          final int responseCode,
                                          int method
    )
    {
        Log.e(TAG,url);
        String tag_json_obj = "json_obj_req";
         JsonArrayRequest jsonObjReq = new JsonArrayRequest(method,
                url ,
                jsonRequest.toString(),
                new Response.Listener<JSONArray>() {

                    @Override
                    public void onResponse(JSONArray response) {
                        VolleyLog.e(TAG, response.toString());



                                responce.success(response,responseCode);


                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.e(TAG, "Error: " + error.getMessage());
                responce.failer(error, responseCode);

            }
        }) ;
        jsonObjReq.setRetryPolicy(new DefaultRetryPolicy(
                20000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(jsonObjReq, tag_json_obj);
    }
}
