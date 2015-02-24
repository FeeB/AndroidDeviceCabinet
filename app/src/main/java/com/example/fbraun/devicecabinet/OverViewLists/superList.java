package com.example.fbraun.devicecabinet.OverViewLists;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.example.fbraun.devicecabinet.DeviceView;
import com.example.fbraun.devicecabinet.R;
import com.example.fbraun.devicecabinet.com.example.fbraun.devicecabinet.model.Device;

import java.util.ArrayList;

/**
 * Created by fbraun on 24.02.15.
 */
abstract class superList extends ListActivity {

    ArrayList<Device> dataList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        fetchDevices();
    }

    abstract void fetchDevices();

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        Intent intent = new Intent(this, DeviceView.class);
        Device device = dataList.get(position);
        intent.putExtra("device", device);

        startActivity(intent);
    }


}
