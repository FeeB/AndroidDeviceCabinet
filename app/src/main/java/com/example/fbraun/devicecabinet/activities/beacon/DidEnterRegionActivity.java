package com.example.fbraun.devicecabinet.activities.beacon;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.TaskStackBuilder;
import android.view.WindowManager;

import com.example.fbraun.devicecabinet.R;
import com.example.fbraun.devicecabinet.activities.DeviceActivity;
import com.example.fbraun.devicecabinet.model.Device;

/**
 * Created by fbraun on 17.03.15.
 */
public class DidEnterRegionActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int id = 1;

        Intent intent = getIntent();
        if (intent != null) {
            Device device = intent.getParcelableExtra("device");
            Intent resultIntent = new Intent(this, DeviceActivity.class);
            resultIntent.putExtra("device", device);
            resultIntent.putExtra("beacon", true);

            final TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
            stackBuilder.addParentStack(DeviceActivity.class);
            stackBuilder.addNextIntent(resultIntent);


            final PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);

            AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this, AlertDialog.THEME_DEVICE_DEFAULT_DARK);
            dialogBuilder.setIcon(R.drawable.app_icon).setTitle(getString(R.string.return_device)).setMessage(getString(R.string.return_message));
            dialogBuilder.setPositiveButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    //stackBuilder.startActivities();
                    try {
                        resultPendingIntent.send();
                    } catch (PendingIntent.CanceledException e) {
                        e.printStackTrace();
                    }

                }
            });
            dialogBuilder.setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    finish();
                }
            });

            AlertDialog alert = dialogBuilder.create();
            alert.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
            alert.show();
        }
    }
}
