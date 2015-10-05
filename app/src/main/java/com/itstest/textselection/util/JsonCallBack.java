package  com.itstest.textselection.util;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;
import org.json.JSONObject;

/**
  Created by anil on 07/09/2015.
 */
public interface JsonCallBack {

    public void success(JSONArray response, int responseCode);
    public void failer(VolleyError response, int responseCode);

}
