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
            showAlert(context, "Connection Error", "Sorry, there is no Connection to the Server, check the connection and try again");
        } else if (error instanceof DuplicatedError) {
            showAlert(context, "Device already existing", "Device with same name already existing!");
        } else if (error instanceof AlreadyBookedError) {
            showAlert(context, "Device is already booked", "Sorry, someone booked the device already!");
        } else {
            showAlert(context, "Something went wrong", "Sorry, something went wrong please try again later");
        }
    }

    private void showAlert(Context context, String title, String message) {
        AlertDialog.Builder alert = new AlertDialog.Builder(context);

        alert.setTitle(title);
        alert.setMessage(message);
        alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        alert.show();
    }
}
