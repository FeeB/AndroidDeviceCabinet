package com.example.fbraun.devicecabinet.activities.lists.person;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.fbraun.devicecabinet.R;
import com.example.fbraun.devicecabinet.model.Device;
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
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            // Inflate the layout according to the view type
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.list_person, parent, false);
        }

        Person person = dataList.get(position);

        TextView fullName = (TextView) view.findViewById(R.id.fullName);
        fullName.setText(person.getFullName());

        return view;
    }
}
