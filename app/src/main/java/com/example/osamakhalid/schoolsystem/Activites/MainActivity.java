package com.example.osamakhalid.schoolsystem.Activites;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.osamakhalid.schoolsystem.BaseConnection.RetrofitInitialize;
import com.example.osamakhalid.schoolsystem.ConnectionInterface.ClientAPIs;
import com.example.osamakhalid.schoolsystem.Consts.Values;
import com.example.osamakhalid.schoolsystem.Model.LoginResponse;
import com.example.osamakhalid.schoolsystem.R;

import GlobalCalls.CommonCalls;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity {
    private Button loginButton;
    private EditText userName, password;
    private String username = "user1", pass = "userpass";
    private ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (checkUserAlreadyLoggedIn()) {
            Intent i = new Intent(MainActivity.this, TrackingActivity.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(i);
            finish();
        }
        loginButton = (Button) findViewById(R.id.login_button);
        userName = (EditText) findViewById(R.id.login_email);
        password = (EditText) findViewById(R.id.login_password);
        progressDialog = new ProgressDialog(this);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressDialog.setMessage("Please wait...");
                progressDialog.show();
                loginUser();
            }
        });
    }

    public void loginUser() {
        Retrofit retrofit = RetrofitInitialize.getApiClient();
        ClientAPIs clientAPIs = retrofit.create(ClientAPIs.class);
        String base = Values.USER_CURL + ":" + Values.PASSWORD_CURL;
        String authHeader = "Basic " + Base64.encodeToString(base.getBytes(), Base64.NO_WRAP);
        Call<LoginResponse> call = clientAPIs.loginUser(userName.getText().toString(), password.getText().toString(), authHeader);
        System.out.println("logg coming");
        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                LoginResponse loginResponse1 = response.body();
                if (response.isSuccessful()) {
                    LoginResponse loginResponse = response.body();
                    if (loginResponse.getStatus() == 1) {
                        progressDialog.dismiss();
                        Toast.makeText(MainActivity.this, "Successfully logged in.", Toast.LENGTH_SHORT).show();
                        CommonCalls.saveUserData(loginResponse,MainActivity.this);
                        Intent i = new Intent(MainActivity.this, TrackingActivity.class);
                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(i);
                        finish();
                    }
                } else {
                    progressDialog.dismiss();
                    Toast.makeText(MainActivity.this, "Wrong username or password.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(MainActivity.this,"Sorry something went wrong.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public boolean checkUserAlreadyLoggedIn() {
        if (CommonCalls.getUserData(MainActivity.this) != null) {
            return true;
        } else {
            return false;
        }
    }
}
