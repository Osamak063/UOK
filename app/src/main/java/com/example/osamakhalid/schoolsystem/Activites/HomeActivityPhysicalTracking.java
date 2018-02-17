package com.example.osamakhalid.schoolsystem.Activites;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import com.example.osamakhalid.schoolsystem.R;
public class HomeActivityPhysicalTracking extends AppCompatActivity {
    RadioButton mylocation, partner;
    Button done;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        mylocation = (RadioButton) findViewById(R.id.mylocation);
        partner = (RadioButton) findViewById(R.id.partnerlocation);
        done = (Button) findViewById(R.id.done);
        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mylocation.isChecked()) {
                    startActivity(new Intent(HomeActivityPhysicalTracking.this, MyLocationActivity.class));
                } else if (partner.isChecked()) {
                    startActivity(new Intent(HomeActivityPhysicalTracking.this, StudentLocationActivity.class));
                }
            }
        });
    }
}
