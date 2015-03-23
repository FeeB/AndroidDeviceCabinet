package com.example.fbraun.devicecabinet.activities.lists.person;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.android.volley.VolleyError;
import com.example.fbraun.devicecabinet.errorhandling.ErrorMapperRESTApiClient;
import com.example.fbraun.devicecabinet.R;
import com.example.fbraun.devicecabinet.restnetworking.RESTApiClient;
import com.example.fbraun.devicecabinet.model.Device;
import com.example.fbraun.devicecabinet.model.Person;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by fbraun on 16.02.15.
 */
public class PersonListActivity extends ListActivity {
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
                PersonListAdapter listOverviewAdapter = new PersonListAdapter(dataList, PersonListActivity.this);
                setListAdapter(listOverviewAdapter);
            }

            @Override
            public void onErrorListViews(VolleyError error) {

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
            public void onSaveSuccess() {
                finish();
            }

            @Override
            public void onStoreFailure(VolleyError error) {
                ErrorMapperRESTApiClient errorMapperRESTApiClient = new ErrorMapperRESTApiClient();
                errorMapperRESTApiClient.handleError(error, PersonListActivity.this);
            }
        });

    }

    protected void removeItemFromPosition(int position) {
        final RESTApiClient client = new RESTApiClient();
        final int indexToDelete = position;

        AlertDialog.Builder alert = new AlertDialog.Builder(this);

        alert.setTitle(getString(R.string.delete));
        alert.setMessage(getString(R.string.delete_message));
        alert.setPositiveButton(getString(R.string.yes), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                client.deletePerson(dataList.get(indexToDelete), new RESTApiClient.VolleyCallbackStore() {
                    @Override
                    public void onSaveSuccess() {
                        dataList.remove(indexToDelete);
                        fetchPersons();
                    }

                    @Override
                    public void onStoreFailure(VolleyError error) {
                        ErrorMapperRESTApiClient errorMapperRESTApiClient = new ErrorMapperRESTApiClient();
                        errorMapperRESTApiClient.handleError(error, PersonListActivity.this);
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
