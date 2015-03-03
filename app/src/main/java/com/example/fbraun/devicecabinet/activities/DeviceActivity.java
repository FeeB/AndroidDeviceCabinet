package com.example.fbraun.devicecabinet.activities;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.example.fbraun.devicecabinet.R;
import com.example.fbraun.devicecabinet.RESTApiClient;
import com.example.fbraun.devicecabinet.VolleySingleton;
import com.example.fbraun.devicecabinet.model.Device;
import com.example.fbraun.devicecabinet.activities.lists.person.PersonListActivity;

/**
 * Created by fbraun on 16.02.15.
 */
public class DeviceActivity extends Activity {

    private Device device;
    private static final int CAMERA_REQUEST = 1888;
    private NetworkImageView image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.device_page);

        TextView deviceName = (TextView) findViewById(R.id.device_name_label_in_device_activity);
        TextView system = (TextView) findViewById(R.id.system_version_label_in_device_view);
        TextView type = (TextView) findViewById(R.id.type_text_in_device_view);
        TextView model = (TextView) findViewById(R.id.model_text_in_device_view);
        TextView person = (TextView) findViewById(R.id.person_name_in_device_view);
        image = (NetworkImageView) findViewById(R.id.image_device_view);

        Intent intent = getIntent();
        if (intent != null) {
            device = intent.getParcelableExtra("device");
            deviceName.setText(device.deviceName);
            system.setText(device.systemVersion);
            type.setText(device.type);
            model.setText(device.deviceModel);

            if (device.imageUrl != null) {
                ImageLoader imageLoader = VolleySingleton.getInstance().getImageLoader();
                image.setImageUrl(device.imageUrl, imageLoader);
            }

            if (device.bookedByPerson) {
                person.setText(device.bookedByPersonFullName);
            } else {
                ImageView personImage = (ImageView) findViewById(R.id.person_icon_in_device_view);
                personImage.setVisibility(View.GONE);
            }
        }

        Button bookDeviceButton = (Button) findViewById(R.id.book_return_button);
        if (device.bookedByPerson) {
            bookDeviceButton.setText("Return");
        }
        bookDeviceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bookReturnDevice(v);
            }
        });

        Button changePictureButton = (Button) findViewById(R.id.change_picture_button);
        changePictureButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                takeAPicture(v);
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
            Intent intent = new Intent(this, PersonListActivity.class);
            intent.putExtra("device", device);
            startActivity(intent);
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
            client.uploadImage(photo, device, new RESTApiClient.VolleyCallbackStore() {
                @Override
                public void onStoreSuccess() {
                    System.out.println("success");
                }
            });
        }
    }
}
