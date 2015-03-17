package com.example.fbraun.devicecabinet.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.example.fbraun.devicecabinet.errorhandling.ErrorMapperRESTApiClient;
import com.example.fbraun.devicecabinet.R;
import com.example.fbraun.devicecabinet.restnetworking.RESTApiClient;
import com.example.fbraun.devicecabinet.model.Device;

/**
 * Created by fbraun on 16.02.15.
 */
public class CreateDeviceActivity extends Activity {

    private Device device = new Device();
    private static String IPHONE = "iPhone";
    private static String IPAD = "iPad";
    private static String ANDROID_PHONE = "Android Phone";
    private static String ANDROID_TABLET = "Android Tablet";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_device);

        EditText deviceModel = (EditText) findViewById(R.id.device_model_text);
        deviceModel.setText(Build.MODEL);

        Button storeDevice = (Button) findViewById(R.id.save_device_button);
        storeDevice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                storeDevice(v);
            }
        });

        RadioGroup radioGroup = (RadioGroup) findViewById(R.id.radio_button_group);
        this.device.setType(ANDROID_PHONE);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch(checkedId) {
                    case R.id.iphone_option:
                        device.setType(IPHONE);
                        break;
                    case R.id.ipad_option:
                        device.setType(IPAD);
                        break;
                    case R.id.android_phone_option:
                        device.setType(ANDROID_PHONE);
                        break;
                    case R.id.android_tablet_option:
                        device.setType(ANDROID_TABLET);
                        break;
                }
            }
        });
    }

    public void storeDevice(View view) {
        TextView deviceName = (TextView) findViewById(R.id.device_name_text);
        device.setDeviceName(deviceName.getText().toString());

        TextView deviceModel = (TextView) findViewById(R.id.device_model_text);
        device.setDeviceModel(deviceModel.getText().toString());

        device.setSystemVersion(Build.VERSION.RELEASE);

        if (deviceName != null && deviceModel != null) {

           RESTApiClient client = new RESTApiClient();
            client.storeDevice(device, new RESTApiClient.VolleyCallbackCheckDevice() {
                @Override
                public void onFetchDeviceSuccess(Device device) {
                    Switch currentDeviceSwitch = (Switch) findViewById(R.id.switch_current_device);
                    if (currentDeviceSwitch.isChecked()) {
                        storeCurrentDevice(device);
                    }
                    finish();
                }

                @Override
                public void onFetchDeviceFailure(VolleyError error) {
                    ErrorMapperRESTApiClient errorMapperRESTApiClient = new ErrorMapperRESTApiClient();
                    errorMapperRESTApiClient.handleError(error, CreateDeviceActivity.this);
                }
            });
        } else {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle(getString(R.string.field_missing_title));
            builder.setMessage(getString(R.string.field_device_missing_message)).setCancelable(false).setPositiveButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });
        }
    }

    public void storeCurrentDevice(Device device) {
        SharedPreferences sharedPref = getSharedPreferences("MyPref", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("currentDevice", device.getDeviceId());
        editor.apply();
    }
}
