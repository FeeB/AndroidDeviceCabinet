package com.example.fbraun.devicecabinet.activities.lists.person;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.example.fbraun.devicecabinet.errorhandling.ErrorMapperRESTApiClient;
import com.example.fbraun.devicecabinet.R;
import com.example.fbraun.devicecabinet.restnetworking.RESTApiClient;
import com.example.fbraun.devicecabinet.model.Person;

import java.util.List;

/**
 * Created by fbraun on 16.02.15.
 */
public class PersonListAdapter extends ArrayAdapter<Person> {

    private List<Person> dataList;
    private Context context;

    public PersonListAdapter(List<Person> dataList, Context context) {
        super(context, R.layout.list_person, dataList);
        this.dataList = dataList;
        this.context = context;
    }

    @Override
    public int getCount() {
        return dataList.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            // Inflate the layout according to the view type
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.list_person, parent, false);
        }

        Person person = dataList.get(position);

        TextView fullName = (TextView) view.findViewById(R.id.fullName);
        fullName.setText(person.getFullName());

        ImageView deleteBtn = (ImageView) view.findViewById(R.id.trash_icon_in_person_list_activity);

        deleteBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alert = new AlertDialog.Builder(context);

                alert.setTitle(context.getString(R.string.delete));
                alert.setMessage(context.getString(R.string.delete_message));
                alert.setPositiveButton(context.getString(R.string.yes), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        RESTApiClient client = new RESTApiClient();
                        client.deletePerson(dataList.get(position), new RESTApiClient.VolleyCallbackStore() {
                            @Override
                            public void onStoreSuccess() {
                                dataList.remove(position);
                                notifyDataSetChanged();
                            }

                            @Override
                            public void onStoreFailure(VolleyError error) {
                                ErrorMapperRESTApiClient errorMapperRESTApiClient = new ErrorMapperRESTApiClient();
                                errorMapperRESTApiClient.handleError(error, context);
                            }
                        });
                    }
                });
                alert.setNegativeButton(context.getString(R.string.cancel), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                alert.show();

            }
        });

        return view;
    }
}
