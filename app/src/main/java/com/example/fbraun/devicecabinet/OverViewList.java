package com.example.fbraun.devicecabinet;

import android.content.Context;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.example.fbraun.devicecabinet.com.example.fbraun.devicecabinet.model.Device;

import java.util.ArrayList;
import java.util.List;


public class OverViewList extends ActionBarActivity {

    private static Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        List<Device> dataList = returnData();
        context = getApplicationContext();
        ListAdapter listAdapter = new ListAdapter(dataList, context);
        ListView listView = (ListView)findViewById(R.id.listView);
        listView.setAdapter(listAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
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

        Device nexus = new Device();
        nexus.deviceName = "B4F-002";
        nexus.deviceType = "Nexus";
        nexus.systemVersion = "iOS8";
        nexus.bookedByPersonFullName = "Fee Braun";

        List<Device> devicesList = new ArrayList<Device>();

        devicesList.add(iPhone);
        devicesList.add(nexus);

        return devicesList;
    }


}
