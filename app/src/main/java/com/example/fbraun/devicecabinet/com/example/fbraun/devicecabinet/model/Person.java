package com.example.fbraun.devicecabinet.com.example.fbraun.devicecabinet.model;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;

/**
 * Created by fbraun on 03.02.15.
 */
public class Person {
    public String firstName;
    public String lastName;
    private String fullName;
    public String personId;

    public Person (JSONObject object) {
        try {
            this.firstName = object.getString("first_name");
            this.lastName = object.getString("last_name");
            this.fullName = object.getString("full_name");
            this.personId = object.getString("id");
        }
        catch (JSONException e) {

        }
    }

    public Person () {
        super();
    }

    public String getFullName () {
        this.fullName = firstName + " " + lastName;
        return this.fullName;
    }

    public JSONObject toJson() {
        HashMap<String, String> jsonMap = new HashMap<>(3);
        jsonMap.put("first_name", this.firstName);
        jsonMap.put("last_name", this.lastName);
        jsonMap.put("full_name", this.fullName);

        return new JSONObject(jsonMap);
    }
}
