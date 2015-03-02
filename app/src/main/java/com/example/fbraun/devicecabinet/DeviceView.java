package com.example.fbraun.devicecabinet;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.fbraun.devicecabinet.com.example.fbraun.devicecabinet.model.Device;
import com.example.fbraun.devicecabinet.personList.PersonList;

/**
 * Created by fbraun on 16.02.15.
 */
public class DeviceView extends Activity {

    private Device device;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.device_page);

        TextView deviceName = (TextView) findViewById(R.id.deviceNameDeviceView);
        TextView system = (TextView) findViewById(R.id.systemVersionDeviceView);
        TextView type = (TextView) findViewById(R.id.typeTextDeviceView);
        TextView model = (TextView) findViewById(R.id.modelTextDeviceView);
        TextView person = (TextView) findViewById(R.id.personDeviceView);

        Intent intent = getIntent();
        if (intent != null) {
            device = intent.getParcelableExtra("device");
            deviceName.setText(device.deviceName);
            system.setText(device.systemVersion);
            type.setText(device.type);
            model.setText(device.deviceModel);
            if (device.bookedByPerson) {
                person.setText(device.bookedByPersonFullName);
            } else {
                ImageView personImage = (ImageView) findViewById(R.id.personImageDeviceView);
                personImage.setVisibility(View.GONE);
            }
        }

        Button bookDeviceButton = (Button) findViewById(R.id.button);
        if (device.bookedByPerson) {
            bookDeviceButton.setText("Return");
        }
        bookDeviceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bookReturnDevice(v);
            }
        });
    }

    public void bookReturnDevice(View view) {
        if (device.bookedByPerson) {
            RESTApiClient client = new RESTApiClient();
            client.deletePersonReferenceFromDevice(device, new RESTApiClient.VolleyCallbackStore() {
                @Override
                public void onStoreSuccess() {
                    finish();
                }
            });
        } else {
            Intent intent = new Intent(this, PersonList.class);
            intent.putExtra("device", device);
            startActivity(intent);
        }
    }
}
