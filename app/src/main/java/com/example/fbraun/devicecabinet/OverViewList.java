package com.example.fbraun.devicecabinet;

import android.app.ActionBar;
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
        setContentView(R.layout.activity_main);

        dataList = returnData();
        context = getApplicationContext();
        ListAdapter listAdapter = new ListAdapter(dataList, context);
        setListAdapter(listAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        return super.onOptionsItemSelected(item);
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


}
