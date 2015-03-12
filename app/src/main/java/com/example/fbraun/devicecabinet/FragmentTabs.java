package com.example.fbraun.devicecabinet;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTabHost;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.fbraun.devicecabinet.activities.CreateDeviceActivity;
import com.example.fbraun.devicecabinet.activities.CreatePersonActivity;
import com.example.fbraun.devicecabinet.activities.lists.device.AvailableListFragment;
import com.example.fbraun.devicecabinet.activities.lists.device.BookedListFragment;
import com.example.fbraun.devicecabinet.activities.lists.device.CurrentDeviceListFragment;

/**
 * Created by fbraun on 23.02.15.
 */

public class FragmentTabs extends FragmentActivity {
    private static String AVAILABLE = "available";
    private static String CURRENT = "current";
    private static String BOOKED = "booked";
    private static final String DEFAULT_VALUE = "default";

    private FragmentTabHost tabHost;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.tab_navigation);
        tabHost = (FragmentTabHost)findViewById(android.R.id.tabhost);
        tabHost.setup(this, getSupportFragmentManager(), android.R.id.tabcontent);

        tabHost.addTab(tabHost.newTabSpec(CURRENT).setIndicator(getResources().getString(R.string.current)), CurrentDeviceListFragment.class, null);
        tabHost.addTab(tabHost.newTabSpec(AVAILABLE).setIndicator(getResources().getString(R.string.available)), AvailableListFragment.class, null);
        tabHost.addTab(tabHost.newTabSpec(BOOKED).setIndicator(getResources().getString(R.string.booked)), BookedListFragment.class, null);
    }

    @Override
    public void onStart(){
        super.onStart();
        SharedPreferences sharedPref = getSharedPreferences("MyPref", Context.MODE_PRIVATE);
        String deviceId = sharedPref.getString("currentDevice", DEFAULT_VALUE);

        if (!deviceId.equals(DEFAULT_VALUE)) {
            tabHost.getTabWidget().getChildTabViewAt(0).setVisibility(View.VISIBLE);
            tabHost.setCurrentTab(0);
        }
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
