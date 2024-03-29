package com.example.fbraun.devicecabinet.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.example.fbraun.devicecabinet.CircledNetworkImageView;
import com.example.fbraun.devicecabinet.errorhandling.ErrorMapperRESTApiClient;
import com.example.fbraun.devicecabinet.R;
import com.example.fbraun.devicecabinet.restnetworking.RESTApiClient;
import com.example.fbraun.devicecabinet.restnetworking.VolleySingleton;
import com.example.fbraun.devicecabinet.model.Device;
import com.example.fbraun.devicecabinet.activities.lists.person.PersonListActivity;

/**
 * Created by fbraun on 16.02.15.
 */
public class DeviceActivity extends Activity {

    private Device device;
    private static final int CAMERA_REQUEST = 1888;
    private CircledNetworkImageView image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.device_page);

        TextView deviceName = (TextView) findViewById(R.id.device_name_label_in_device_activity);
        TextView system = (TextView) findViewById(R.id.system_version_label_in_device_view);
        TextView type = (TextView) findViewById(R.id.type_text_in_device_view);
        TextView model = (TextView) findViewById(R.id.model_text_in_device_view);
        TextView person = (TextView) findViewById(R.id.person_name_in_device_view);
        image = (CircledNetworkImageView) findViewById(R.id.image_device_view);

        Button bookDeviceButton = (Button) findViewById(R.id.book_return_button);

        bookDeviceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bookReturnDevice(v);
            }
        });

        Intent intent = getIntent();
        if (intent != null) {
            device = intent.getParcelableExtra("device");
            deviceName.setText(device.getDeviceName());
            system.setText(device.getSystemVersion());
            type.setText(device.getType());
            model.setText(device.getDeviceModel());

            if (intent.getBooleanExtra("beacon", false)) {
                bookDeviceButton.performClick();
            }

            ImageLoader imageLoader = VolleySingleton.getInstance().getImageLoader();

            image.setDefaultImageResId(R.drawable.placeholder);
            image.setErrorImageResId(R.drawable.placeholder);
            image.setImageUrl(device.getImageUrl(), imageLoader);

            if (device.isBookedByPerson()) {
                person.setText(device.getBookedByPersonFullName());
            } else {
                ImageView personImage = (ImageView) findViewById(R.id.person_icon_in_device_activity);
                personImage.setVisibility(View.GONE);
            }

            SharedPreferences sharedPref = getSharedPreferences("MyPref", Context.MODE_PRIVATE);
            String currentDeviceId = sharedPref.getString("currentDevice", null);

            if (currentDeviceId != null && currentDeviceId.equals(device.getDeviceId())) {
                if (!device.getSystemVersion().equals(Build.VERSION.RELEASE)) {
                    device.setSystemVersion(Build.VERSION.RELEASE);
                    RESTApiClient client = new RESTApiClient();
                    client.updateSystemVersion(device);
                }
            }
        }

        if (device.isBookedByPerson()) {
            bookDeviceButton.setText(getString(R.string.return_device));
        }


        Button changePictureButton = (Button) findViewById(R.id.change_picture_button);
        changePictureButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                takeAPicture(v);
            }
        });
    }

    public void bookReturnDevice(View view) {
        if (device.isBookedByPerson()) {
            RESTApiClient client = new RESTApiClient();
            client.deletePersonReferenceFromDevice(device, new RESTApiClient.VolleyCallbackStore() {
                @Override
                public void onStoreSuccess() {
                    finish();
                }

                @Override
                public void onStoreFailure(VolleyError error) {
                    ErrorMapperRESTApiClient errorMapper = new ErrorMapperRESTApiClient();
                    errorMapper.handleError(error, DeviceActivity.this);
                }
            });
        } else {
            Intent intent = new Intent(this, PersonListActivity.class);
            intent.putExtra("device", device);
            startActivity(intent);
            finish();
        }
    }

    public void takeAPicture(View view) {
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(cameraIntent, CAMERA_REQUEST);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAMERA_REQUEST && resultCode == RESULT_OK) {
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            image.setImageBitmap(photo);
            RESTApiClient client = new RESTApiClient();
            client.uploadImage(photo, device);
        }
    }
}
