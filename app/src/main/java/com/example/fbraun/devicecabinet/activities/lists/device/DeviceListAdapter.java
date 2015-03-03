package com.example.fbraun.devicecabinet.activities.lists.device;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.example.fbraun.devicecabinet.R;
import com.example.fbraun.devicecabinet.VolleySingleton;
import com.example.fbraun.devicecabinet.model.Device;

import java.util.List;

/**
 * Created by fbraun on 03.02.15.
 */
public class DeviceListAdapter extends ArrayAdapter<Device> {

    private List<Device> dataList;
    private Context context;

    public DeviceListAdapter(List<Device> dataList, Context context) {
        super(context, R.layout.list_overview, dataList);
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
            view = inflater.inflate(R.layout.list_overview, parent, false);
        }

        Device device = dataList.get(position);

        TextView deviceName = (TextView) view.findViewById(R.id.device_name_label_in_overview_activity);
        deviceName.setText(device.deviceName);

        TextView deviceType = (TextView) view.findViewById(R.id.device_type_in_overview_activity);
        deviceType.setText(device.deviceModel);

        TextView system = (TextView) view.findViewById(R.id.system_in_overview_activity);
        system.setText(device.systemVersion);

        TextView person = (TextView) view.findViewById(R.id.person_name_in_overview_activity);
        person.setText(device.bookedByPersonFullName);

        if (device.imageUrl != null) {
            NetworkImageView image = (NetworkImageView) view.findViewById(R.id.device_image_in_overview_list);
            ImageLoader imageLoader = VolleySingleton.getInstance().getImageLoader();
            image.setImageUrl(device.imageUrl, imageLoader);
        }

        return view;
    }

}

