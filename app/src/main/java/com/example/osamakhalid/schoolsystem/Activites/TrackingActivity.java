package com.example.osamakhalid.schoolsystem.Activites;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.osamakhalid.schoolsystem.BaseConnection.RetrofitInitialize;
import com.example.osamakhalid.schoolsystem.ConnectionInterface.ClientAPIs;
import com.example.osamakhalid.schoolsystem.Consts.Values;
import com.example.osamakhalid.schoolsystem.GlobalCalls.CommonCalls;
import com.example.osamakhalid.schoolsystem.Model.ParentLoginResponse;
import com.example.osamakhalid.schoolsystem.Model.ParentModels.ParentStudentListResponse;
import com.example.osamakhalid.schoolsystem.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;


public class TrackingActivity extends AppCompatActivity {
    private Button educationalButton, physicalButton;
    private Retrofit retrofit;
    private ClientAPIs clientAPIs;
    ParentLoginResponse parentData;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tracking);
        educationalButton = (Button) findViewById(R.id.educational);
        physicalButton = (Button) findViewById(R.id.physical);
        if (CommonCalls.getUserType(TrackingActivity.this).equals("Parent")) {
            parentData = CommonCalls.getParentData(TrackingActivity.this);
            String base = parentData.getUsername() + ":" + parentData.getPassword();
            String authHeader = "Basic " + Base64.encodeToString(base.getBytes(), Base64.NO_WRAP);
            getParentChildren(authHeader);
        }
        educationalButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(TrackingActivity.this, DashboardActivity.class));
                finish();
            }
        });
        physicalButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(TrackingActivity.this, HomeActivityPhysicalTracking.class));
                finish();
            }
        });
    }

    public void getParentChildren(String authHeader) {
        retrofit = RetrofitInitialize.getApiClient();
        clientAPIs = retrofit.create(ClientAPIs.class);
        Call<ParentStudentListResponse> call = clientAPIs.getParentStudentList(parentData.getParentID(), authHeader);
        call.enqueue(new Callback<ParentStudentListResponse>() {
            @Override
            public void onResponse(Call<ParentStudentListResponse> call, Response<ParentStudentListResponse> response) {
                if (response.isSuccessful()) {

                    ParentStudentListResponse parentStudentListResponse = response.body();
                    if (parentStudentListResponse != null && parentStudentListResponse.getStudentData() != null) {
                        CommonCalls.saveChildrenOfParentList(TrackingActivity.this, parentStudentListResponse.getStudentData());
                    }

                } else {
                    Toast.makeText(TrackingActivity.this, Values.DATA_ERROR, Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<ParentStudentListResponse> call, Throwable t) {
                // progressDialouge.dismiss();
                Toast.makeText(TrackingActivity.this, Values.SERVER_ERROR, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
