package com.example.fbraun.devicecabinet;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

import com.example.fbraun.devicecabinet.com.example.fbraun.devicecabinet.model.Device;

import java.util.ArrayList;
import java.util.List;


public class OverViewList extends ListActivity {

    private static Context context;
    List<Device> dataList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        dataList = returnData();
        context = getApplicationContext();
        ListOverviewAdapter listOverviewAdapter = new ListOverviewAdapter(dataList, context);
        setListAdapter(listOverviewAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    public List<Device> returnData() {
        Device iPhone = new Device();
        iPhone.deviceName = "B4F-001";
        iPhone.deviceType = "iPhone 4s";
        iPhone.systemVersion = "Android 4";
        iPhone.type = "iPhone";

        Device nexus = new Device();
        nexus.deviceName = "B4F-002";
        nexus.deviceType = "Nexus";
        nexus.systemVersion = "iOS8";
        nexus.type = "Android Tablet";
        nexus.bookedByPersonFullName = "Fee Braun";

        List<Device> devicesList = new ArrayList<Device>();

        devicesList.add(iPhone);
        devicesList.add(nexus);

        return devicesList;
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        Intent intent = new Intent(this, DeviceView.class);
        Device device = dataList.get(position);
        intent.putExtra("deviceName", device.deviceName);
        intent.putExtra("system", device.systemVersion);
        intent.putExtra("type", device.type);
        intent.putExtra("model", device.deviceType);
        intent.putExtra("person", device.bookedByPersonFullName);

        startActivity(intent);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items
        switch (item.getItemId()) {
            case R.id.create_new_Device:
                Intent intentDevice = new Intent(this, CreateDeviceView.class);
                startActivity(intentDevice);
                return true;
            case R.id.create_new_Person:
                Intent intentPerson = new Intent(this, CreatePersonView.class);
                startActivity(intentPerson);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
