package com.example.osamakhalid.schoolsystem.Activites;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.osamakhalid.schoolsystem.Adapters.HomeWork_Adapter;
import com.example.osamakhalid.schoolsystem.BaseConnection.RetrofitInitialize;
import com.example.osamakhalid.schoolsystem.ConnectionInterface.ClientAPIs;
import com.example.osamakhalid.schoolsystem.Consts.Values;
import com.example.osamakhalid.schoolsystem.GlobalCalls.CommonCalls;
import com.example.osamakhalid.schoolsystem.Model.Homework_Data_Model;
import com.example.osamakhalid.schoolsystem.Model.Homework_Model;
import com.example.osamakhalid.schoolsystem.Model.LoginResponse;
import com.example.osamakhalid.schoolsystem.Model.ParentLoginResponse;
import com.example.osamakhalid.schoolsystem.Model.ParentModels.ParentStudentData;
import com.example.osamakhalid.schoolsystem.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class HomeWork extends AppCompatActivity implements View.OnClickListener {

    public RecyclerView recyclerView;
    public RecyclerView.Adapter adapter;
    public int day = 0;
    public int month = 0;
    public int year = 0;
    ProgressDialog progressDialog;
    Button check;
    Spinner studentName_spinner;
    String username;
    List<ParentStudentData> parentStudentDataList;
    List<String> childrenUsernames;
    private List<Homework_Data_Model> listItem;
    ImageView calenderButton;
    Calendar mCurrentDate;
    TextView calenderDate;
    Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_work);
        //Setting up Toolbar
        toolbar = findViewById(R.id.toolBar);
        toolbar.setTitle("Homework");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomeWork.this, DashboardActivity.class));
            }
        });
        listItem = new ArrayList<>();
        studentName_spinner = findViewById(R.id.studentName_spinner);
        calenderButton = findViewById(R.id.image_button_calender_icon);
        calenderDate = findViewById(R.id.calender_date);
        mCurrentDate = Calendar.getInstance();
        day = mCurrentDate.get(Calendar.DAY_OF_MONTH);
        month = mCurrentDate.get(Calendar.MONTH);
        year = mCurrentDate.get(Calendar.YEAR);
        calenderButton.setOnClickListener(this);


        if (CommonCalls.getUserType(this).equals(Values.TYPE_PARENT)) {

            parentStudentDataList = CommonCalls.getChildrenOfParentList(HomeWork.this);
            childrenUsernames = new ArrayList<>();
            for (ParentStudentData data : parentStudentDataList) {
                childrenUsernames.add(data.getName());
            }

            studentName_spinner.setVisibility(View.VISIBLE);
            ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter
                    (HomeWork.this, android.R.layout.simple_spinner_item, childrenUsernames);
            spinnerArrayAdapter.setDropDownViewResource(android.R.layout
                    .simple_spinner_dropdown_item);
            studentName_spinner.setAdapter(spinnerArrayAdapter);
            studentName_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    username = parentStudentDataList.get(i).getUsername();
                    Log.e("Username", username);
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });

        }

        check = findViewById(R.id.checkHomeWork);
        check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressDialog = CommonCalls.createDialouge(HomeWork.this, "", Values.DIALOGUE_MSG);
                listItem.clear();
                getHomeWork();
            }
        });

    }


    //Homework Function
    public void getHomeWork() {

        final Retrofit[] retrofit = {RetrofitInitialize.getApiClient()};
        ClientAPIs clientAPIs = retrofit[0].create(ClientAPIs.class);
        String base = null;
        if (CommonCalls.getUserType(HomeWork.this).equals(Values.TYPE_PARENT)) {
            ParentLoginResponse loginResponse = CommonCalls.getParentData(HomeWork.this);
            base = loginResponse.getUsername() + ":" + loginResponse.getPassword();
        } else if (CommonCalls.getUserType(HomeWork.this).equals(Values.TYPE_STUDENT)) {
            LoginResponse loginResponse = CommonCalls.getUserData(HomeWork.this);
            base = loginResponse.getUsername() + ":" + loginResponse.getPassword();
            username = loginResponse.getUsername();
        }
        String authHeader = "Basic " + Base64.encodeToString(base.getBytes(), Base64.NO_WRAP);
        Call<Homework_Model> call = clientAPIs.getHomeWOrk(calenderDate.getText().toString(), username, authHeader);
        call.enqueue(new Callback<Homework_Model>() {
            @Override
            public void onResponse(Call<Homework_Model> call, Response<Homework_Model> response) {
                if (response.isSuccessful()) {

                    Homework_Model homework_model = response.body();

                    if (homework_model != null) {
                        progressDialog.dismiss();
                        if (homework_model.getHomeworkDateData() != null) {
                            listItem.addAll(homework_model.getHomeworkDateData());
                            recyclerView = findViewById(R.id.homeworkview);
                            recyclerView.setHasFixedSize(true);
                            recyclerView.setLayoutManager(new LinearLayoutManager(HomeWork.this));
                            adapter = new HomeWork_Adapter(listItem, HomeWork.this);
                            adapter.notifyDataSetChanged();
                            recyclerView.setAdapter(adapter);
                        } else {
                            listItem = new ArrayList<>();
                            recyclerView = findViewById(R.id.homeworkview);
                            recyclerView.setHasFixedSize(true);
                            recyclerView.setLayoutManager(new LinearLayoutManager(HomeWork.this));
                            adapter = new HomeWork_Adapter(listItem, HomeWork.this);
                            adapter.notifyDataSetChanged();
                            recyclerView.setAdapter(adapter);
                            Toast.makeText(HomeWork.this, Values.DATA_ERROR, Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(HomeWork.this, Values.DATA_ERROR, Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<Homework_Model> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(HomeWork.this, Values.SERVER_ERROR, Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public void onClick(View view) {
        if (view.getId() == calenderButton.getId()) {
            DatePickerDialog datePickerDialog = new DatePickerDialog(HomeWork.this, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                    int selectedYear = i;
                    int selectedMonth = i1 + 1;
                    int selectedDay = i2;
                    calenderDate.setText(selectedYear + "-" + selectedMonth + "-" + selectedDay);
                }
            }, year, month, day);
            datePickerDialog.show();
        }
    }

}
