package com.example.osamakhalid.schoolsystem.Activites;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.widget.TextView;
import android.widget.Toast;

import com.example.osamakhalid.schoolsystem.Adapters.Attendance_Adapter;
import com.example.osamakhalid.schoolsystem.BaseConnection.RetrofitInitialize;
import com.example.osamakhalid.schoolsystem.ConnectionInterface.ClientAPIs;
import com.example.osamakhalid.schoolsystem.Consts.Values;
import com.example.osamakhalid.schoolsystem.GlobalCalls.CommonCalls;
import com.example.osamakhalid.schoolsystem.Model.AttendanceResponse;
import com.example.osamakhalid.schoolsystem.Model.AttendanceValuesResponse;
import com.example.osamakhalid.schoolsystem.Model.LoginResponse;
import com.example.osamakhalid.schoolsystem.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class Attendance extends AppCompatActivity {
    public RecyclerView recyclerView;
    public RecyclerView.Adapter adapter;
    public List<AttendanceValuesResponse> listItems;
    public List<String> MonthNameListItems;
    private Retrofit retrofit;
    private ClientAPIs clientAPIs;
    LoginResponse userData;
    String monthName;
    int index = 0;
    Calendar calendar;
    TextView heading;
    private ProgressDialog progressDialouge;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance);
        listItems = new ArrayList<>();
        MonthNameListItems = new ArrayList<>();
        progressDialouge = CommonCalls.createDialouge(this, "", Values.DIALOGUE_MSG);
        MonthNameListItems.addAll(Arrays.asList(getResources().getStringArray(R.array.months)));
        Intent i = getIntent();
        if (i != null) {
            monthName = i.getStringExtra("name");
        }
        recyclerView = findViewById(R.id.attendance_recycler_view);
        heading = findViewById(R.id.attendance_header);
        calendar = Calendar.getInstance();
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        userData = CommonCalls.getUserData(Attendance.this);
        String base = userData.getUsername() + ":" + userData.getPassword();
        String authHeader = "Basic " + Base64.encodeToString(base.getBytes(), Base64.NO_WRAP);
        index = MonthNameListItems.indexOf(monthName);
        getData(authHeader);
        adapter = new Attendance_Adapter(listItems, getApplicationContext());
        recyclerView.setAdapter(adapter);
    }

    public void getData(String authHeader) {
        retrofit = RetrofitInitialize.getApiClient();
        clientAPIs = retrofit.create(ClientAPIs.class);
        int year = calendar.get(Calendar.YEAR);
        heading.setText("Attendance - " + year);
        String monthyear;
        if (index > 9) {
            monthyear = (index + 1) + "-" + year;
        } else {
            monthyear = "0" + (index + 1) + "-" + year;
        }
        Call<AttendanceResponse> call = clientAPIs.getAttendance(userData.getStudentID(), userData.getUsername(), "english", monthyear, authHeader);
        call.enqueue(new Callback<AttendanceResponse>() {
            @Override
            public void onResponse(Call<AttendanceResponse> call, Response<AttendanceResponse> response) {
                if (response.isSuccessful()) {
                    AttendanceResponse attendanceResponseList = response.body();
                    if (attendanceResponseList != null && attendanceResponseList.getData() != null) {
                        progressDialouge.dismiss();
                        listItems.addAll(attendanceResponseList.getData().getAttendance());
                        adapter.notifyDataSetChanged();
                    } else {
                        progressDialouge.dismiss();
                        Toast.makeText(Attendance.this, "Attendance not available yet.", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<AttendanceResponse> call, Throwable t) {
                progressDialouge.dismiss();
                Toast.makeText(Attendance.this, Values.SERVER_ERROR, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
