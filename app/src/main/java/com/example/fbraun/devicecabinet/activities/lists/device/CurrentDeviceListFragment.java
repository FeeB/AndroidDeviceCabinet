package com.example.fbraun.devicecabinet.activities.lists.device;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v4.app.FragmentTabHost;
import android.view.View;

import com.android.volley.VolleyError;
import com.example.fbraun.devicecabinet.ErrorMapperRESTApiClient;
import com.example.fbraun.devicecabinet.RESTApiClient;
import com.example.fbraun.devicecabinet.model.Device;

import java.util.ArrayList;

/**
 * Created by fbraun on 23.02.15.
 */
public class CurrentDeviceListFragment extends AbstractDeviceListFragment {
    private static final String DEFAULT_VALUE = "default";
    private SharedPreferences sharedPref;
    private FragmentTabHost tabHost;

    public ArrayList<Device> fetchDevices() {
        final ArrayList<Device> devicesList = new ArrayList<Device>();

        sharedPref = getActivity().getSharedPreferences("MyPref", Context.MODE_PRIVATE);
        String deviceId = sharedPref.getString("currentDevice", DEFAULT_VALUE);

        tabHost = (FragmentTabHost) getActivity().findViewById(android.R.id.tabhost);

        if (deviceId.equals(DEFAULT_VALUE)) {
            tabHost.getTabWidget().getChildTabViewAt(0).setVisibility(View.GONE);
            tabHost.setCurrentTab(1);
        } else {
            RESTApiClient client = new RESTApiClient();
            client.fetchDeviceById(deviceId, new RESTApiClient.VolleyCallbackCheckDevice() {
                @Override
                public void onFetchDeviceSuccess(Device device) {
                    devicesList.add(device);
                    DeviceListAdapter deviceListAdapter = new DeviceListAdapter(devicesList, getActivity());
                    setListAdapter(deviceListAdapter);
                }

                @Override
                public void onFetchDeviceFailure(VolleyError error) {
                    SharedPreferences.Editor editor = sharedPref.edit();
                    editor.putString("currentDevice", DEFAULT_VALUE);
                    editor.apply();
                    tabHost.getTabWidget().getChildTabViewAt(0).setVisibility(View.GONE);
                    tabHost.setCurrentTab(1);
                }
            });
        }
        return devicesList;
    }
}
