package com.example.fbraun.devicecabinet.OverViewLists;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TabHost;

import com.example.fbraun.devicecabinet.CreateDeviceView;
import com.example.fbraun.devicecabinet.CreatePersonView;
import com.example.fbraun.devicecabinet.R;

/**
 * Created by fbraun on 23.02.15.
 */
public class TabHostManager extends TabActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tab_navigation);
        TabHost tabHost = getTabHost();

        TabHost.TabSpec current = tabHost.newTabSpec("Current");
        current.setIndicator("Current");
        Intent currentIntent = new Intent(this, CurrentDeviceList.class);
        current.setContent(currentIntent);

        TabHost.TabSpec available = tabHost.newTabSpec("available");
        available.setIndicator("Available");
        Intent availableIntent = new Intent(this, AvailableList.class);
        available.setContent(availableIntent);

        TabHost.TabSpec booked = tabHost.newTabSpec("booked");
        booked.setIndicator("Booked");
        Intent bookedIntent = new Intent(this, BookedList.class);
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
