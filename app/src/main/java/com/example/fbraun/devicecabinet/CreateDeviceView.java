package com.example.fbraun.devicecabinet;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.TextView;

import com.example.fbraun.devicecabinet.com.example.fbraun.devicecabinet.model.Device;

import org.w3c.dom.Text;

/**
 * Created by fbraun on 16.02.15.
 */
public class CreateDeviceView extends Activity {

    private Device device = new Device();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_device);

        EditText deviceModel = (EditText) findViewById(R.id.deviceModelText);
        deviceModel.setText(Build.MODEL);

        Button storeDevice = (Button) findViewById(R.id.saveButton);
        storeDevice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                storeDevice(v);
            }
        });

        RadioGroup radioGroup = (RadioGroup) findViewById(R.id.radioButtonGroup);
        this.device.type = "Android Phone";
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch(checkedId) {
                    case R.id.iPhone:
                        device.type = "iPhone";
                        break;
                    case R.id.iPad:
                        device.type = "iPad";
                        break;
                    case R.id.Android:
                        device.type = "Android Phone";
                        break;
                    case R.id.AndroidTablet:
                        device.type = "Android Tablet";
                        break;
                }
            }
        });

        Switch currentDeviceSwitch = (Switch) findViewById(R.id.Switch);
        currentDeviceSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    //toDo store Device as current device
                }
            }
        });
    }

    public void storeDevice(View view) {
        TextView deviceName = (TextView) findViewById(R.id.deviceNameText);
        device.deviceName = deviceName.getText().toString();

        TextView deviceModel = (TextView) findViewById(R.id.deviceModelText);
        device.deviceModel = deviceModel.getText().toString();

        device.systemVersion = Build.VERSION.RELEASE;

        if (deviceName != null && deviceModel != null) {
           RESTApiClient client = new RESTApiClient();
            client.storeDevice(device, new RESTApiClient.VolleyCallbackStore() {
                @Override
                public void onStoreSuccess() {
                    finish();
                }
            });
        } else {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Error");
            builder.setMessage("try later").setCancelable(false).setPositiveButton("ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });
        }
    }
}
