package com.example.fbraun.devicecabinet;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.fbraun.devicecabinet.personList.PersonList;

/**
 * Created by fbraun on 16.02.15.
 */
public class DeviceView extends Activity {

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
            deviceName.setText(intent.getStringExtra("deviceName"));
            system.setText(intent.getStringExtra("system"));
            type.setText(intent.getStringExtra("type"));
            model.setText(intent.getStringExtra("model"));
            if (intent.getStringExtra("person") != null) {
                person.setText(intent.getStringExtra("person"));
            } else {
                ImageView personImage = (ImageView) findViewById(R.id.personImageDeviceView);
                personImage.setVisibility(View.GONE);
            }
        }

        Button bookDeviceButton = (Button) findViewById(R.id.button);
        bookDeviceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bookDevice(v);
            }
        });
    }

    //toDo Button book or return

    public void bookDevice (View view) {
        //toDo Button return
        Intent intent = new Intent(this, PersonList.class);
        startActivity(intent);
    }
}
