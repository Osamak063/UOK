package com.example.osamakhalid.schoolsystem.Activites;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.osamakhalid.schoolsystem.Adapters.Attendance_Adapter;
import com.example.osamakhalid.schoolsystem.Adapters.Transport_Adapter;
import com.example.osamakhalid.schoolsystem.Model.Attendance_Model;
import com.example.osamakhalid.schoolsystem.Model.Transport_Model;
import com.example.osamakhalid.schoolsystem.R;

import java.util.ArrayList;
import java.util.List;

public class Attendance extends AppCompatActivity {
    public RecyclerView recyclerView;
    public RecyclerView.Adapter adapter;
    public List<Attendance_Model> listItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance);
        listItems = new ArrayList<>();
        // Dummy Data
        listItems.add(new Attendance_Model("Fri,Sep 01 2017", "P"));
        listItems.add(new Attendance_Model("Sat,Sep 02 2017", "A"));
        listItems.add(new Attendance_Model("Sun,Sep 03 2017", "H"));
        listItems.add(new Attendance_Model("Mon,Sep 04 2017", "P"));
        listItems.add(new Attendance_Model("Tue,Sep 05 2017", "P"));
        listItems.add(new Attendance_Model("Wed,Sep 06 2017", "A"));
        listItems.add(new Attendance_Model("Thurs,Sep 07 2017", "A"));
        listItems.add(new Attendance_Model("Fri,Sep 08 2017", "H"));
        listItems.add(new Attendance_Model("Mon,Sep 09 2017", "P"));
        listItems.add(new Attendance_Model("Tue,Sep 10 2017", "A"));
        recyclerView = findViewById(R.id.attendance_recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new Attendance_Adapter(listItems, getApplicationContext());
        recyclerView.setAdapter(adapter);
    }
}
