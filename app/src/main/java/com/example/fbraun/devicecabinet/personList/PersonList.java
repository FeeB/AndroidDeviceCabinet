package com.example.fbraun.devicecabinet.personList;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.fbraun.devicecabinet.OverViewLists.AvailableList;
import com.example.fbraun.devicecabinet.OverViewLists.ListOverviewAdapter;
import com.example.fbraun.devicecabinet.R;
import com.example.fbraun.devicecabinet.RESTApiClient;
import com.example.fbraun.devicecabinet.com.example.fbraun.devicecabinet.model.Device;
import com.example.fbraun.devicecabinet.com.example.fbraun.devicecabinet.model.Person;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by fbraun on 16.02.15.
 */
public class PersonList extends ListActivity {
    private ArrayAdapter<Person> listAdapter;
    private List<Person> dataList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        fetchPersons();
        this.getListView().setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                removeItemFromPosition(position);
                return true;
            }
        });
    }

    public void fetchPersons() {
        RESTApiClient client = new RESTApiClient();
        client.fetchAllPersons(new RESTApiClient.VolleyCallbackPersonList() {
            @Override
            public void onSuccessListViews(ArrayList<Person> result) {
                dataList = result;
                PersonListAdapter listOverviewAdapter = new PersonListAdapter(dataList, PersonList.this);
                setListAdapter(listOverviewAdapter);
            }
        });
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        Person person = dataList.get(position);
        Device device = getIntent().getParcelableExtra("device");
        RESTApiClient client = new RESTApiClient();
        client.storePersonReferenceInDeviceObject(person, device, new RESTApiClient.VolleyCallbackStore() {
            @Override
            public void onStoreSuccess() {
                finish();
            }
        });

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
                client.deletePerson(dataList.get(indexToDelete), new RESTApiClient.VolleyCallbackStore() {
                    @Override
                    public void onStoreSuccess() {
                        dataList.remove(indexToDelete);
                        fetchPersons();
                    }
                });
            }
        });
        alert.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // TODO Auto-generated method stub
                dialog.dismiss();
            }
        });

        alert.show();
    }
}
