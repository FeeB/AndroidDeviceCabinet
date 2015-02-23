package com.example.fbraun.devicecabinet;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.fbraun.devicecabinet.com.example.fbraun.devicecabinet.model.Device;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.List;

/**
 * Created by fbraun on 23.02.15.
 */
public class RESTApiClient {

    // Instantiate the RequestQueue.
    final String url ="http://cryptic-journey-8537.herokuapp.com/devices";

    public interface VolleyCallback{
        void onSuccess(ArrayList<Device> result);
    }



    public void fetchAllDevices(final VolleyCallback callback) {
        JsonArrayRequest request = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {

            @Override
            public void onResponse(JSONArray response) {
                ArrayList<Device> devices = new ArrayList<Device>();
                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject object = response.getJSONObject(i);
                        Device device = new Device(object);
                        devices.add(device);
                    }
                    catch (JSONException e) {
                        //do something
                    }
                }
                callback.onSuccess(devices);
            }

        }, new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error) {
                //do something
            }
        });

        VolleyApplication.getInstance().getRequestQueue().add(request);
    }
}
