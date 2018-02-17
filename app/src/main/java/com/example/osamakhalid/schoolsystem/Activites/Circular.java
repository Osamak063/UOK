package com.example.osamakhalid.schoolsystem.Activites;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.osamakhalid.schoolsystem.Adapters.alert_adapter;
import com.example.osamakhalid.schoolsystem.Model.Alert_Model;
import com.example.osamakhalid.schoolsystem.R;

import java.util.ArrayList;
import java.util.List;

public class Circular extends AppCompatActivity {

    public RecyclerView recyclerView;
    public RecyclerView.Adapter adapter;
    public List<Alert_Model> listItems;
    Alert_Model alert;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_circular);

        listItems = new ArrayList<>();

        //for Dummy Data
        for (int i=0; i<20 ; i++){

            alert = new Alert_Model();
            alert.setNotification("Start New Session on 01 January, 2018");
            listItems.add(alert);

        }

        //setting up recyclerview
        recyclerView =  findViewById(R.id.circular);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new alert_adapter(listItems,getApplicationContext());
        recyclerView.setAdapter(adapter);

    }
}
