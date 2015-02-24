package com.example.fbraun.devicecabinet.OverViewLists;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

import com.example.fbraun.devicecabinet.CreateDeviceView;
import com.example.fbraun.devicecabinet.CreatePersonView;
import com.example.fbraun.devicecabinet.DeviceView;
import com.example.fbraun.devicecabinet.R;
import com.example.fbraun.devicecabinet.RESTApiClient;
import com.example.fbraun.devicecabinet.com.example.fbraun.devicecabinet.model.Device;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by fbraun on 23.02.15.
 */
public class BookedList extends superList {

    public void fetchDevices() {
        RESTApiClient client = new RESTApiClient();
        client.fetchBookedDevices(new RESTApiClient.VolleyCallbackLists() {
            @Override
            public void onSuccessListViews(ArrayList<Device> result) {
                dataList = result;
                ListOverviewAdapter listOverviewAdapter = new ListOverviewAdapter(dataList, BookedList.this);
                setListAdapter(listOverviewAdapter);
            }
        });
    }

}
