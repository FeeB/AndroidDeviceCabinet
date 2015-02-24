package com.example.fbraun.devicecabinet.OverViewLists;

import com.example.fbraun.devicecabinet.RESTApiClient;
import com.example.fbraun.devicecabinet.com.example.fbraun.devicecabinet.model.Device;

import java.util.ArrayList;


public class AvailableList extends superList {

    @Override
    public void fetchDevices() {
        RESTApiClient client = new RESTApiClient();
        client.fetchAvailableDevices(new RESTApiClient.VolleyCallbackDeviceList() {
            @Override
            public void onSuccessListViews(ArrayList<Device> result) {
                dataList = result;
                ListOverviewAdapter listOverviewAdapter = new ListOverviewAdapter(dataList, AvailableList.this);
                setListAdapter(listOverviewAdapter);
            }
        });
    }

}
