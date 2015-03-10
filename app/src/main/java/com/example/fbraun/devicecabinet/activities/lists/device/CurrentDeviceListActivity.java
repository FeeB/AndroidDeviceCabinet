package com.example.fbraun.devicecabinet.activities.lists.device;

import com.example.fbraun.devicecabinet.model.Device;

import java.util.ArrayList;

/**
 * Created by fbraun on 23.02.15.
 */
public class CurrentDeviceListActivity extends AbstractDeviceListActivity {

    public ArrayList<Device> fetchDevices() {

        //toDo real current Device

        Device iPhone = new Device();
        iPhone.deviceName = "B4F-005";
        iPhone.deviceModel = "iPhone 6";
        iPhone.systemVersion = "8.1";
        iPhone.type = "iPhone";
        iPhone.bookedByPerson = false;

        ArrayList<Device> devicesList = new ArrayList<Device>();

        devicesList.add(iPhone);
        DeviceListAdapter deviceListAdapter = new DeviceListAdapter(devicesList, this);
        setListAdapter(deviceListAdapter);

        return devicesList;
    }
}
