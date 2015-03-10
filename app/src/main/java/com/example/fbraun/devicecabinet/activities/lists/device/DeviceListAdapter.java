package com.example.fbraun.devicecabinet.activities.lists.device;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
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
    private static String ANDROID_PHONE = "Android Phone";
    private static String ANDROID_TABLET = "Android Tablet";

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
        deviceName.setText(device.getDeviceName());

        TextView deviceType = (TextView) view.findViewById(R.id.device_type_in_overview_activity);
        deviceType.setText(device.getDeviceModel());

        TextView system = (TextView) view.findViewById(R.id.system_in_overview_activity);
        system.setText(device.getSystemVersion());

        TextView person = (TextView) view.findViewById(R.id.person_name_in_overview_activity);
        person.setText(device.getBookedByPersonFullName());

        NetworkImageView image = (NetworkImageView) view.findViewById(R.id.device_image_in_overview_list);
        ImageLoader imageLoader = VolleySingleton.getInstance().getImageLoader();
        if (device.getImageUrl() != null) {
            image.setImageUrl(device.getImageUrl(), imageLoader);
        } else {
            image.setDefaultImageResId(R.drawable.placeholder);
            image.setImageUrl(null, imageLoader);
        }

        if (device.isBookedByPerson()) {
            ImageView personImage = (ImageView) view.findViewById(R.id.person_icon_in_overview_activity);
            personImage.setImageDrawable(context.getResources().getDrawable(R.drawable.user));
        }

        ImageView systemImage = (ImageView) view.findViewById(R.id.system_icon_image_view_in_overview_activity);
        if (device.getType().equals(ANDROID_PHONE) || device.getType().equals(ANDROID_TABLET)) {
            systemImage.setImageDrawable(context.getResources().getDrawable(R.drawable.android));
        } else {
            systemImage.setImageDrawable(context.getResources().getDrawable(R.drawable.apple));
        }

        return view;
    }

}

