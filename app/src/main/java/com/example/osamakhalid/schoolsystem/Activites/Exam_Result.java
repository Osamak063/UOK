package com.example.osamakhalid.schoolsystem.Activites;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.example.osamakhalid.schoolsystem.Adapters.exam_adapter;
import com.example.osamakhalid.schoolsystem.Model.ExamResult_Data;
import com.example.osamakhalid.schoolsystem.R;

import java.util.List;

public class Exam_Result extends AppCompatActivity {

    public RecyclerView recyclerView;
    public RecyclerView.Adapter adapter;
    public static List<ExamResult_Data> listItems;
    String exam_name;
    TextView examview_name;
    private ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exam__result);
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



         //setting up recyclerview
        recyclerView =  findViewById(R.id.examResult);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new exam_adapter(listItems,getApplicationContext());
        recyclerView.setAdapter(adapter);
    }





}
