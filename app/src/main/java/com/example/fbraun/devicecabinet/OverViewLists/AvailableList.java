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


public class AvailableList extends ListActivity {

    private static Context context;
    ArrayList<Device> dataList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        returnAvailableList();
        context = getApplicationContext();

    }

    public void returnAvailableList() {
        RESTApiClient client = new RESTApiClient();
        client.fetchAvailableDevices(new RESTApiClient.VolleyCallbackLists() {
            @Override
            public void onSuccessListViews(ArrayList<Device> result) {
                dataList = result;
                ListOverviewAdapter listOverviewAdapter = new ListOverviewAdapter(dataList, context);
                setListAdapter(listOverviewAdapter);
            }
        });
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        Intent intent = new Intent(this, DeviceView.class);
        Device device = dataList.get(position);
        intent.putExtra("deviceName", device.deviceName);
        intent.putExtra("system", device.systemVersion);
        intent.putExtra("type", device.type);
        intent.putExtra("model", device.deviceModel);
        intent.putExtra("person", device.bookedByPersonFullName);

        startActivity(intent);
    }

}
