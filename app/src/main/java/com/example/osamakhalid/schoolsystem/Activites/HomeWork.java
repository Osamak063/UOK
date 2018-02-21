package com.example.osamakhalid.schoolsystem.Activites;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.TextView;

import com.example.osamakhalid.schoolsystem.Adapters.HomeWork_Adapter;
import com.example.osamakhalid.schoolsystem.Model.Homework_Data_Model;
import com.example.osamakhalid.schoolsystem.R;

import java.util.List;

public class HomeWork extends AppCompatActivity {

    public RecyclerView recyclerView;
    public RecyclerView.Adapter adapter;
    public List<Homework_Data_Model> listItems;
   TextView date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_work);
        date = findViewById(R.id.date_section);
        listItems = DashboardActivity.homework_data_models;
        date.setText(DashboardActivity.Currentdate);
        if(listItems == null){
            Log.e("HomeWork_DATA: ","Empty");
        }


        recyclerView =  findViewById(R.id.homeworkview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new HomeWork_Adapter(listItems,getApplicationContext());
        recyclerView.setAdapter(adapter);

    }
}
