package com.example.fbraun.devicecabinet.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.example.fbraun.devicecabinet.errorhandling.ErrorMapperRESTApiClient;
import com.example.fbraun.devicecabinet.R;
import com.example.fbraun.devicecabinet.restnetworking.RESTApiClient;
import com.example.fbraun.devicecabinet.model.Person;

/**
 * Created by fbraun on 16.02.15.
 */
public class CreatePersonActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_person);

        Button storeDevice = (Button) findViewById(R.id.save_person_button);
        storeDevice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                storePerson(v);
            }
        });
    }

    public void storePerson(View view) {
        Person person = new Person();
        TextView firstName = (TextView) findViewById(R.id.first_name_text);
        person.setFirstName(firstName.getText().toString());

        TextView lastName = (TextView) findViewById(R.id.last_name_text);
        person.setLastName(lastName.getText().toString());

        if (firstName != null && lastName != null) {
            RESTApiClient client = new RESTApiClient();
            client.storePerson(person, new RESTApiClient.VolleyCallbackStore() {
                @Override
                public void onSaveSuccess() {
                    finish();
                }

                @Override
                public void onStoreFailure(VolleyError error) {
                    ErrorMapperRESTApiClient errorMapperRESTApiClient = new ErrorMapperRESTApiClient();
                    errorMapperRESTApiClient.handleError(error, CreatePersonActivity.this);
                }
            });
        } else {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle(getString(R.string.field_missing_title));
            builder.setMessage(getString(R.string.field_person_missing_message)).setCancelable(false).setPositiveButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });
        }
    }
}
