package com.example.fbraun.devicecabinet.activities.lists.device;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import com.android.volley.VolleyError;
import com.example.fbraun.devicecabinet.ErrorMapperRESTApiClient;
import com.example.fbraun.devicecabinet.activities.DeviceActivity;
import com.example.fbraun.devicecabinet.R;
import com.example.fbraun.devicecabinet.RESTApiClient;
import com.example.fbraun.devicecabinet.model.Device;

import java.util.ArrayList;

/**
 * Created by fbraun on 24.02.15.
 */
abstract class AbstractDeviceListActivity extends ListActivity {

    // TODO: Refactor, this should be private
    private ArrayList<Device> dataList;

    @Override
    protected void onStart() {
        super.onStart();
        this.dataList = fetchDevices();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        this.getListView().setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                removeItemFromPosition(position);
                return true;
            }
        });
    }

    abstract ArrayList<Device> fetchDevices();

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        Intent intent = new Intent(this, DeviceActivity.class);
        Device device = dataList.get(position);
        intent.putExtra("device", device);

        startActivity(intent);
    }

    protected void removeItemFromPosition(int position) {
        final RESTApiClient client = new RESTApiClient();
        final int indexToDelete = position;

        AlertDialog.Builder alert = new AlertDialog.Builder(this);

        alert.setTitle("Delete");
        alert.setMessage("Do you want delete this item?");
        alert.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                client.deleteDevice(dataList.get(indexToDelete), new RESTApiClient.VolleyCallbackStore() {
                    @Override
                    public void onStoreSuccess() {
                        dataList.remove(indexToDelete);
                        fetchDevices();
                    }

                    @Override
                    public void onStoreFailure(VolleyError error) {
                        ErrorMapperRESTApiClient errorMapperRESTApiClient = new ErrorMapperRESTApiClient();
                        errorMapperRESTApiClient.handleError(error, AbstractDeviceListActivity.this);
                    }
                });
            }
        });
        alert.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        alert.show();
    }


}
