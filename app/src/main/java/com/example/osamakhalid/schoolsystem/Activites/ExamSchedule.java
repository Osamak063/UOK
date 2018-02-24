package com.example.osamakhalid.schoolsystem.Activites;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.widget.Toast;

import com.example.osamakhalid.schoolsystem.Adapters.ExamScheduleAdapter;
import com.example.osamakhalid.schoolsystem.Adapters.NewsAndEvents_Adapter;
import com.example.osamakhalid.schoolsystem.BaseConnection.RetrofitInitialize;
import com.example.osamakhalid.schoolsystem.ConnectionInterface.ClientAPIs;
import com.example.osamakhalid.schoolsystem.GlobalCalls.CommonCalls;
import com.example.osamakhalid.schoolsystem.Model.ExamScheduleResponse;
import com.example.osamakhalid.schoolsystem.Model.ExamScheduleResponseList;
import com.example.osamakhalid.schoolsystem.Model.HolidayResponse;
import com.example.osamakhalid.schoolsystem.Model.HolidayResponseList;
import com.example.osamakhalid.schoolsystem.Model.LoginResponse;
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
    LoginResponse userData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exam_schedule);
        recyclerView = findViewById(R.id.exam_schedule_recycler_view);
        listItems = new ArrayList<ExamScheduleResponse>();
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        userData = CommonCalls.getUserData(ExamSchedule.this);
        String base = userData.getUsername() + ":" + userData.getPassword();
        String authHeader = "Basic " + Base64.encodeToString(base.getBytes(), Base64.NO_WRAP);
        getData(authHeader);
        adapter = new ExamScheduleAdapter(listItems, getApplicationContext());
        recyclerView.setAdapter(adapter);
    }

    public void getData(String authHeader) {
        retrofit = RetrofitInitialize.getApiClient();
        clientAPIs = retrofit.create(ClientAPIs.class);
        Call<ExamScheduleResponseList> call = clientAPIs.getExamSchedule(userData.getUsername(), userData.getUsertype(), authHeader);
        call.enqueue(new Callback<ExamScheduleResponseList>() {
            @Override
            public void onResponse(Call<ExamScheduleResponseList> call, Response<ExamScheduleResponseList> response) {
                if (response.isSuccessful()) {
                    ExamScheduleResponseList examScheduleList = response.body();
                    if (examScheduleList != null && examScheduleList.getExamsScheduleData() != null) {
                        listItems.addAll(examScheduleList.getExamsScheduleData());
                        adapter.notifyDataSetChanged();
                    } else {
                        Toast.makeText(ExamSchedule.this, "Schedule not available yet.", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<ExamScheduleResponseList> call, Throwable t) {
                Toast.makeText(ExamSchedule.this, "Sorry something went wrong.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
