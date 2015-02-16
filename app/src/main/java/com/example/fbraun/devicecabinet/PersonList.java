package com.example.fbraun.devicecabinet;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.fbraun.devicecabinet.com.example.fbraun.devicecabinet.model.Person;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by fbraun on 16.02.15.
 */
public class PersonList extends ListActivity {
    private ArrayAdapter<Person> listAdapter;
    private List<Person> dataList;
    private static Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        dataList = returnData();
        context = getApplicationContext();

        PersonListAdapter personListAdapter = new PersonListAdapter(dataList, context);
        setListAdapter(personListAdapter);
    }

    public List<Person> returnData() {
        Person person1 = new Person();
        person1.firstName = "Fee";
        person1.lastName = "Braun";
        person1.personId = "1";

        Person person2 = new Person();
        person2.firstName = "Max";
        person2.lastName = "Mustermann";
        person2.personId = "2";

        List<Person> personList = new ArrayList<Person>();

        personList.add(person1);
        personList.add(person2);

        return personList;
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        Intent intent = new Intent(this, OverViewList.class);
        startActivity(intent);
    }
}
