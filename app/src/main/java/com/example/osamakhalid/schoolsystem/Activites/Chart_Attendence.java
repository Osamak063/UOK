package com.example.osamakhalid.schoolsystem.Activites;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.osamakhalid.schoolsystem.Adapters.MonthsAdapter;
import com.example.osamakhalid.schoolsystem.Model.ItemClickListener;
import com.example.osamakhalid.schoolsystem.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by HAMI on 15/02/2018.
 */

public class Chart_Attendence extends AppCompatActivity implements ItemClickListener {

    public RecyclerView recyclerView;
    public MonthsAdapter adapter;
    public List<String> listItems;
    Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chart__attendence);
        //Setting up Toolbar
        toolbar = findViewById(R.id.toolBar);
        toolbar.setTitle("Attendence");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Chart_Attendence.this, DashboardActivity.class));
            }
        });
        listItems = new ArrayList<>();
        listItems.addAll(Arrays.asList(getResources().getStringArray(R.array.months)));
        recyclerView = findViewById(R.id.monthRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new MonthsAdapter(listItems,getApplicationContext());
        adapter.setItemClickListener(Chart_Attendence.this);
        recyclerView.setAdapter(adapter);
    }


    @Override
    public void onClick(View view, String name) {
        Intent i = new Intent(Chart_Attendence.this, Attendance.class);
        i.putExtra("name", name);
        startActivity(i);
    }
}
