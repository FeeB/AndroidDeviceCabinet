package com.example.fbraun.devicecabinet;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.fbraun.devicecabinet.com.example.fbraun.devicecabinet.model.Device;

import org.w3c.dom.Text;

/**
 * Created by fbraun on 16.02.15.
 */
public class CreateDeviceView extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_device);
    }

    public void storeDevice(View view) {
        Device device = new Device();

        TextView deviceName = (TextView) findViewById(R.id.deviceNameText);
        device.deviceName = deviceName.getText().toString();

        TextView deviceModel = (TextView) findViewById(R.id.deviceModelText);
        device.deviceModel = deviceModel.getText().toString();

        if (deviceName != null && deviceModel != null) {
            //toDo store Device
        }
    }
}
