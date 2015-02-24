package com.example.fbraun.devicecabinet;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.fbraun.devicecabinet.com.example.fbraun.devicecabinet.model.Person;

/**
 * Created by fbraun on 16.02.15.
 */
public class CreatePersonView extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_person);
    }

    public void storePerson(View view) {
        Person person = new Person();
        TextView firstName = (TextView) findViewById(R.id.firstNameText);
        person.firstName = firstName.getText().toString();

        TextView lastName = (TextView) findViewById(R.id.lastNameText);
        person.lastName = lastName.getText().toString();

        if (firstName != null && lastName != null) {
            RESTApiClient client = new RESTApiClient();
            client.storePerson(person, new RESTApiClient.VolleyCallbackStore() {
                @Override
                public void onStoreSuccess() {
                    finish();
                }
            });
        } else {
            //toDo Alert
        }
    }
}
