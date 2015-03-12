package com.example.fbraun.devicecabinet.activities.lists.device;

import com.android.volley.VolleyError;
import com.example.fbraun.devicecabinet.ErrorMapperRESTApiClient;
import com.example.fbraun.devicecabinet.RESTApiClient;
import com.example.fbraun.devicecabinet.model.Device;

import java.util.ArrayList;

/**
 * Created by fbraun on 23.02.15.
 */
public class BookedListFragment extends AbstractDeviceListFragment {

    public ArrayList<Device> fetchDevices() {
        final ArrayList<Device> devices = new ArrayList<>();
        RESTApiClient client = new RESTApiClient();
        client.fetchBookedDevices(new RESTApiClient.VolleyCallbackDeviceList() {
            @Override
            public void onSuccessListViews(ArrayList<Device> result) {
                DeviceListAdapter deviceListAdapter = new DeviceListAdapter(result, getActivity());
                setListAdapter(deviceListAdapter);

                devices.addAll(result);
            }

            @Override
            public void onErrorListViews(VolleyError error) {
                ErrorMapperRESTApiClient errorMapper = new ErrorMapperRESTApiClient();
                errorMapper.handleError(error, getActivity());
            }
        });

        return devices;
    }

}
