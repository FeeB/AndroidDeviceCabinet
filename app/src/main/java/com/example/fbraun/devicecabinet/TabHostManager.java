package com.example.fbraun.devicecabinet;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TabHost;

import com.example.fbraun.devicecabinet.activities.CreateDeviceActivity;
import com.example.fbraun.devicecabinet.activities.CreatePersonActivity;
import com.example.fbraun.devicecabinet.activities.lists.device.AvailableListActivity;
import com.example.fbraun.devicecabinet.activities.lists.device.BookedListActivity;
import com.example.fbraun.devicecabinet.activities.lists.device.CurrentDeviceListActivity;

/**
 * Created by fbraun on 23.02.15.
 */

//todo whats wrong?
public class TabHostManager extends TabActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tab_navigation);
        TabHost tabHost = getTabHost();

        TabHost.TabSpec current = tabHost.newTabSpec("Current");
        current.setIndicator("Current");
        Intent currentIntent = new Intent(this, CurrentDeviceListActivity.class);
        current.setContent(currentIntent);

        TabHost.TabSpec available = tabHost.newTabSpec("available");
        available.setIndicator("Available");
        Intent availableIntent = new Intent(this, AvailableListActivity.class);
        available.setContent(availableIntent);

        TabHost.TabSpec booked = tabHost.newTabSpec("booked");
        booked.setIndicator("Booked");
        Intent bookedIntent = new Intent(this, BookedListActivity.class);
        booked.setContent(bookedIntent);

        tabHost.addTab(current);
        tabHost.addTab(available);
        tabHost.addTab(booked);
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
        // Handle presses on the action bar items
        switch (item.getItemId()) {
            case R.id.create_new_Device:
                Intent intentDevice = new Intent(this, CreateDeviceActivity.class);
                startActivity(intentDevice);
                return true;
            case R.id.create_new_Person:
                Intent intentPerson = new Intent(this, CreatePersonActivity.class);
                startActivity(intentPerson);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
