package com.itstest.textselection.util;

import android.content.Context;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;

import com.google.gson.Gson;
import com.itstest.textselection.AppController;

import org.json.JSONException;
import org.json.JSONObject;

/**
   Created by anil on 26/08/2015.
 */
public class NetworkRequest {


    public static String TAG="NW";
    public static String RESULT="result";

    public static String BaseUrl="http://www.getwebcare.in/bible_app/music/";
   public static String SongSrc="http://www.getwebcare.in/bible_app/podcast.php";
   // public static String SongSrc="https://api.myjson.com/bins/2bx1m";
    public static  String data="[{\"name\":\"music\",\"url\":\"http://www.getwebcare.in/bible_app/music/first.mp3\",\"singer_name\":\"Artif Aslam\",\"singer_email\":\"\",\"singer_mobile_no\":\"9856985314\",\"singer_details\":\"\"},{\"name\":\"Test Music\",\"url\":\"http://www.getwebcare.in/bible_app/music/ARRehman-MaaTujheSalam.mp3\",\"singer_name\":\"Singer1\",\"singer_email\":\"singer@gmail.com\",\"singer_mobile_no\":\"9856239856\",\"singer_details\":\"Sad songs\"}]";


    public static Gson gson=new Gson();



    public static void  SimpleJsonRequest(final Context context,
                                          JSONObject jsonRequest,
                                          String url,
                                          final JsonCallBack responce,
                                          final int responseCode,
                                          int method
    )
    {
        String tag_json_obj = "json_obj_req";
         JsonObjectRequest jsonObjReq = new JsonObjectRequest(method,
                url ,
                jsonRequest.toString(),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        VolleyLog.e(TAG, response.toString());
                        try {
                            if(response.getInt(RESULT)==0)
                            {
                                responce.success(response,responseCode);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

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
