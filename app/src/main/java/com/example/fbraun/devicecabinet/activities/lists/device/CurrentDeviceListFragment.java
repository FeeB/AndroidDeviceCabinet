package com.example.fbraun.devicecabinet.activities.lists.device;

import com.example.fbraun.devicecabinet.model.Device;

import java.util.ArrayList;

/**
 * Created by fbraun on 23.02.15.
 */
public class CurrentDeviceListFragment extends AbstractDeviceListFragment {

    public ArrayList<Device> fetchDevices() {

        //toDo real current Device

        Device iPhone = new Device();
        iPhone.setDeviceName("B4F-005");
        iPhone.setDeviceModel("iPhone 6");
        iPhone.setSystemVersion("8.1");
        iPhone.setType("iPhone");
        iPhone.setBookedByPerson(false);

        ArrayList<Device> devicesList = new ArrayList<Device>();

        devicesList.add(iPhone);
        DeviceListAdapter deviceListAdapter = new DeviceListAdapter(devicesList, getActivity());
        setListAdapter(deviceListAdapter);

        return devicesList;
    }
}
