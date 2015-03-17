package com.example.fbraun.devicecabinet.activities.lists.device;

import android.support.v4.app.ListFragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.fbraun.devicecabinet.activities.DeviceActivity;
import com.example.fbraun.devicecabinet.R;
import com.example.fbraun.devicecabinet.model.Device;

import java.util.ArrayList;

/**
 * Created by fbraun on 24.02.15.
 */
abstract class AbstractDeviceListFragment extends ListFragment {

    private ArrayList<Device> dataList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.dataList = fetchDevices();
        return inflater.inflate(R.layout.activity_list, container, false);
    }


    @Override
    public void onStart() {
        super.onStart();
        this.dataList = fetchDevices();
    }

    abstract ArrayList<Device> fetchDevices();

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        Intent intent = new Intent(getActivity(), DeviceActivity.class);
        Device device = dataList.get(position);
        intent.putExtra("device", device);

        startActivity(intent);
    }
}
