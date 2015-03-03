package com.example.fbraun.devicecabinet;

import android.graphics.Bitmap;
import android.util.Base64;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.StringRequest;
import com.example.fbraun.devicecabinet.com.example.fbraun.devicecabinet.model.Device;
import com.example.fbraun.devicecabinet.com.example.fbraun.devicecabinet.model.Person;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by fbraun on 23.02.15.
 */
public class RESTApiClient {

    // Instantiate the RequestQueue.
//    final String deviceUrl ="http://localhost:3000/devices";
//    final String personUrl ="http://localhost:3000/persons";

    final String deviceUrl ="http://cryptic-journey-8537.herokuapp.com/devices";
    final String personUrl ="http://cryptic-journey-8537.herokuapp.com/persons";

    public interface VolleyCallbackDeviceList {
        void onSuccessListViews(ArrayList<Device> result);
    }

    public interface VolleyCallbackPersonList {
        void onSuccessListViews(ArrayList<Person> result);
    }

    public interface VolleyCallbackStore {
        void onStoreSuccess();
    }


    public void fetchAvailableDevices(final VolleyCallbackDeviceList callback) {
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

    public void fetchBookedDevices(final VolleyCallbackDeviceList callback) {
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

    public void fetchAllPersons(final VolleyCallbackPersonList callback) {;
        JsonArrayRequest request = new JsonArrayRequest(personUrl, new Response.Listener<JSONArray>() {

            @Override
            public void onResponse(JSONArray response) {
                ArrayList<Person> persons = new ArrayList<Person>();
                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject object = response.getJSONObject(i);
                        Person person = new Person(object);
                        persons.add(person);
                    }
                    catch (JSONException e) {
                        //do something
                    }
                }
                callback.onSuccessListViews(persons);
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

    public void deleteDevice(final Device device, final VolleyCallbackStore callback) {
        String newUrl = deviceUrl + "/" + device.deviceId;

        StringRequest request = new StringRequest(Request.Method.DELETE, newUrl, new Response.Listener<String>(){

            @Override
            public void onResponse(String response) {
                callback.onStoreSuccess();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //do something
            }
        });
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

    public void storePersonReferenceInDeviceObject(final Person person, final Device device, final VolleyCallbackStore callback) {

        String newUrl = deviceUrl + "/" + device.deviceId;

        StringRequest request = new StringRequest(Request.Method.PUT, newUrl, new Response.Listener<String>(){
            @Override
            public void onResponse(String response) {
                callback.onStoreSuccess();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //do something
            }
        }){
            @Override
            protected Map<String, String> getParams() {
                Map<String,String> params = new HashMap<String, String>();
                params.put("person_id", person.personId);
                params.put("is_booked", "YES");
                params.put("Content-Type","application/json");
                return params;
            }
        };
        VolleyApplication.getInstance().getRequestQueue().add(request);
    }

    public void deletePersonReferenceFromDevice(final Device device, final VolleyCallbackStore callback){

        String newUrl = deviceUrl + "/" + device.deviceId;

        StringRequest request = new StringRequest(Request.Method.PUT, newUrl, new Response.Listener<String>(){
            @Override
            public void onResponse(String response) {
                callback.onStoreSuccess();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //do something
            }
        }){
            @Override
            protected Map<String, String> getParams() {
                Map<String,String> params = new HashMap<String, String>();
                params.put("person_id", "");
                params.put("is_booked", "NO");
                params.put("Content-Type","application/json");
                return params;
            }
        };
        VolleyApplication.getInstance().getRequestQueue().add(request);

    }

    public void deletePerson(final Person person, final VolleyCallbackStore callback) {
        String newUrl = personUrl + "/" + person.personId;

        StringRequest request = new StringRequest(Request.Method.DELETE, newUrl, new Response.Listener<String>(){

            @Override
            public void onResponse(String response) {
                callback.onStoreSuccess();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //do something
            }
        });
        VolleyApplication.getInstance().getRequestQueue().add(request);
    }

    public void uploadImage(final Bitmap image, final Device device, final VolleyCallbackStore callback) {

        String newUrl = deviceUrl + "/" + device.deviceId;

        StringRequest request = new StringRequest(Request.Method.PUT, newUrl, new Response.Listener<String>(){
            @Override
            public void onResponse(String response) {
                callback.onStoreSuccess();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //do something
            }
        }){
            @Override
            protected Map<String, String> getParams() {
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                image.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream); //bm is the bitmap object
                byte[] byteArrayImage = byteArrayOutputStream.toByteArray();
                String encodedImage = Base64.encodeToString(byteArrayImage, Base64.DEFAULT);

                Map<String,String> params = new HashMap<String, String>();
                params.put("image_data_encoded", encodedImage);
                return params;
            }
        };
        VolleyApplication.getInstance().getRequestQueue().add(request);
    }
}
