package com.example.fbraun.devicecabinet;

import android.app.Application;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.RemoteException;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;

import com.android.volley.VolleyError;
import com.example.fbraun.devicecabinet.activities.DeviceActivity;
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
public class DeviceCabinet extends Application implements BootstrapNotifier, BeaconConsumer {

    static Context instance;
    private static final String TAG = ".DeviceCabinet";
    private RegionBootstrap regionBootstrap;
    private BeaconManager mBeaconManager;
    private Region mAllBeaconsRegion;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;


        mBeaconManager = BeaconManager.getInstanceForApplication(this);
        //mBeaconManager.getBeaconParsers().add(new BeaconParser().setBeaconLayout("m:2-3=0215,i:4-19,i:20-21,i:22-23,p:24-24,d:25-25"));

        mAllBeaconsRegion = new Region("MyRegion", null, null, null);
        //mAllBeaconsRegion = new Region("MyRegion", Identifier.parse("f0018b9b-7509-4c31-a905-1a27d39c003c"), Identifier.parse("1"), Identifier.parse("1"));
        regionBootstrap = new RegionBootstrap(this, mAllBeaconsRegion);
        mBeaconManager.bind(this);
    }

    public static Context getContext() {
        return instance;
    }

    @Override
    public void didDetermineStateForRegion(int arg0, Region arg1) {
        // Don't care
    }

    @Override
    public void onBeaconServiceConnect() {
        mBeaconManager.setMonitorNotifier(new MonitorNotifier() {
            @Override
            public void didEnterRegion(Region region) {
                int id = 1;

                Intent resultIntent = new Intent(DeviceCabinet.this, DeviceActivity.class);

                TaskStackBuilder stackBuilder = TaskStackBuilder.create(DeviceCabinet.this);
                stackBuilder.addParentStack(DeviceActivity.class);
                stackBuilder.addNextIntent(resultIntent);

                PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);

                NotificationCompat.Builder builder = new NotificationCompat.Builder(DeviceCabinet.this).setSmallIcon(R.drawable.app_icon).setContentTitle("Ausleihen").setContentText("Jetzt ausleihen!");
                builder.setContentIntent(resultPendingIntent);
                NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                mNotificationManager.notify(id, builder.build());
            }

            @Override
            public void didExitRegion(Region region) {
                int id = 2;

                Intent resultIntent = new Intent(DeviceCabinet.this, DeviceActivity.class);

                TaskStackBuilder stackBuilder = TaskStackBuilder.create(DeviceCabinet.this);
                stackBuilder.addParentStack(DeviceActivity.class);
                stackBuilder.addNextIntent(resultIntent);

                PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);

                NotificationCompat.Builder builder = new NotificationCompat.Builder(DeviceCabinet.this).setSmallIcon(R.drawable.app_icon).setContentTitle("Zurückgeben").setContentText("Jetzt zurückgeben!");
                builder.setContentIntent(resultPendingIntent);
                NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                mNotificationManager.notify(id, builder.build());
            }

            @Override
            public void didDetermineStateForRegion(int state, Region region) {

            }
        });

        try {
            mBeaconManager.startMonitoringBeaconsInRegion(new Region("myMonitoringUniqueId", null, null, null));
        } catch (RemoteException e) {    }
    }


    @Override
    public void didEnterRegion(Region arg0) {
        regionBootstrap.disable();

        SharedPreferences sharedPref = getSharedPreferences("MyPref", Context.MODE_PRIVATE);
        String deviceId = sharedPref.getString("currentDevice", null);

        RESTApiClient client = new RESTApiClient();
        client.fetchDeviceById(deviceId, new RESTApiClient.VolleyCallbackCheckDevice() {
            @Override
            public void onFetchDeviceSuccess(Device device) {
                int id = 3;

                Intent resultIntent = new Intent(DeviceCabinet.this, DeviceActivity.class);
                resultIntent.putExtra("device", device);
                resultIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                TaskStackBuilder stackBuilder = TaskStackBuilder.create(DeviceCabinet.this);
                stackBuilder.addParentStack(DeviceActivity.class);
                stackBuilder.addNextIntent(resultIntent);

                PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);

                NotificationCompat.Builder builder = new NotificationCompat.Builder(DeviceCabinet.this).setSmallIcon(R.drawable.app_icon).setContentTitle("Zurückgeben").setContentText("Jetzt zurückgeben!");
                builder.setContentIntent(resultPendingIntent);
                NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                mNotificationManager.notify(id, builder.build());
            }

            @Override
            public void onFetchDeviceFailure(VolleyError error) {
                ErrorMapperRESTApiClient errorMapperRESTApiClient = new ErrorMapperRESTApiClient();
                errorMapperRESTApiClient.handleError(error, DeviceCabinet.this);
            }
        });
    }

    @Override
    public void didExitRegion(Region arg0) {
        regionBootstrap.disable();

        SharedPreferences sharedPref = getSharedPreferences("MyPref", Context.MODE_PRIVATE);
        String deviceId = sharedPref.getString("currentDevice", null);

        RESTApiClient client = new RESTApiClient();
        client.fetchDeviceById(deviceId, new RESTApiClient.VolleyCallbackCheckDevice() {
            @Override
            public void onFetchDeviceSuccess(Device device) {
                int id = 4;

                Intent resultIntent = new Intent(DeviceCabinet.this, DeviceActivity.class);
                resultIntent.putExtra("device", device);
                resultIntent.putExtra("return", true);
                resultIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                TaskStackBuilder stackBuilder = TaskStackBuilder.create(DeviceCabinet.this);
                stackBuilder.addParentStack(DeviceActivity.class);
                stackBuilder.addNextIntent(resultIntent);

                PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);

                NotificationCompat.Builder builder = new NotificationCompat.Builder(DeviceCabinet.this).setSmallIcon(R.drawable.app_icon).setContentTitle("Zurückgeben").setContentText("Jetzt zurückgeben!");
                builder.setContentIntent(resultPendingIntent);
                NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                mNotificationManager.notify(id, builder.build());
            }

            @Override
            public void onFetchDeviceFailure(VolleyError error) {
                ErrorMapperRESTApiClient errorMapperRESTApiClient = new ErrorMapperRESTApiClient();
                errorMapperRESTApiClient.handleError(error, DeviceCabinet.this);
            }
        });
    }

}
