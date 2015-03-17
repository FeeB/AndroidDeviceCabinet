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

    private String deviceName;
    private String type;
    private boolean bookedByPerson;
    private String bookedByPersonId;
    private String bookedByPersonFullName;
    private String deviceUdId;
    private String systemVersion;
    private String deviceId;
    private String deviceModel;
    private String imageUrl;

    public Device (JSONObject object) {
        try {
            this.imageUrl = object.isNull("image_url") ? null : object.getString("image_url");
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

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean isBookedByPerson() {
        return bookedByPerson;
    }

    public void setBookedByPerson(boolean bookedByPerson) {
        this.bookedByPerson = bookedByPerson;
    }

    public String getBookedByPersonId() {
        return bookedByPersonId;
    }

    public void setBookedByPersonId(String bookedByPersonId) {
        this.bookedByPersonId = bookedByPersonId;
    }

    public String getBookedByPersonFullName() {
        return bookedByPersonFullName;
    }

    public void setBookedByPersonFullName(String bookedByPersonFullName) {
        this.bookedByPersonFullName = bookedByPersonFullName;
    }

    public String getDeviceUdId() {
        return deviceUdId;
    }

    public void setDeviceUdId(String deviceUdId) {
        this.deviceUdId = deviceUdId;
    }

    public String getSystemVersion() {
        return systemVersion;
    }

    public void setSystemVersion(String systemVersion) {
        this.systemVersion = systemVersion;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getDeviceModel() {
        return deviceModel;
    }

    public void setDeviceModel(String deviceModel) {
        this.deviceModel = deviceModel;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
