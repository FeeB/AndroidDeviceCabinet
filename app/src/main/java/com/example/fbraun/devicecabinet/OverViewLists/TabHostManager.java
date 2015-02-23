package com.example.fbraun.devicecabinet.OverViewLists;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TabHost;

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


}
