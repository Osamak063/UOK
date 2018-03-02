package com.example.osamakhalid.schoolsystem.Activites;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.widget.Toast;

import com.example.osamakhalid.schoolsystem.Adapters.SubjectsAdapter;
import com.example.osamakhalid.schoolsystem.BaseConnection.RetrofitInitialize;
import com.example.osamakhalid.schoolsystem.ConnectionInterface.ClientAPIs;
import com.example.osamakhalid.schoolsystem.Consts.Values;
import com.example.osamakhalid.schoolsystem.GlobalCalls.CommonCalls;
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
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subjects);
        //setting up recyclerview
        listItems = new ArrayList<>();
        progressDialog = CommonCalls.createDialouge(this,"", Values.DIALOGUE_MSG);
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
        Call<SubjectResponseList> call = clientAPIs.getSubjects( userData.getUsername(), authHeader);
        call.enqueue(new Callback<SubjectResponseList>() {
            @Override
            public void onResponse(Call<SubjectResponseList> call, Response<SubjectResponseList> response) {
                if (response.isSuccessful()) {
                    SubjectResponseList subjectList = response.body();
                    if (subjectList != null && subjectList.getSubjectData() != null) {
                        progressDialog.dismiss();
                        listItems.addAll(subjectList.getSubjectData());
                        adapter.notifyDataSetChanged();
                    } else {
                        progressDialog.dismiss();
                        Toast.makeText(Subjects.this, "Subjects not available yet.", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<SubjectResponseList> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(Subjects.this, "Sorry something went wrong.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
