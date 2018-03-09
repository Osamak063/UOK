package com.example.osamakhalid.schoolsystem.Activites;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.example.osamakhalid.schoolsystem.Adapters.SyllabusSubject_Adapter;
import com.example.osamakhalid.schoolsystem.Consts.Values;
import com.example.osamakhalid.schoolsystem.Model.SyllabusMultiple_Subject_Model;
import com.example.osamakhalid.schoolsystem.R;

import java.util.List;

public class SyllabusSubject_Activity extends AppCompatActivity {

    List<SyllabusMultiple_Subject_Model> syllabusMultiple_subject_models;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_syllabus_subject_);

        syllabusMultiple_subject_models = this.getIntent().getParcelableArrayListExtra("syllabus_data");
        if(syllabusMultiple_subject_models != null){
            recyclerView = findViewById(R.id.subject_recyclerVIEWS);
            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(new LinearLayoutManager(SyllabusSubject_Activity.this));
            adapter = new SyllabusSubject_Adapter(syllabusMultiple_subject_models,SyllabusSubject_Activity.this);
            recyclerView.setAdapter(adapter);

        }else{
            Toast.makeText(SyllabusSubject_Activity.this, Values.DATA_ERROR, Toast.LENGTH_SHORT).show();

        }


    }
}
