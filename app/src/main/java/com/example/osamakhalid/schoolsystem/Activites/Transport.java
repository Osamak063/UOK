package com.example.osamakhalid.schoolsystem.Activites;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.osamakhalid.schoolsystem.Adapters.Transport_Adapter;
import com.example.osamakhalid.schoolsystem.Adapters.alert_adapter;
import com.example.osamakhalid.schoolsystem.Model.Alert_Model;
import com.example.osamakhalid.schoolsystem.Model.Transport_Model;
import com.example.osamakhalid.schoolsystem.R;

import java.util.ArrayList;
import java.util.List;

public class Transport extends AppCompatActivity {
    public RecyclerView recyclerView;
    public RecyclerView.Adapter adapter;
    public List<Transport_Model> listItems;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transport);
        listItems = new ArrayList<>();
        // Dummy Data
        listItems.add(new Transport_Model("Van#1","Gulshan to Malir"));
        listItems.add(new Transport_Model("Van#2","Gulshan to North Karachi"));
        listItems.add(new Transport_Model("Van#3","Gulshan to Saddar"));
        listItems.add(new Transport_Model("Van#4","Gulshan to North Nazimabad"));
        listItems.add(new Transport_Model("Van#5","Gulshan to Gulistan-e-Johar"));
        listItems.add(new Transport_Model("Van#6","Gulshan to Kaniz Fatima"));
        listItems.add(new Transport_Model("Van#7","Gulshan to Hassan Square"));
        listItems.add(new Transport_Model("Van#8","Gulshan to Steel Town"));
        listItems.add(new Transport_Model("Van#9","Gulshan to Model Colony"));
        listItems.add(new Transport_Model("Van#10","Gulshan to Shah Faisal Town"));

        recyclerView =  findViewById(R.id.transport_recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new Transport_Adapter(listItems,getApplicationContext());
        recyclerView.setAdapter(adapter);
    }
}
