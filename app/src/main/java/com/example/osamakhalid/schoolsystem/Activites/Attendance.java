package com.example.osamakhalid.schoolsystem.Activites;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.support.v7.widget.Toolbar;

import com.example.osamakhalid.schoolsystem.Adapters.Attendance_Adapter;
import com.example.osamakhalid.schoolsystem.BaseConnection.RetrofitInitialize;
import com.example.osamakhalid.schoolsystem.ConnectionInterface.ClientAPIs;
import com.example.osamakhalid.schoolsystem.Consts.Values;
import com.example.osamakhalid.schoolsystem.GlobalCalls.CommonCalls;
import com.example.osamakhalid.schoolsystem.Model.AttendanceResponse;
import com.example.osamakhalid.schoolsystem.Model.AttendanceValuesResponse;
import com.example.osamakhalid.schoolsystem.Model.LoginResponse;
import com.example.osamakhalid.schoolsystem.Model.ParentLoginResponse;
import com.example.osamakhalid.schoolsystem.Model.ParentModels.ParentStudentData;
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
    public Spinner childrenSpinner;
    public RecyclerView.Adapter adapter;
    public List<AttendanceValuesResponse> listItems;
    public List<String> MonthNameListItems;
    private Retrofit retrofit;
    private ClientAPIs clientAPIs;
    LoginResponse userData;
    ParentLoginResponse parentData;
    String monthName;
    int index = 0;
    Calendar calendar;
    TextView heading;
    private ProgressDialog progressDialouge;
    public List<ParentStudentData> parentStudentDataList;
    String base;
    List<String> childrenUsernames;
    String spinnerValue;
    String authHeader;
    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance);
        //Setting up Toolbar
        toolbar = findViewById(R.id.toolBar);
        toolbar.setTitle("Attendence");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Attendance.this, Chart_Attendence.class));
            }
        });
        listItems = new ArrayList<>();
        MonthNameListItems = new ArrayList<>();

        MonthNameListItems.addAll(Arrays.asList(getResources().getStringArray(R.array.months)));
        Intent i = getIntent();
        if (i != null) {
            monthName = i.getStringExtra("name");
        }
        recyclerView = findViewById(R.id.attendance_recycler_view);
        childrenSpinner = findViewById(R.id.attendance_children_spinner);
      //  heading = findViewById(R.id.attendance_header);
        calendar = Calendar.getInstance();
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        if (CommonCalls.getUserType(Attendance.this).equals(Values.TYPE_STUDENT)) {
            progressDialouge = CommonCalls.createDialouge(Attendance.this, "", Values.DIALOGUE_MSG);
            childrenSpinner.setVisibility(View.GONE);
            userData = CommonCalls.getUserData(Attendance.this);
           // base = userData.getUsername() + ":" + userData.getPassword();
            spinnerValue = "";
            index = MonthNameListItems.indexOf(monthName);
            getData();
        } else if (CommonCalls.getUserType(Attendance.this).equals(Values.TYPE_PARENT)) {
            parentStudentDataList = CommonCalls.getChildrenOfParentList(Attendance.this);
            childrenUsernames= new ArrayList<>();
            for (ParentStudentData data : parentStudentDataList) {
                childrenUsernames.add(data.getName());
            }
            childrenSpinner.setVisibility(View.VISIBLE);
            ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter
                    (Attendance.this, android.R.layout.simple_spinner_item, childrenUsernames);
            spinnerArrayAdapter.setDropDownViewResource(android.R.layout
                    .simple_spinner_dropdown_item);
            childrenSpinner.setAdapter(spinnerArrayAdapter);
            parentData = CommonCalls.getParentData(Attendance.this);
            base = parentData.getUsername() + ":" + parentData.getPassword();
            if (childrenUsernames.size() > 0)
                spinnerValue = childrenUsernames.get(0);
            authHeader = "Basic " + Base64.encodeToString(base.getBytes(), Base64.NO_WRAP);
            index = MonthNameListItems.indexOf(monthName);
            getParentChildrenData(authHeader,spinnerValue);
        }
        childrenSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                spinnerValue = parentStudentDataList.get(i).getUsername();
                progressDialouge = CommonCalls.createDialouge(Attendance.this, "", Values.DIALOGUE_MSG);
                listItems.clear();
                getParentChildrenData(authHeader,spinnerValue);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        adapter = new Attendance_Adapter(listItems, getApplicationContext());
        recyclerView.setAdapter(adapter);
    }

    public void getData() {
        retrofit = RetrofitInitialize.getApiClient();
        clientAPIs = retrofit.create(ClientAPIs.class);
        if (CommonCalls.getUserType(Attendance.this).equals(Values.TYPE_PARENT)) {
            ParentLoginResponse loginResponse = CommonCalls.getParentData(Attendance.this);
            base = loginResponse.getUsername() + ":" + loginResponse.getPassword();
        } else if (CommonCalls.getUserType(Attendance.this).equals(Values.TYPE_STUDENT)) {
            LoginResponse loginResponse = CommonCalls.getUserData(Attendance.this);
            base = loginResponse.getUsername() + ":" + loginResponse.getPassword();

        }
        authHeader = "Basic " + Base64.encodeToString(base.getBytes(), Base64.NO_WRAP);

        int year = calendar.get(Calendar.YEAR);
      //  heading.setText("Attendance - " + year);
        String monthyear;
        if (index > 9) {
            monthyear = (index + 1) + "-" + year;
        } else {
            monthyear = "0" + (index + 1) + "-" + year;
        }
        Call<AttendanceResponse> call = clientAPIs.getAttendance(userData.getUsername(), "english", monthyear, authHeader);
        call.enqueue(new Callback<AttendanceResponse>() {
            @Override
            public void onResponse(Call<AttendanceResponse> call, Response<AttendanceResponse> response) {
                if (response.isSuccessful()) {

                    AttendanceResponse attendanceResponseList = response.body();
                    if (attendanceResponseList != null) {
                        progressDialouge.dismiss();
                        if (attendanceResponseList.getData() != null) {
                            listItems.addAll(attendanceResponseList.getData().getAttendance());
                            adapter.notifyDataSetChanged();
                        }


                    }

                } else {
                    progressDialouge.dismiss();
                    Toast.makeText(Attendance.this, Values.DATA_ERROR, Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<AttendanceResponse> call, Throwable t) {
                progressDialouge.dismiss();
                Toast.makeText(Attendance.this, Values.SERVER_ERROR, Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void getParentChildrenData(String authHeader,String SpinnerValue) {
        retrofit = RetrofitInitialize.getApiClient();
        clientAPIs = retrofit.create(ClientAPIs.class);
        int year = calendar.get(Calendar.YEAR);
//        heading.setText("Attendance - " + year);
        String monthyear;
        if (index > 9) {
            monthyear = (index + 1) + "-" + year;
        } else {
            monthyear = "0" + (index + 1) + "-" + year;
        }
        Call<AttendanceResponse> call = clientAPIs.getAttendance(SpinnerValue, "english", monthyear, authHeader);
        call.enqueue(new Callback<AttendanceResponse>() {
            @Override
            public void onResponse(Call<AttendanceResponse> call, Response<AttendanceResponse> response) {
                if (response.isSuccessful()) {

                    AttendanceResponse attendanceResponseList = response.body();
                    if (attendanceResponseList != null) {
                        progressDialouge.dismiss();
                        if (attendanceResponseList.getData() != null) {
                            listItems.addAll(attendanceResponseList.getData().getAttendance());
                            adapter.notifyDataSetChanged();
                        }
                        else {
                            progressDialouge.dismiss();
                            listItems.clear();
                            adapter.notifyDataSetChanged();
                            Toast.makeText(Attendance.this, Values.DATA_ERROR, Toast.LENGTH_SHORT).show();
                        }


                    } else {
                        progressDialouge.dismiss();
                        listItems.clear();
                        adapter.notifyDataSetChanged();
                        Toast.makeText(Attendance.this, Values.DATA_ERROR, Toast.LENGTH_SHORT).show();
                    }

                } else {
                    progressDialouge.dismiss();
                    listItems.clear();
                    adapter.notifyDataSetChanged();
                //    Toast.makeText(Attendance.this, Values.DATA_ERROR, Toast.LENGTH_SHORT).show();
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
