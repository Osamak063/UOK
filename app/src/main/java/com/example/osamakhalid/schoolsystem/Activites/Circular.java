package com.example.osamakhalid.schoolsystem.Activites;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.view.View;
import android.widget.Toast;

import com.example.osamakhalid.schoolsystem.Adapters.alert_adapter;
import com.example.osamakhalid.schoolsystem.BaseConnection.RetrofitInitialize;
import com.example.osamakhalid.schoolsystem.ConnectionInterface.ClientAPIs;
import com.example.osamakhalid.schoolsystem.Consts.Values;
import com.example.osamakhalid.schoolsystem.GlobalCalls.CommonCalls;
import com.example.osamakhalid.schoolsystem.Model.AlertResponse_Model;
import com.example.osamakhalid.schoolsystem.Model.Alert_Model;
import com.example.osamakhalid.schoolsystem.Model.LoginResponse;
import com.example.osamakhalid.schoolsystem.Model.ParentLoginResponse;
import com.example.osamakhalid.schoolsystem.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class Circular extends AppCompatActivity {

    public RecyclerView recyclerView;
    public RecyclerView.Adapter adapter;
    public ProgressDialog progress_dialouge;
    List<Alert_Model> list;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_circular);
        //Setting up Toolbar
        toolbar = findViewById(R.id.toolBar);
        toolbar.setTitle("Notice Board");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Circular.this, DashboardActivity.class));
            }
        });
        list = new ArrayList<>();
        recyclerView =  findViewById(R.id.circular);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(Circular.this));
        progress_dialouge = CommonCalls.createDialouge(Circular.this,"",Values.DIALOGUE_MSG);

        getNoticeData();

    }

    //Notice Board function
    public void getNoticeData() {

        Retrofit retrofit = RetrofitInitialize.getApiClient();
        ClientAPIs clientAPIs = retrofit.create(ClientAPIs.class);
        String base = null;
        if (CommonCalls.getUserType(Circular.this).equals(Values.TYPE_PARENT)) {
            ParentLoginResponse loginResponse = CommonCalls.getParentData(Circular.this);
            base = loginResponse.getUsername() + ":" + loginResponse.getPassword();
        } else if (CommonCalls.getUserType(Circular.this).equals(Values.TYPE_STUDENT)) {
            LoginResponse loginResponse = CommonCalls.getUserData(Circular.this);
            base = loginResponse.getUsername() + ":" + loginResponse.getPassword();

        }
        String authHeader = "Basic " + Base64.encodeToString(base.getBytes(), Base64.NO_WRAP);
        Call<AlertResponse_Model> call = clientAPIs.getNoticeData(authHeader);
        call.enqueue(new Callback<AlertResponse_Model>() {
            @Override
            public void onResponse(Call<AlertResponse_Model> call, Response<AlertResponse_Model> response) {
                if (response.isSuccessful()) {

                    AlertResponse_Model  alert_response_model = response.body();
                    if (alert_response_model != null) {
                        if(alert_response_model.getNoticeData() != null){

                            progress_dialouge.dismiss();
                            list.addAll(alert_response_model.getNoticeData());
                            if(list != null){
                                adapter = new alert_adapter(list,Circular.this);
                                recyclerView.setAdapter(adapter);
                                adapter.notifyDataSetChanged();
                            }

                        }else {
                            progress_dialouge.dismiss();
                            adapter = new alert_adapter(list,Circular.this);
                            recyclerView.setAdapter(adapter);
                            adapter.notifyDataSetChanged();
                            Toast.makeText(Circular.this, Values.DATA_ERROR, Toast.LENGTH_SHORT).show();

                        }
                     } else{
                        progress_dialouge.dismiss();
                        adapter = new alert_adapter(list,Circular.this);
                        recyclerView.setAdapter(adapter);
                        adapter.notifyDataSetChanged();
                        Toast.makeText(Circular.this, Values.DATA_ERROR, Toast.LENGTH_SHORT).show();

                    }


                }

            }

            @Override
            public void onFailure(Call<AlertResponse_Model> call, Throwable t) {
                progress_dialouge.dismiss();
                Toast.makeText(Circular.this, Values.SERVER_ERROR, Toast.LENGTH_SHORT).show();
            }
        });

    }
}
