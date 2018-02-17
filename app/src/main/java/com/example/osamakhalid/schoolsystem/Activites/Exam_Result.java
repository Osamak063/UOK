package com.example.osamakhalid.schoolsystem.Activites;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.osamakhalid.schoolsystem.Adapters.exam_adapter;
import com.example.osamakhalid.schoolsystem.Model.ExamResult_Model;
import com.example.osamakhalid.schoolsystem.R;

import java.util.ArrayList;
import java.util.List;

public class Exam_Result extends AppCompatActivity {

    public RecyclerView recyclerView;
    public RecyclerView.Adapter adapter;
    public List<ExamResult_Model> listItems;
    ExamResult_Model exam_result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exam__result);

        listItems = new ArrayList<>();


        //for Dummy Data
        for (int i=0; i<10 ; i++){

            exam_result = new ExamResult_Model();
            exam_result.setSubject("Computer - "+(i+1));
            exam_result.setMarks("Marks = "+(i+1));
            exam_result.setDate("Date = "+(i+1)+"/2/17");

            listItems.add(exam_result);

        }


        //setting up recyclerview
        recyclerView =  findViewById(R.id.examResult);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new exam_adapter(listItems,getApplicationContext());
        recyclerView.setAdapter(adapter);
    }
}
