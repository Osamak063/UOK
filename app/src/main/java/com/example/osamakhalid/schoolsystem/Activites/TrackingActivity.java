package com.example.osamakhalid.schoolsystem.Activites;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.osamakhalid.schoolsystem.R;

public class TrackingActivity extends AppCompatActivity {
    private Button educationalButton,physicalButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tracking);
        educationalButton=(Button) findViewById(R.id.educational);
        physicalButton=(Button) findViewById(R.id.physical);
        educationalButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(TrackingActivity.this,DashboardActivity.class));
            }
        });
        physicalButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(TrackingActivity.this,HomeActivityPhysicalTracking.class));
            }
        });
    }
}
