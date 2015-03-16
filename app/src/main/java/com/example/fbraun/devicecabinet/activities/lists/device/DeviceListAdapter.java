package com.example.fbraun.devicecabinet.activities.lists.device;

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
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.example.fbraun.devicecabinet.CircledNetworkImageView;
import com.example.fbraun.devicecabinet.ErrorMapperRESTApiClient;
import com.example.fbraun.devicecabinet.R;
import com.example.fbraun.devicecabinet.RESTApiClient;
import com.example.fbraun.devicecabinet.VolleySingleton;
import com.example.fbraun.devicecabinet.activities.DeviceActivity;
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
    public View getView(final int position, View convertView, ViewGroup parent) {
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

        CircledNetworkImageView image = (CircledNetworkImageView) view.findViewById(R.id.device_image_in_overview_list);
        ImageLoader imageLoader = VolleySingleton.getInstance().getImageLoader();

        image.setDefaultImageResId(R.drawable.placeholder);
        image.setErrorImageResId(R.drawable.placeholder);
        image.setImageUrl(device.getImageUrl(), imageLoader);

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

        ImageView deleteBtn = (ImageView) view.findViewById(R.id.trash_icon_in_overview_activity);

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
                        client.deleteDevice(dataList.get(position), new RESTApiClient.VolleyCallbackStore() {
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

