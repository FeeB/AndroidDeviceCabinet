package com.example.fbraun.devicecabinet.com.example.fbraun.devicecabinet.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Constructor;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by fbraun on 03.02.15.
 */
public class Device implements Parcelable {

    public String deviceName;
    public String type;
    public Boolean bookedByPerson;
    public String bookedByPersonId;
    public String bookedByPersonFullName;
    public String deviceUdId;
    public String systemVersion;
    public String deviceId;
    public String deviceModel;
    public URL imageUrl;

    public Device (JSONObject object) {
        try {
            this.deviceName = object.getString("device_name");
            this.deviceUdId = object.getString("device_id");
            this.type = object.getString("category");
            this.deviceId = object.getString("id");
            this.systemVersion = object.getString("system_version");
            this.bookedByPerson = object.getString("is_booked").equals("YES") ? true : false;
            this.bookedByPersonId = object.getString("person_id");
            this.bookedByPersonFullName = object.getJSONObject("person").getString("full_name");
            this.deviceModel = object.getString("device_type");

            if (!object.getString("image_url").isEmpty()) {
                try {
                    this.imageUrl = new URL(object.getString("image_url"));
                }
                catch(MalformedURLException e) {

                }
            }
        }
        catch (JSONException e) {

        }
    }

    public Device(Parcel source) {
        this.deviceName = source.readString();
        this.type = source.readString();
        // TODO for all
    }

    public Device() {
        super();
    }

    public String toJson() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(deviceName);
        dest.writeString(type);
        // TODO: Do for all fields
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {

        @Override
        public Device createFromParcel(Parcel source) {
            return new Device(source);
        }

        @Override
        public Device[] newArray(int size) {
            return new Device[0];
        }
    };

}
