package com.example.fbraun.devicecabinet;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.RemoteException;

import com.android.volley.VolleyError;
import com.example.fbraun.devicecabinet.activities.DidEnterRegionActivity;
import com.example.fbraun.devicecabinet.activities.DidExitRegionActivity;
import com.example.fbraun.devicecabinet.model.Device;
import com.example.fbraun.devicecabinet.restnetworking.RESTApiClient;

import org.altbeacon.beacon.Beacon;
import org.altbeacon.beacon.BeaconConsumer;
import org.altbeacon.beacon.BeaconManager;
import org.altbeacon.beacon.BeaconParser;
import org.altbeacon.beacon.Identifier;
import org.altbeacon.beacon.MonitorNotifier;
import org.altbeacon.beacon.Region;

/**
 * Created by Fee on 03.03.15.
 */
public class DeviceCabinet extends Application implements BeaconConsumer {

    private static final String TAG = ".DeviceCabinet";
    static Context instance;
    private BeaconManager mBeaconManager;
    private Device fetchedDevice;
    private static final String UUID = "f0018b9b-7509-4c31-a905-1a27d39c003c";
    private Boolean isCurrentDeviceBooked;

    public static Context getContext() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;

        SharedPreferences sharedPref = getSharedPreferences("MyPref", Context.MODE_PRIVATE);
        String deviceId = sharedPref.getString("currentDevice", null);
        if (deviceId != null) {

            RESTApiClient client = new RESTApiClient();
            client.fetchDeviceById(deviceId, new RESTApiClient.VolleyCallbackCheckDevice() {
                @Override
                public void onFetchDeviceSuccess(Device device) {
                    fetchedDevice = device;

                    mBeaconManager = BeaconManager.getInstanceForApplication(DeviceCabinet.this);
                    mBeaconManager.getBeaconParsers().add(new BeaconParser().setBeaconLayout("m:2-3=0215,i:4-19,i:20-21,i:22-23,p:24-24,d:25-25"));

                    mBeaconManager.bind(DeviceCabinet.this);
                }

                @Override
                public void onFetchDeviceFailure(VolleyError error) {

                }
            });
        }

    }

    @Override
    public void onBeaconServiceConnect() {
        mBeaconManager.setMonitorNotifier(new MonitorNotifier() {
            @Override
            public void didEnterRegion(Region region) {

                RESTApiClient client = new RESTApiClient();
                client.fetchDeviceById(fetchedDevice.getDeviceId(), new RESTApiClient.VolleyCallbackCheckDevice() {
                    @Override
                    public void onFetchDeviceSuccess(Device device) {
                        if (device.isBookedByPerson()) {
                            Intent intent = new Intent(instance, DidEnterRegionActivity.class);
                            intent.putExtra("device", device);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            isCurrentDeviceBooked = false;
                            try {
                                startActivity(intent);
                            } catch (Error e) {
                                System.out.println(e);
                            }
                        }
                    }

                    @Override
                    public void onFetchDeviceFailure(VolleyError error) {

                    }
                });

            }

            @Override
            public void didExitRegion(Region region) {
                RESTApiClient client = new RESTApiClient();
                client.fetchDeviceById(fetchedDevice.getDeviceId(), new RESTApiClient.VolleyCallbackCheckDevice() {
                    @Override
                    public void onFetchDeviceSuccess(Device device) {
                        if(!device.isBookedByPerson()) {
                            Intent intent = new Intent(instance, DidExitRegionActivity.class);
                            intent.putExtra("device", device);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            isCurrentDeviceBooked = true;
                            try {
                                startActivity(intent);
                            } catch (Error e) {
                                System.out.println(e);
                            }
                        }
                    }

                    @Override
                    public void onFetchDeviceFailure(VolleyError error) {

                    }
                });

            }

            @Override
            public void didDetermineStateForRegion(int state, Region region) {
                //nothing to do
            }
        });

        try {
            mBeaconManager.startMonitoringBeaconsInRegion(new Region("myMonitoringUniqueId", Identifier.parse(UUID), null, null));
        } catch (RemoteException e) {
        }
    }

}
