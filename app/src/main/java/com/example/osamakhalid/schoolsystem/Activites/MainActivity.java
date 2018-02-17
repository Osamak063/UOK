package com.example.osamakhalid.schoolsystem.Activites;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.osamakhalid.schoolsystem.R;

public class MainActivity extends AppCompatActivity {
    private Button loginButton;
    private EditText userName, password;
    private String username="user1",pass="userpass";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loginButton = (Button) findViewById(R.id.login_button);
        userName=(EditText) findViewById(R.id.login_email);
        password=(EditText) findViewById(R.id.login_password);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (userName.getText().toString().equals(username) && password.getText().toString().equals(pass)){
                    Intent i = new Intent(MainActivity.this, TrackingActivity.class);
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(i);
                    finish();
                }
                else{
                    Toast.makeText(MainActivity.this,"Wrong Username or Password",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
