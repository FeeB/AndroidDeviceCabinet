package com.example.fbraun.devicecabinet.activities.lists.device;

import com.example.fbraun.devicecabinet.RESTApiClient;
import com.example.fbraun.devicecabinet.model.Device;

import java.util.ArrayList;

public class AvailableListActivity extends AbstractDeviceListActivity {

    @Override
    public void fetchDevices() {
        RESTApiClient client = new RESTApiClient();
        client.fetchAvailableDevices(new RESTApiClient.VolleyCallbackDeviceList() {
            @Override
            public void onSuccessListViews(ArrayList<Device> result) {
                dataList = result;


                DeviceListAdapter deviceListAdapter = new DeviceListAdapter(dataList, AvailableListActivity.this);
                setListAdapter(deviceListAdapter);
            }
        });
    }

}
