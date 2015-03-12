package com.example.fbraun.devicecabinet.activities.lists.device;

import android.app.AlertDialog;
import android.support.v4.app.ListFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
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
abstract class AbstractDeviceListFragment extends ListFragment {

    private ArrayList<Device> dataList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.dataList = fetchDevices();
        return inflater.inflate(R.layout.activity_list, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        this.dataList = fetchDevices();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
    public void onListItemClick(ListView l, View v, int position, long id) {
        Intent intent = new Intent(getActivity(), DeviceActivity.class);
        Device device = dataList.get(position);
        intent.putExtra("device", device);

        startActivity(intent);
    }

    protected void removeItemFromPosition(int position) {
        final RESTApiClient client = new RESTApiClient();
        final int indexToDelete = position;

        AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());

        alert.setTitle(getString(R.string.delete));
        alert.setMessage(getString(R.string.delete_message));
        alert.setPositiveButton(getString(R.string.yes), new DialogInterface.OnClickListener() {
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
                        errorMapperRESTApiClient.handleError(error, getActivity());
                    }
                });
            }
        });
        alert.setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        alert.show();
    }


}
