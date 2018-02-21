package com.example.osamakhalid.schoolsystem.Activites;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.widget.Toast;

import com.example.osamakhalid.schoolsystem.Adapters.NewsAndEvents_Adapter;
import com.example.osamakhalid.schoolsystem.Adapters.SubjectsAdapter;
import com.example.osamakhalid.schoolsystem.BaseConnection.RetrofitInitialize;
import com.example.osamakhalid.schoolsystem.ConnectionInterface.ClientAPIs;
import com.example.osamakhalid.schoolsystem.GlobalCalls.CommonCalls;
import com.example.osamakhalid.schoolsystem.Model.HolidayResponse;
import com.example.osamakhalid.schoolsystem.Model.HolidayResponseList;
import com.example.osamakhalid.schoolsystem.Model.LoginResponse;
import com.example.osamakhalid.schoolsystem.Model.SubjectResponse;
import com.example.osamakhalid.schoolsystem.Model.SubjectResponseList;
import com.example.osamakhalid.schoolsystem.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class Subjects extends AppCompatActivity {
    public RecyclerView recyclerView;
    public RecyclerView.Adapter adapter;
    public List<SubjectResponse> listItems;
    private Retrofit retrofit;
    private ClientAPIs clientAPIs;
    LoginResponse userData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subjects);
        //setting up recyclerview
        listItems = new ArrayList<>();
        recyclerView = findViewById(R.id.subjects_recyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        userData = CommonCalls.getUserData(Subjects.this);
        String base = userData.getUsername() + ":" + userData.getPassword();
        String authHeader = "Basic " + Base64.encodeToString(base.getBytes(), Base64.NO_WRAP);
        getData(authHeader);
        adapter = new SubjectsAdapter(listItems, getApplicationContext());
        recyclerView.setAdapter(adapter);
    }

    public void getData(String authHeader) {
        retrofit = RetrofitInitialize.getApiClient();
        clientAPIs = retrofit.create(ClientAPIs.class);
        Call<SubjectResponseList> call = clientAPIs.getSubjects(userData.getUsertype(), userData.getUsername(), authHeader);
        call.enqueue(new Callback<SubjectResponseList>() {
            @Override
            public void onResponse(Call<SubjectResponseList> call, Response<SubjectResponseList> response) {
                if (response.isSuccessful()) {
                    SubjectResponseList subjectList = response.body();
                    if (subjectList != null) {
                        listItems.addAll(subjectList.getSubjectData());
                        adapter.notifyDataSetChanged();
                    }
                }
            }

            @Override
            public void onFailure(Call<SubjectResponseList> call, Throwable t) {
                Toast.makeText(Subjects.this, "Sorry something went wrong.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
