package com.example.fbraun.devicecabinet;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ServerError;
import com.android.volley.VolleyError;

/**
 * Created by fbraun on 10.03.15.
 */
public class ErrorMapperRESTApiClient {

    public void handleError(VolleyError error, Context context) {
        if (error instanceof NoConnectionError) {
            showAlert(context, context.getString(R.string.connection_error_title), context.getString(R.string.connection_error_message));
        } else if (error instanceof DuplicatedError) {
            showAlert(context, context.getString(R.string.device_already_exists_title), context.getString(R.string.device_already_exists_message));
        } else if (error instanceof AlreadyBookedError) {
            showAlert(context, context.getString(R.string.device_already_booked_title), context.getString(R.string.device_already_booked_message));
        } else {
            showAlert(context, context.getString(R.string.something_went_wrong_title), context.getString(R.string.something_went_wrong_message));
        }
    }

    private void showAlert(Context context, String title, String message) {
        AlertDialog.Builder alert = new AlertDialog.Builder(context);

        alert.setTitle(title);
        alert.setMessage(message);
        alert.setPositiveButton(context.getString(R.string.ok), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        alert.show();
    }
}
