package com.example.fbraun.devicecabinet.activities.lists.device;

import com.example.fbraun.devicecabinet.RESTApiClient;
import com.example.fbraun.devicecabinet.model.Device;

import java.util.ArrayList;

/**
 * Created by fbraun on 23.02.15.
 */
public class BookedListActivity extends AbstractDeviceListActivity {

    public void fetchDevices() {
        RESTApiClient client = new RESTApiClient();
        client.fetchBookedDevices(new RESTApiClient.VolleyCallbackDeviceList() {
            @Override
            public void onSuccessListViews(ArrayList<Device> result) {
                dataList = result;
                DeviceListAdapter deviceListAdapter = new DeviceListAdapter(dataList, BookedListActivity.this);
                setListAdapter(deviceListAdapter);
            }
        });
    }

}
