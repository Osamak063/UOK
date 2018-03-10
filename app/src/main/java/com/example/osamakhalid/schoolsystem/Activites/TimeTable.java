package com.example.osamakhalid.schoolsystem.Activites;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.view.View;
import android.widget.Toast;

import com.example.osamakhalid.schoolsystem.Adapters.timetable_adapter;
import com.example.osamakhalid.schoolsystem.BaseConnection.RetrofitInitialize;
import com.example.osamakhalid.schoolsystem.ConnectionInterface.ClientAPIs;
import com.example.osamakhalid.schoolsystem.Consts.Values;
import com.example.osamakhalid.schoolsystem.GlobalCalls.CommonCalls;
import com.example.osamakhalid.schoolsystem.Interfaces.ItemClickListenerDay;
import com.example.osamakhalid.schoolsystem.Model.LoginResponse;
import com.example.osamakhalid.schoolsystem.Model.TimeTable_Data_Model;
import com.example.osamakhalid.schoolsystem.Model.TimeTable_Model;
import com.example.osamakhalid.schoolsystem.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class TimeTable extends AppCompatActivity implements ItemClickListenerDay {

    RecyclerView recyclerView;
    timetable_adapter adapter;
    private ProgressDialog progressDilouge;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_table);
        progressDilouge = CommonCalls.createDialouge(this,"", Values.DIALOGUE_MSG);
        getTimeTableData();



    }

    public void getTimeTableData(){

        Retrofit retrofit = RetrofitInitialize.getApiClient();
        ClientAPIs clientAPIs = retrofit.create(ClientAPIs.class);
        final LoginResponse loginResponse = CommonCalls.getUserData(TimeTable.this);
        String base = loginResponse.getUsername() + ":" + loginResponse.getPassword();
        String authHeader = "Basic " + Base64.encodeToString(base.getBytes(), Base64.NO_WRAP);
        Call<TimeTable_Model> call = clientAPIs.getTimeTable(loginResponse.getUsername(), Values.LANGUAGE,authHeader);
        call.enqueue(new Callback<TimeTable_Model>() {
            @Override
            public void onResponse(Call<TimeTable_Model> call, Response<TimeTable_Model> response) {

                if(response.isSuccessful()){

                    TimeTable_Model timeTable_model = response.body();
                    if(timeTable_model != null ){
                        progressDilouge.dismiss();
                        recyclerView = findViewById(R.id.timetable_date_recyclerview);
                        recyclerView.setHasFixedSize(true);
                        recyclerView.setLayoutManager(new LinearLayoutManager(TimeTable.this));
                        adapter = new timetable_adapter(timeTable_model.getRoutineData(),getApplicationContext());
                        adapter.setItemClickListener(TimeTable.this);
                        recyclerView.setAdapter(adapter);
                        adapter.notifyDataSetChanged();

                    }else{

                        progressDilouge.dismiss();
                        Toast.makeText(getApplicationContext(),Values.DATA_ERROR,Toast.LENGTH_SHORT).show();

                    }

                }

            }

            @Override
            public void onFailure(Call<TimeTable_Model> call, Throwable t) {
                progressDilouge.dismiss();
                Toast.makeText(getApplicationContext(),Values.SERVER_ERROR,Toast.LENGTH_SHORT).show();
            }
        });


    }


    @Override
    public void onClick(View view, List<TimeTable_Data_Model> TimeTable) {
        Intent mIntent = new Intent(TimeTable.this, TimeTable_Subjects.class);
        mIntent.putParcelableArrayListExtra("timetable_data", (ArrayList<? extends Parcelable>) TimeTable);
        startActivity(mIntent);

    }
}
