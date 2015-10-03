package  com.itstest.textselection.util;

import com.android.volley.VolleyError;

import org.json.JSONObject;

/**
  Created by anil on 07/09/2015.
 */
public interface JsonCallBack {

    public void success(JSONObject response, int responseCode);
    public void failer(VolleyError response, int responseCode);

}
