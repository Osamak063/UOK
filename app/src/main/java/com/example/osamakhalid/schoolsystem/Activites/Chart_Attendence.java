package com.example.osamakhalid.schoolsystem.Activites;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.osamakhalid.schoolsystem.Adapters.Chart_Attendence_Adapter;
import com.example.osamakhalid.schoolsystem.Model.Alert_Model;
import com.example.osamakhalid.schoolsystem.Model.ItemClickListener;
import com.example.osamakhalid.schoolsystem.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by HAMI on 15/02/2018.
 */

public class Chart_Attendence extends AppCompatActivity implements ItemClickListener{

    public RecyclerView recyclerView;
    public Chart_Attendence_Adapter adapter;
    public List<Alert_Model> listItems;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chart__attendence);


        listItems = new ArrayList<>();
        recyclerView = findViewById(R.id.monthRecyclerView);

        Alert_Model alert = new Alert_Model();
        alert.setNotification("January");
        listItems.add(alert);
        Alert_Model alert0 = new Alert_Model();
        alert0.setNotification("Febuary");
        listItems.add(alert0);
        Alert_Model alert1 = new Alert_Model();
        alert1.setNotification("March");
        listItems.add(alert1);
        Alert_Model alert2 = new Alert_Model();
        alert2.setNotification("April");
        listItems.add(alert2);
        Alert_Model alert3 = new Alert_Model();
        alert3.setNotification("May");
        listItems.add(alert3);
        Alert_Model alert4 = new Alert_Model();
        alert4.setNotification("June");
        listItems.add(alert4);
        Alert_Model alert5 = new Alert_Model();
        alert5.setNotification("July");
        listItems.add(alert5);
        Alert_Model alert6 = new Alert_Model();
        alert6.setNotification("August");
        listItems.add(alert6);
        Alert_Model alert7 = new Alert_Model();
        alert7.setNotification("September");
        listItems.add(alert7);
        Alert_Model alert8 = new Alert_Model();
        alert8.setNotification("October");
        listItems.add(alert8);
        Alert_Model alert9 = new Alert_Model();
        alert9.setNotification("November");
        listItems.add(alert9);
        Alert_Model alert10 = new Alert_Model();
        alert10.setNotification("December");
        listItems.add(alert10);


        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new Chart_Attendence_Adapter(listItems, getApplicationContext());
        adapter.setItemClickListener(this);
        recyclerView.setAdapter(adapter);



    }


    @Override
    public void onClick(View view, String name) {
        Intent i = new Intent(this, Attendance.class);
        i.putExtra("", name);
        startActivity(i);
    }
}
