package com.example.fbraun.devicecabinet.OverViewLists;

import com.example.fbraun.devicecabinet.RESTApiClient;
import com.example.fbraun.devicecabinet.com.example.fbraun.devicecabinet.model.Device;

import java.util.ArrayList;

/**
 * Created by fbraun on 23.02.15.
 */
public class BookedList extends superList {

    public void fetchDevices() {
        RESTApiClient client = new RESTApiClient();
        client.fetchBookedDevices(new RESTApiClient.VolleyCallbackDeviceList() {
            @Override
            public void onSuccessListViews(ArrayList<Device> result) {
                dataList = result;
                ListOverviewAdapter listOverviewAdapter = new ListOverviewAdapter(dataList, BookedList.this);
                setListAdapter(listOverviewAdapter);
            }
        });
    }

}
