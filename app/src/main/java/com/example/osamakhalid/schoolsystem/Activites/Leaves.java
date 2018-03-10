package com.example.osamakhalid.schoolsystem.Activites;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.osamakhalid.schoolsystem.BaseConnection.RetrofitInitialize;
import com.example.osamakhalid.schoolsystem.ConnectionInterface.ClientAPIs;
import com.example.osamakhalid.schoolsystem.Consts.Values;
import com.example.osamakhalid.schoolsystem.Fragments.LeavesListFragment;
import com.example.osamakhalid.schoolsystem.Fragments.SubmitLeaveFragment;
import com.example.osamakhalid.schoolsystem.GlobalCalls.CommonCalls;
import com.example.osamakhalid.schoolsystem.Model.LoginResponse;
import com.example.osamakhalid.schoolsystem.Model.TeacherData_Model;
import com.example.osamakhalid.schoolsystem.Model.Teacher_Model;
import com.example.osamakhalid.schoolsystem.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class Leaves extends AppCompatActivity {
    Fragment fragment;
    public static List<TeacherData_Model> teacherData_models;
    public static ArrayList<String> teachersUsername;
    private ProgressDialog progressDilougue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leaves);
        teacherData_models = new ArrayList<>();
        teachersUsername = new ArrayList<>();
        getTeacherData();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.leaves_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    public void getTeacherData() {

        Retrofit retrofit = RetrofitInitialize.getApiClient();
        ClientAPIs clientAPIs = retrofit.create(ClientAPIs.class);
        final LoginResponse loginResponse = CommonCalls.getUserData(Leaves.this);
        String base = loginResponse.getUsername() + ":" + loginResponse.getPassword();
        String authHeader = "Basic " + Base64.encodeToString(base.getBytes(), Base64.NO_WRAP);
        Call<Teacher_Model> call = clientAPIs.getCourseTeacherData
                (loginResponse.getUsername(), authHeader);
        call.enqueue(new Callback<Teacher_Model>() {
            @Override
            public void onResponse(Call<Teacher_Model> call, Response<Teacher_Model> response) {

                if (response.isSuccessful()) {

                    Teacher_Model teacher_model = response.body();
                    if (teacher_model != null && teacher_model.getTeacherData() != null) {
                        teacherData_models = teacher_model.getTeacherData();
                        getTeachersUsername(teacherData_models);
                    } else {
                        Toast.makeText(Leaves.this, "Teachers not available yet.", Toast.LENGTH_SHORT).show();
                    }

                }

            }

            @Override
            public void onFailure(Call<Teacher_Model> call, Throwable t) {

                Toast.makeText(Leaves.this, Values.SERVER_ERROR, Toast.LENGTH_SHORT).show();

            }
        });
    }

    public void getTeachersUsername(List<TeacherData_Model> list) {
        if (teachersUsername != null) {
            teachersUsername.clear();
        }
        for (TeacherData_Model data : list) {
            teachersUsername.add(data.getTeacherUsername());
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.leaves_list) {
            fragment = new LeavesListFragment();
        } else if (id == R.id.submit_leave_menu) {
            fragment = new SubmitLeaveFragment();
            Bundle bundle = new Bundle();
            bundle.putStringArrayList("usernames", teachersUsername);
            fragment.setArguments(bundle);
        }
        if (fragment != null) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.fragment_leaves_list, fragment);
            ft.commit();
        }
        return true;
    }
}