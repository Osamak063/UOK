package com.example.osamakhalid.schoolsystem.Activites;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.osamakhalid.schoolsystem.BaseConnection.RetrofitInitialize;
import com.example.osamakhalid.schoolsystem.ConnectionInterface.ClientAPIs;
import com.example.osamakhalid.schoolsystem.Consts.Values;
import com.example.osamakhalid.schoolsystem.GlobalCalls.CommonCalls;
import com.example.osamakhalid.schoolsystem.Model.LoginResponse;
import com.example.osamakhalid.schoolsystem.Model.TeacherData_Model;
import com.example.osamakhalid.schoolsystem.Model.Teacher_Model;
import com.example.osamakhalid.schoolsystem.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class DashboardActivity extends AppCompatActivity {
    private LinearLayout attendance, syllabus, results, noticeBoard, transport, messages, library, photoGallery, newsAndEvents,

            examSchedule, teacherDetails, fees, holidayAlert, homework, subjects, timeTable;


            examSchedule, teacherDetails, fees, holidayAlert, homework, subjects, timeTable,leaves;
    ;
    public AlertResponse_Model alert_response_model;
    public static String Currentdate;
    public static List<Alert_Model> alert_model;
    public static List<Homework_Data_Model> homework_data_models;



    public static List<TeacherData_Model> teacherData_models;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        attendance = findViewById(R.id.attendance);
        syllabus = findViewById(R.id.syllabus_view);
        timeTable = findViewById(R.id.timeTable);
        results = findViewById(R.id.results);
        noticeBoard = findViewById(R.id.notice_board);
        transport = findViewById(R.id.transport);
        messages = findViewById(R.id.messages);
        homework = findViewById(R.id.homework);
        photoGallery = findViewById(R.id.gallery);
        newsAndEvents = findViewById(R.id.news_and_events);
        teacherDetails = findViewById(R.id.teacher_details);
        subjects = findViewById(R.id.subjects);
        fees = findViewById(R.id.fees);
        holidayAlert = findViewById(R.id.holiday_alert);
        examSchedule = findViewById(R.id.exam_schedule);

        library = findViewById(R.id.library_view);

        leaves = findViewById(R.id.leaves);

        library.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(DashboardActivity.this,Initiate_Library.class));
            }
        });
        homework.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(DashboardActivity.this, HomeWork.class));
            }
        });

        syllabus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //     startActivity(new Intent(DashboardActivity.this,Syllabus.class));
            }
        });

        timeTable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(DashboardActivity.this, TimeTable.class));
            }
        });
        results.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(DashboardActivity.this, ExamResult_Category.class));
            }
        });
        noticeBoard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(DashboardActivity.this, Circular.class));

            }
        });
        transport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(DashboardActivity.this, Transport.class));
            }
        });
        messages.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(DashboardActivity.this, AllMessages.class));
            }
        });
        attendance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(DashboardActivity.this, Chart_Attendence.class));
            }
        });
        photoGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(DashboardActivity.this, PhotoGallery.class));
            }
        });
        newsAndEvents.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(DashboardActivity.this, NewsAndEvents.class));
            }
        });
        teacherDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getTeacherData();

            }
        });
        fees.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(DashboardActivity.this, Fees.class));
            }
        });
        holidayAlert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(DashboardActivity.this, Alert.class));

            }
        });
        subjects.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(DashboardActivity.this, Subjects.class));

            }
        });
        examSchedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(DashboardActivity.this, ExamSchedule.class));

            }
        });
        leaves.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(DashboardActivity.this, Leaves.class));

            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.help_item_icon, menu);
        getMenuInflater().inflate(R.menu.menu_item, menu);
        MenuItem item = menu.findItem(R.id.help_item);
        item.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {

                Intent i = new Intent(DashboardActivity.this, AboutClass.class);
                startActivity(i);
                return true;
            }
        });
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.logout_menu) {
            SharedPreferences mPrefs = getSharedPreferences("UserData", 0);
            mPrefs.edit().remove("UserObject").apply();
            Intent i = new Intent(DashboardActivity.this, MainActivity.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(i);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    public void getTeacherData() {

        Retrofit retrofit = RetrofitInitialize.getApiClient();
        ClientAPIs clientAPIs = retrofit.create(ClientAPIs.class);
        final LoginResponse loginResponse = CommonCalls.getUserData(DashboardActivity.this);
        String base = loginResponse.getUsername() + ":" + loginResponse.getPassword();
        String authHeader = "Basic " + Base64.encodeToString(base.getBytes(), Base64.NO_WRAP);
        Call<Teacher_Model> call = clientAPIs.getCourseTeacherData
                (loginResponse.getUsername(), authHeader);
        call.enqueue(new Callback<Teacher_Model>() {
            @Override
            public void onResponse(Call<Teacher_Model> call, Response<Teacher_Model> response) {

                if (response.isSuccessful()) {

                    Teacher_Model teacher_model = response.body();
                    if (teacher_model != null) {

                        teacherData_models = teacher_model.getTeacherData();
                        startActivity(new Intent(DashboardActivity.this, Profile.class));

                    }

                }

            }

            @Override
            public void onFailure(Call<Teacher_Model> call, Throwable t) {

                Toast.makeText(DashboardActivity.this, Values.SERVER_ERROR, Toast.LENGTH_SHORT).show();

            }
        });
    }


}


