package com.example.osamakhalid.schoolsystem.Activites;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.example.osamakhalid.schoolsystem.Adapters.alert_adapter;
import com.example.osamakhalid.schoolsystem.Model.Alert_Model;
import com.example.osamakhalid.schoolsystem.R;

import java.util.List;

public class Circular extends AppCompatActivity {

    public RecyclerView recyclerView;
    public RecyclerView.Adapter adapter;
    public static List<Alert_Model> listItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_circular);

        listItems = DashboardActivity.alert_model;
        if(listItems == null){
            Toast.makeText(Circular.this,"empty",Toast.LENGTH_SHORT).show();
        }

        //setting up recyclerview
        recyclerView =  findViewById(R.id.circular);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new alert_adapter(listItems,getApplicationContext());
        recyclerView.setAdapter(adapter);

    }
}
