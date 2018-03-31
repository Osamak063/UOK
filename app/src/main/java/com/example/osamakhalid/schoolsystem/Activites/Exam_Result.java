package com.example.osamakhalid.schoolsystem.Activites;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import android.support.v7.widget.Toolbar;

import com.example.osamakhalid.schoolsystem.Adapters.exam_adapter;
import com.example.osamakhalid.schoolsystem.Consts.Values;
import com.example.osamakhalid.schoolsystem.Model.ExamResult_Data;
import com.example.osamakhalid.schoolsystem.R;

import java.util.List;

public class Exam_Result extends AppCompatActivity {

    public RecyclerView recyclerView;
    public RecyclerView.Adapter adapter;
    public static List<ExamResult_Data> listItems;
    String exam_name;
    TextView examview_name;
    Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Setting up Toolbar
        setContentView(R.layout.activity_exam__result);
        toolbar = findViewById(R.id.toolBar);
        toolbar.setTitle("Results");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Exam_Result.this, ExamResult_Category.class));
            }
        });

        examview_name = findViewById(R.id.examtext);
        exam_name = ExamResult_Category.exam_name;

        if(exam_name.equals("Final Exams")){
            listItems = ExamResult_Category.final_exam;
            examview_name.setText(exam_name);

        }else if(exam_name.contains("First term exam")){
            listItems = ExamResult_Category.first_exam;
            examview_name.setText(exam_name);

        }else if(exam_name.contains("Second term exam")){
            listItems = ExamResult_Category.second_exam;
            examview_name.setText(exam_name);
        }


        if(listItems != null){
            //setting up recyclerview
            recyclerView =  findViewById(R.id.examResult);
            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));

            adapter = new exam_adapter(listItems,getApplicationContext());
            adapter.notifyDataSetChanged();
            recyclerView.setAdapter(adapter);
        }else{
            Toast.makeText(Exam_Result.this, Values.DATA_ERROR, Toast.LENGTH_SHORT).show();

        }


    }





}
