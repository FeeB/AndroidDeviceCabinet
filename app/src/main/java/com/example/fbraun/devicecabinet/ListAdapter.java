package com.example.fbraun.devicecabinet;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.fbraun.devicecabinet.com.example.fbraun.devicecabinet.model.Device;

import java.util.List;

/**
 * Created by fbraun on 03.02.15.
 */
public class ListAdapter extends ArrayAdapter<Device> {

    private List<Device> dataList;
    private Context context;

    public ListAdapter(List<Device> dataList, Context context) {
        super(context, R.layout.list_view, dataList);
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
        int type = getItemViewType(position);
        if (view == null) {
            // Inflate the layout according to the view type
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.list_view, parent, false);
        }

        Device device = dataList.get(position);

        TextView deviceName = (TextView) view.findViewById(R.id.deviceNameDeviceView);
        deviceName.setText(device.deviceName);

        TextView deviceType = (TextView) view.findViewById(R.id.deviceTypeDeviceView);
        deviceType.setText(device.deviceType);

        TextView system = (TextView) view.findViewById(R.id.system);
        system.setText(device.systemVersion);

        TextView person = (TextView) view.findViewById(R.id.personDeviceView);
        person.setText(device.bookedByPersonFullName);

        return view;
    }

}

