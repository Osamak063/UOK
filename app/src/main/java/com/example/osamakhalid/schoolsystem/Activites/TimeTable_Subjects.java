package com.example.osamakhalid.schoolsystem.Activites;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.example.osamakhalid.schoolsystem.Adapters.TimeTable_Subject_Adapter;
import com.example.osamakhalid.schoolsystem.Consts.Values;
import com.example.osamakhalid.schoolsystem.Model.TimeTable_Data_Model;
import com.example.osamakhalid.schoolsystem.R;

import java.util.List;

public class TimeTable_Subjects extends AppCompatActivity {

    List<TimeTable_Data_Model> timeTable_data_models;
    RecyclerView recyclerview;
    RecyclerView.Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_table__subjects);

        timeTable_data_models = this.getIntent().getParcelableArrayListExtra("timetable_data");
        for(TimeTable_Data_Model time: timeTable_data_models){
            System.out.println("Onclick "+time.getRoom());
        }
        if (timeTable_data_models != null) {


            recyclerview = findViewById(R.id.timetable_subjects_recclerview);
            recyclerview.setHasFixedSize(true);
            recyclerview.setLayoutManager(new LinearLayoutManager(TimeTable_Subjects.this));
            adapter = new TimeTable_Subject_Adapter(timeTable_data_models,TimeTable_Subjects.this);
            adapter.notifyDataSetChanged();
            recyclerview.setAdapter(adapter);


        }else{

            Toast.makeText(this, Values.DATA_ERROR,Toast.LENGTH_SHORT).show();

        }


    }
}
