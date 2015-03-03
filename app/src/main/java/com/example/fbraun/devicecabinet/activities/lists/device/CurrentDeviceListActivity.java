package com.example.fbraun.devicecabinet.activities.lists.device;

import com.example.fbraun.devicecabinet.model.Device;

import java.util.ArrayList;

/**
 * Created by fbraun on 23.02.15.
 */
public class CurrentDeviceListActivity extends AbstractDeviceListActivity {

    public void fetchDevices() {

        //toDo real current Device

        Device iPhone = new Device();
        iPhone.deviceName = "B4F-005";
        iPhone.deviceModel = "iPhone 6";
        iPhone.systemVersion = "8.1";
        iPhone.type = "iPhone";

        ArrayList<Device> devicesList = new ArrayList<Device>();

        devicesList.add(iPhone);
        dataList = devicesList;
        DeviceListAdapter deviceListAdapter = new DeviceListAdapter(devicesList, this);
        setListAdapter(deviceListAdapter);
    }
}
