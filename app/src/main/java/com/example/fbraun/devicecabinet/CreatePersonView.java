package com.example.fbraun.devicecabinet;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

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
        //toDo store Person
    }
}
