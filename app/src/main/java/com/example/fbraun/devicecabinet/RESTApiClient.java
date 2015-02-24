package com.example.fbraun.devicecabinet;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.fbraun.devicecabinet.com.example.fbraun.devicecabinet.model.Device;
import com.example.fbraun.devicecabinet.com.example.fbraun.devicecabinet.model.Person;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by fbraun on 23.02.15.
 */
public class RESTApiClient {

    // Instantiate the RequestQueue.
    final String deviceUrl ="http://cryptic-journey-8537.herokuapp.com/devices";
    final String personUrl ="http://cryptic-journey-8537.herokuapp.com/persons";

    public interface VolleyCallbackLists {
        void onSuccessListViews(ArrayList<Device> result);
    }

    public interface VolleyCallbackStore {
        void onStoreSuccess();
    }


    public void fetchAvailableDevices(final VolleyCallbackLists callback) {
        String newUrl = deviceUrl + "?booked=NO";
        JsonArrayRequest request = new JsonArrayRequest(newUrl, new Response.Listener<JSONArray>() {

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
                callback.onSuccessListViews(devices);
            }

        }, new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error) {
                //do something
            }
        });

        VolleyApplication.getInstance().getRequestQueue().add(request);
    }

    public void fetchBookedDevices(final VolleyCallbackLists callback) {
        String newUrl = deviceUrl + "?booked=YES";
        JsonArrayRequest request = new JsonArrayRequest(newUrl, new Response.Listener<JSONArray>() {

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
                callback.onSuccessListViews(devices);
            }

        }, new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error) {
                //do something
            }
        });

        VolleyApplication.getInstance().getRequestQueue().add(request);
    }

    public void storeDevice(final Device device, final VolleyCallbackStore callback) {

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, deviceUrl, device.toJson(), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                callback.onStoreSuccess();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //do something
            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String,String> params = new HashMap<String, String>();
                params.put("Content-Type","application/json");
                return params;
            }
        };
        VolleyApplication.getInstance().getRequestQueue().add(request);
    }

    public void storePerson(final Person person, final VolleyCallbackStore callback) {

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, personUrl, person.toJson(), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                callback.onStoreSuccess();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //do something
            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String,String> params = new HashMap<String, String>();
                params.put("Content-Type","application/json");
                return params;
            }
        };
        VolleyApplication.getInstance().getRequestQueue().add(request);
    }
}
