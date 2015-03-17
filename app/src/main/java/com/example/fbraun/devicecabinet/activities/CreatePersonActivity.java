package com.example.fbraun.devicecabinet.activities;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.fbraun.devicecabinet.R;
import com.example.fbraun.devicecabinet.RESTApiClient;
import com.example.fbraun.devicecabinet.model.Person;

/**
 * Created by fbraun on 16.02.15.
 */
public class CreatePersonActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_person);
    }

    public void storePerson(View view) {
        Person person = new Person();
        TextView firstName = (TextView) findViewById(R.id.first_name_text);
        person.firstName = firstName.getText().toString();

        TextView lastName = (TextView) findViewById(R.id.last_name_text);
        person.lastName = lastName.getText().toString();

        if (firstName != null && lastName != null) {
            RESTApiClient client = new RESTApiClient();
            client.storePerson(person, new RESTApiClient.VolleyCallbackStore() {
                @Override
                public void onSaveSuccess() {
                    finish();
                }
            });
        } else {
            //toDo Alert
        }
    }
}
