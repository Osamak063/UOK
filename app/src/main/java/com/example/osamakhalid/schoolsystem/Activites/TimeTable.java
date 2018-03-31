package com.example.osamakhalid.schoolsystem.Activites;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;
import android.support.v7.widget.Toolbar;

import com.example.osamakhalid.schoolsystem.Adapters.timetable_adapter;
import com.example.osamakhalid.schoolsystem.BaseConnection.RetrofitInitialize;
import com.example.osamakhalid.schoolsystem.ConnectionInterface.ClientAPIs;
import com.example.osamakhalid.schoolsystem.Consts.Values;
import com.example.osamakhalid.schoolsystem.GlobalCalls.CommonCalls;
import com.example.osamakhalid.schoolsystem.Interfaces.ItemClickListenerDay;
import com.example.osamakhalid.schoolsystem.Model.LoginResponse;
import com.example.osamakhalid.schoolsystem.Model.ParentLoginResponse;
import com.example.osamakhalid.schoolsystem.Model.ParentModels.ParentStudentData;
import com.example.osamakhalid.schoolsystem.Model.TimeTable_Data_Model;
import com.example.osamakhalid.schoolsystem.Model.TimeTable_Model;
import com.example.osamakhalid.schoolsystem.Model.TimeTable_Response_Model;
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
    String base,username;
    List<ParentStudentData> parentStudentDataList;
    List<String>childrenUsernames;
    List<TimeTable_Response_Model> list;
    private Spinner TimeTable_spinner;
    Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_table);
        //Setting up Toolbar
        toolbar = findViewById(R.id.toolBar);
        toolbar.setTitle("Time Table");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(TimeTable.this, DashboardActivity.class));
            }
        });
        list = new ArrayList<>();
        progressDilouge = CommonCalls.createDialouge(this,"", Values.DIALOGUE_MSG);
        TimeTable_spinner = findViewById(R.id.timetable_spinner);
        recyclerView = findViewById(R.id.timetable_date_recyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(TimeTable.this));

        if (CommonCalls.getUserType(this).equals(Values.TYPE_PARENT)) {

            parentStudentDataList = CommonCalls.getChildrenOfParentList(TimeTable.this);
            childrenUsernames= new ArrayList<>();
            for (ParentStudentData data : parentStudentDataList) {
                childrenUsernames.add(data.getName());
            }
            TimeTable_spinner.setVisibility(View.VISIBLE);
            ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter
                    (TimeTable.this, android.R.layout.simple_spinner_item, childrenUsernames);
            spinnerArrayAdapter.setDropDownViewResource(android.R.layout
                    .simple_spinner_dropdown_item);
            TimeTable_spinner.setAdapter(spinnerArrayAdapter);
            TimeTable_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    username = parentStudentDataList.get(i).getUsername();
                    Log.e("Username",username);
                    if(username!=null){
                        list.clear();
                        getTimeTableData();
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });

        }else if (CommonCalls.getUserType(this).equals(Values.TYPE_STUDENT)) {
            TimeTable_spinner.setVisibility(View.GONE);
            list.clear();
            getTimeTableData();
        }





        }

    public void getTimeTableData(){

        Retrofit retrofit = RetrofitInitialize.getApiClient();
        ClientAPIs clientAPIs = retrofit.create(ClientAPIs.class);
        if (CommonCalls.getUserType(TimeTable.this).equals(Values.TYPE_PARENT)) {
            ParentLoginResponse loginResponse = CommonCalls.getParentData(TimeTable.this);
            base = loginResponse.getUsername() + ":" + loginResponse.getPassword();
        } else if (CommonCalls.getUserType(TimeTable.this).equals(Values.TYPE_STUDENT)) {
            LoginResponse loginResponse = CommonCalls.getUserData(TimeTable.this);
            base = loginResponse.getUsername() + ":" + loginResponse.getPassword();
            username = loginResponse.getUsername();
        }
        String authHeader = "Basic " + Base64.encodeToString(base.getBytes(), Base64.NO_WRAP);
        Call<TimeTable_Model> call = clientAPIs.getTimeTable(username, Values.LANGUAGE,authHeader);
        call.enqueue(new Callback<TimeTable_Model>() {
            @Override
            public void onResponse(Call<TimeTable_Model> call, Response<TimeTable_Model> response) {

                if(response.isSuccessful()){

                    TimeTable_Model timeTable_model = response.body();
                    if(timeTable_model != null ){
                        if(timeTable_model.getRoutineData()!=null){
                            progressDilouge.dismiss();
                            list.addAll(timeTable_model.getRoutineData());
                            adapter = new timetable_adapter(list,getApplicationContext());
                            adapter.setItemClickListener(TimeTable.this);
                            recyclerView.setAdapter(adapter);
                            adapter.notifyDataSetChanged();
                        }else{
                            progressDilouge.dismiss();
                            adapter = new timetable_adapter(list,getApplicationContext());
                            adapter.setItemClickListener(TimeTable.this);
                            recyclerView.setAdapter(adapter);
                            adapter.notifyDataSetChanged();
                            Toast.makeText(getApplicationContext(),Values.DATA_ERROR,Toast.LENGTH_SHORT).show();

                        }


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
