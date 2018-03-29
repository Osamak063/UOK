package com.example.osamakhalid.schoolsystem.Activites;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.osamakhalid.schoolsystem.Adapters.ExamScheduleAdapter;
import com.example.osamakhalid.schoolsystem.BaseConnection.RetrofitInitialize;
import com.example.osamakhalid.schoolsystem.ConnectionInterface.ClientAPIs;
import com.example.osamakhalid.schoolsystem.Consts.Values;
import com.example.osamakhalid.schoolsystem.GlobalCalls.CommonCalls;
import com.example.osamakhalid.schoolsystem.Model.ExamScheduleResponse;
import com.example.osamakhalid.schoolsystem.Model.ExamScheduleResponseList;
import com.example.osamakhalid.schoolsystem.Model.LoginResponse;
import com.example.osamakhalid.schoolsystem.Model.ParentLoginResponse;
import com.example.osamakhalid.schoolsystem.Model.ParentModels.ParentStudentData;
import com.example.osamakhalid.schoolsystem.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ExamSchedule extends AppCompatActivity {
    public RecyclerView recyclerView;
    public RecyclerView.Adapter adapter;
    public List<ExamScheduleResponse> listItems;
    private Retrofit retrofit;
    private ClientAPIs clientAPIs;
    ProgressDialog progressDialog;
    private String username;
    private List<ParentStudentData> parentStudentDataList;
    private ArrayList<String> childrenUsernames;
    private Spinner exam_spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exam_schedule);
        recyclerView = findViewById(R.id.exam_schedule_recycler_view);
        exam_spinner = findViewById(R.id.examSchdule_spinner);
        listItems = new ArrayList<>();
        progressDialog = CommonCalls.createDialouge(this, "", Values.DIALOGUE_MSG);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        parentStudentDataList = CommonCalls.getChildrenOfParentList(ExamSchedule.this);
        childrenUsernames = new ArrayList<>();
        for (ParentStudentData data : parentStudentDataList) {
            childrenUsernames.add(data.getName());
        }
        if (CommonCalls.getUserType(this).equals(Values.TYPE_PARENT)) {

            exam_spinner.setVisibility(View.VISIBLE);
            ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter
                    (ExamSchedule.this, android.R.layout.simple_spinner_item, childrenUsernames);
            spinnerArrayAdapter.setDropDownViewResource(android.R.layout
                    .simple_spinner_dropdown_item);
            exam_spinner.setAdapter(spinnerArrayAdapter);
            exam_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    username = parentStudentDataList.get(i).getUsername();
                    if ((username!= null)){
                        getData();
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });

        }else if(CommonCalls.getUserType(ExamSchedule.this).equals(Values.TYPE_STUDENT)) {
            getData();
        }
        adapter = new ExamScheduleAdapter(listItems, getApplicationContext());
        recyclerView.setAdapter(adapter);
    }

    public void getData() {
        retrofit = RetrofitInitialize.getApiClient();
        clientAPIs = retrofit.create(ClientAPIs.class);
        String base = null;
        if (CommonCalls.getUserType(ExamSchedule.this).equals(Values.TYPE_PARENT)) {
            ParentLoginResponse loginResponse = CommonCalls.getParentData(ExamSchedule.this);
            base = loginResponse.getUsername() + ":" + loginResponse.getPassword();
        } else if (CommonCalls.getUserType(ExamSchedule.this).equals(Values.TYPE_STUDENT)) {
            LoginResponse loginResponse = CommonCalls.getUserData(ExamSchedule.this);
            base = loginResponse.getUsername() + ":" + loginResponse.getPassword();
            username = loginResponse.getUsername();

        }
        String authHeader = "Basic " + Base64.encodeToString(base.getBytes(), Base64.NO_WRAP);
        Call<ExamScheduleResponseList> call = clientAPIs.getExamSchedule(username, authHeader);
        call.enqueue(new Callback<ExamScheduleResponseList>() {
            @Override
            public void onResponse(Call<ExamScheduleResponseList> call, Response<ExamScheduleResponseList> response) {
                if (response.isSuccessful()) {
                    ExamScheduleResponseList examScheduleList = response.body();
                    if (examScheduleList != null && examScheduleList.getExamsScheduleData() != null) {
                        progressDialog.dismiss();
                        listItems.addAll(examScheduleList.getExamsScheduleData());
                        adapter.notifyDataSetChanged();
                    } else {
                        progressDialog.dismiss();
                        Toast.makeText(ExamSchedule.this, Values.DATA_ERROR, Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<ExamScheduleResponseList> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(ExamSchedule.this, Values.SERVER_ERROR, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
