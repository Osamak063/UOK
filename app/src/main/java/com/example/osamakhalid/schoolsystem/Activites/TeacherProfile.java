package com.example.osamakhalid.schoolsystem.Activites;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.osamakhalid.schoolsystem.R;

public class TeacherProfile extends AppCompatActivity {
    TextView teacherName;
    String name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_profile);
        Intent i= getIntent();
        teacherName=(TextView) findViewById(R.id.profile_teacher_name);
        name = i.getStringExtra("name");
        teacherName.setText(name);
    }
}
