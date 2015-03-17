package com.example.fbraun.devicecabinet;

import android.app.AlertDialog;
import android.app.Application;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.RemoteException;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.view.WindowManager;

import com.android.volley.VolleyError;
import com.example.fbraun.devicecabinet.activities.DeviceActivity;
import com.example.fbraun.devicecabinet.activities.DidEnterRegionActivity;
import com.example.fbraun.devicecabinet.activities.DidExitRegionActivity;
import com.example.fbraun.devicecabinet.model.Device;

import org.altbeacon.beacon.BeaconConsumer;
import org.altbeacon.beacon.BeaconManager;
import org.altbeacon.beacon.BeaconParser;
import org.altbeacon.beacon.Identifier;
import org.altbeacon.beacon.MonitorNotifier;
import org.altbeacon.beacon.Region;
import org.altbeacon.beacon.startup.BootstrapNotifier;
import org.altbeacon.beacon.startup.RegionBootstrap;

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
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            try {
                                isCurrentDeviceBooked = false;
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
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            try {
                                isCurrentDeviceBooked = true;
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
