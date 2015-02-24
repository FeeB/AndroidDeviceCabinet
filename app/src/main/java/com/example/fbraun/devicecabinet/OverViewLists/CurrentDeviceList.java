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
import com.example.fbraun.devicecabinet.com.example.fbraun.devicecabinet.model.Device;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by fbraun on 23.02.15.
 */
public class CurrentDeviceList extends superList {

    public void fetchDevices() {

        //toDo real current Device

        Device iPhone = new Device();
        iPhone.deviceName = "B4F-005";
        iPhone.deviceModel = "iPhone 6";
        iPhone.systemVersion = "8.1";
        iPhone.type = "iPhone";

        ArrayList<Device> devicesList = new ArrayList<Device>();

        devicesList.add(iPhone);
        dataList = devicesList;
        ListOverviewAdapter listOverviewAdapter = new ListOverviewAdapter(dataList, this);
        setListAdapter(listOverviewAdapter);
    }
}
