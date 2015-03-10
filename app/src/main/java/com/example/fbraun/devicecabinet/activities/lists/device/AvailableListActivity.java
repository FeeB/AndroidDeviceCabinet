package com.example.fbraun.devicecabinet.activities.lists.device;

import android.app.AlertDialog;
import android.content.DialogInterface;

import com.android.volley.NoConnectionError;
import com.android.volley.VolleyError;
import com.example.fbraun.devicecabinet.ErrorMapperRESTApiClient;
import com.example.fbraun.devicecabinet.RESTApiClient;
import com.example.fbraun.devicecabinet.model.Device;

import java.util.ArrayList;

public class AvailableListActivity extends AbstractDeviceListActivity {

    @Override
    public ArrayList<Device> fetchDevices() {
        final ArrayList<Device> devices = new ArrayList<>();
        RESTApiClient client = new RESTApiClient();
        client.fetchAvailableDevices(new RESTApiClient.VolleyCallbackDeviceList() {
            @Override
            public void onSuccessListViews(ArrayList<Device> result) {
                DeviceListAdapter deviceListAdapter = new DeviceListAdapter(result, AvailableListActivity.this);
                setListAdapter(deviceListAdapter);

                devices.addAll(result);
            }

            @Override
            public void onErrorListViews(VolleyError error) {
                ErrorMapperRESTApiClient errorMapper = new ErrorMapperRESTApiClient();
                errorMapper.handleError(error, AvailableListActivity.this);
            }
        });

        return devices;
    }

}
