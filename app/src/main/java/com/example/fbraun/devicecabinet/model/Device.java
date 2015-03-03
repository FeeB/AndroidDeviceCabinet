package com.example.fbraun.devicecabinet.model;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

/**
 * Created by fbraun on 03.02.15.
 */
public class Device implements Parcelable {

    // TODO: Members should never be public in java
    public String deviceName;
    public String type;
    public boolean bookedByPerson;
    public String bookedByPersonId;
    public String bookedByPersonFullName;
    public String deviceUdId;
    public String systemVersion;
    public String deviceId;
    public String deviceModel;
    public String imageUrl;

    public Device (JSONObject object) {
        try {

            this.imageUrl = object.getString("image_url");
            this.deviceName = object.getString("device_name");
            this.deviceUdId = object.getString("device_id");
            this.type = object.getString("category");
            this.deviceId = object.getString("id");
            this.systemVersion = object.getString("system_version");
            this.bookedByPerson = object.getString("is_booked").equals("YES") ? true : false;
            this.bookedByPersonId = object.getString("person_id");
            this.deviceModel = object.getString("device_type");
            this.bookedByPersonFullName = object.getJSONObject("person").getString("full_name");

        }
        catch (JSONException e) {

        }
    }

    public Device(Parcel source) {
        this.deviceName = source.readString();
        this.type = source.readString();
        this.deviceId = source.readString();
        this.systemVersion = source.readString();
        this.bookedByPerson = source.readInt() == 0 ? false : true;
        this.bookedByPersonId = source.readString();
        this.bookedByPersonFullName = source.readString();
        this.deviceModel = source.readString();
        this.imageUrl = source.readString();
    }

    public Device() {
        super();
    }

    public JSONObject toJson() {
        HashMap<String, String> jsonMap = new HashMap<>(6);
        jsonMap.put("device_name", this.deviceName);
        jsonMap.put("category", this.type);
        jsonMap.put("system_version", this.systemVersion);
        jsonMap.put("is_booked", this.bookedByPerson ? "YES" : "NO");
        jsonMap.put("device_type", this.deviceModel);
        if (this.deviceUdId != null) {
            jsonMap.put("device_id", this.deviceUdId);
        }

        return new JSONObject(jsonMap);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(deviceName);
        dest.writeString(type);
        dest.writeString(deviceId);
        dest.writeString(systemVersion);
        dest.writeInt(bookedByPerson ? 1 : 0);
        dest.writeString(bookedByPersonId);
        dest.writeString(bookedByPersonFullName);
        dest.writeString(deviceModel);
        dest.writeString(imageUrl);
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
