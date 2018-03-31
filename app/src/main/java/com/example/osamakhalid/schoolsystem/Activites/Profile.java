package com.example.osamakhalid.schoolsystem.Activites;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.osamakhalid.schoolsystem.Adapters.Teachers_Adapter;
import com.example.osamakhalid.schoolsystem.BaseConnection.RetrofitInitialize;
import com.example.osamakhalid.schoolsystem.ConnectionInterface.ClientAPIs;
import com.example.osamakhalid.schoolsystem.Consts.Values;
import com.example.osamakhalid.schoolsystem.GlobalCalls.CommonCalls;
import com.example.osamakhalid.schoolsystem.Model.ItemClickListener;
import com.example.osamakhalid.schoolsystem.Model.LoginResponse;
import com.example.osamakhalid.schoolsystem.Model.ParentLoginResponse;
import com.example.osamakhalid.schoolsystem.Model.ParentModels.ParentStudentData;
import com.example.osamakhalid.schoolsystem.Model.TeacherData_Model;
import com.example.osamakhalid.schoolsystem.Model.Teacher_Model;
import com.example.osamakhalid.schoolsystem.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class Profile extends AppCompatActivity implements ItemClickListener {
    RecyclerView recyclerView;
    public Teachers_Adapter adapter;
    public static String teacher_id;
    public  List<TeacherData_Model> teacher_list;
    ProgressDialog progressDialog;
    private String username;
    Spinner teacher_spinner;
    private List<ParentStudentData> parentStudentDataList;
    private List<String> childrenUsernames;
    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        //Setting up Toolbar
        toolbar = findViewById(R.id.toolBar);
        toolbar.setTitle("Teachers");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Profile.this, DashboardActivity.class));
            }
        });
        recyclerView = findViewById(R.id.profile_recycler_view);
        progressDialog = CommonCalls.createDialouge(this, "", Values.DIALOGUE_MSG);
        teacher_list = new ArrayList<>();
        teacher_spinner = findViewById(R.id.teacher_view_spinner);
        //getTeacherData();
   //     teacher_list = DashboardActivity.teacherData_models;
//        teacher_id = teacher_list.get(0).getTeacherId();
     //   if (teacher_list != null) {
        //FOR PARENT CHILD DATA

        if(CommonCalls.getUserType(this).equals(Values.TYPE_PARENT)){

            parentStudentDataList = CommonCalls.getChildrenOfParentList(this);
            childrenUsernames = new ArrayList<>();
            for (ParentStudentData data : parentStudentDataList) {
                childrenUsernames.add(data.getName());
            }

            teacher_spinner.setVisibility(View.VISIBLE);
            ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter
                    (this, android.R.layout.simple_spinner_item, childrenUsernames);
            spinnerArrayAdapter.setDropDownViewResource(android.R.layout
                    .simple_spinner_dropdown_item);
            teacher_spinner.setAdapter(spinnerArrayAdapter);
            teacher_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    username = parentStudentDataList.get(i).getUsername();
                    if ((username!= null)){
                        getTeacherData();
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });
        }else if(CommonCalls.getUserType(this).equals(Values.TYPE_STUDENT)){
            getTeacherData();
        }

            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));





    }

    @Override
    public void onClick(View view, String name) {
        Intent i = new Intent(this, TeacherProfile.class);
        i.putExtra("name", name);
        startActivity(i);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_item_menu, menu);
        MenuItem item = menu.findItem(R.id.search_item);
        SearchView searchView = (SearchView) item.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                adapter.getFilter().filter(s);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                adapter.getFilter().filter(s);
                return false;
            }
        });
        return true;
    }

    public void getTeacherData() {

        Retrofit retrofit = RetrofitInitialize.getApiClient();
        ClientAPIs clientAPIs = retrofit.create(ClientAPIs.class);
        String base = null;
        if(CommonCalls.getUserType(Profile.this).equals(Values.TYPE_PARENT)){
            final ParentLoginResponse loginResponse = CommonCalls.getParentData(Profile.this);
            base = loginResponse.getUsername() + ":" + loginResponse.getPassword();


        }

        else if(CommonCalls.getUserType(Profile.this).equals(Values.TYPE_STUDENT)){
            final LoginResponse loginResponse = CommonCalls.getUserData(Profile.this);
            base = loginResponse.getUsername() + ":" + loginResponse.getPassword();
            username = loginResponse.getUsername();
        }
        String authHeader = "Basic " + Base64.encodeToString(base.getBytes(), Base64.NO_WRAP);
        Call<Teacher_Model> call = clientAPIs.getCourseTeacherData
                (username, authHeader);
        call.enqueue(new Callback<Teacher_Model>() {
            @Override
            public void onResponse(Call<Teacher_Model> call, Response<Teacher_Model> response) {

                if (response.isSuccessful()) {

                    Teacher_Model teacher_model = response.body();
                    if (teacher_model != null) {
                        progressDialog.dismiss();
                        if(teacher_model.getTeacherData()!= null){
                            teacher_list.clear();
                            teacher_list.addAll(teacher_model.getTeacherData());
                            adapter = new Teachers_Adapter(teacher_list, getApplicationContext());
                            adapter.setItemClickListener(Profile.this);
                            adapter.notifyDataSetChanged();
                            recyclerView.setAdapter(adapter);
                        }else{
                            teacher_list.clear();
                            progressDialog.dismiss();
                            adapter = new Teachers_Adapter(teacher_list, getApplicationContext());
                            adapter.notifyDataSetChanged();
                            recyclerView.setAdapter(adapter);
                            Toast.makeText(Profile.this, Values.DATA_ERROR,Toast.LENGTH_SHORT).show();

                          }

                     //   startActivity(new Intent(Profile.this, Profile.class));

                    }

                }

            }

            @Override
            public void onFailure(Call<Teacher_Model> call, Throwable t) {

                Toast.makeText(Profile.this, Values.SERVER_ERROR, Toast.LENGTH_SHORT).show();

            }
        });
    }


}
