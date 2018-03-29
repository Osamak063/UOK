package com.example.osamakhalid.schoolsystem.Activites;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.osamakhalid.schoolsystem.Adapters.SubjectsAdapter;
import com.example.osamakhalid.schoolsystem.BaseConnection.RetrofitInitialize;
import com.example.osamakhalid.schoolsystem.ConnectionInterface.ClientAPIs;
import com.example.osamakhalid.schoolsystem.Consts.Values;
import com.example.osamakhalid.schoolsystem.GlobalCalls.CommonCalls;
import com.example.osamakhalid.schoolsystem.Model.LoginResponse;
import com.example.osamakhalid.schoolsystem.Model.ParentLoginResponse;
import com.example.osamakhalid.schoolsystem.Model.ParentModels.ParentStudentData;
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
    ProgressDialog progressDialog;
    private String base, username;
    private List<ParentStudentData> parentStudentDataList;
    private ArrayList<String> childrenUsernames;
    private Spinner subject_spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subjects);
        //setting up recyclerview
      //  listItems = new ArrayList<>();
     //   progressDialog = CommonCalls.createDialouge(this, "", Values.DIALOGUE_MSG);

        subject_spinner = findViewById(R.id.subject_spinner);
        parentStudentDataList = CommonCalls.getChildrenOfParentList(Subjects.this);
        childrenUsernames = new ArrayList<>();
        for (ParentStudentData data : parentStudentDataList) {
            childrenUsernames.add(data.getName());
        }

        recyclerView = findViewById(R.id.subjects_recyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        if (CommonCalls.getUserType(this).equals(Values.TYPE_PARENT)) {

            subject_spinner.setVisibility(View.VISIBLE);
            ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter
                    (Subjects.this, android.R.layout.simple_spinner_item, childrenUsernames);
            spinnerArrayAdapter.setDropDownViewResource(android.R.layout
                    .simple_spinner_dropdown_item);
            subject_spinner.setAdapter(spinnerArrayAdapter);
            subject_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    username = parentStudentDataList.get(i).getUsername();
                    Log.e("Username", username);
                    if (username != null) {
                        progressDialog = CommonCalls.createDialouge(Subjects.this, "", Values.DIALOGUE_MSG);
                        listItems = new ArrayList<>();
                        getData();
                        adapter = new SubjectsAdapter(listItems, getApplicationContext());
                        recyclerView.setAdapter(adapter);
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });

        } else if (CommonCalls.getUserType(this).equals(Values.TYPE_STUDENT)) {
            progressDialog = CommonCalls.createDialouge(Subjects.this, "", Values.DIALOGUE_MSG);
            listItems = new ArrayList<>();
            getData();
            adapter = new SubjectsAdapter(listItems, getApplicationContext());
            recyclerView.setAdapter(adapter);
        }
    }

    public void getData() {
        retrofit = RetrofitInitialize.getApiClient();
        clientAPIs = retrofit.create(ClientAPIs.class);
        if (CommonCalls.getUserType(Subjects.this).equals(Values.TYPE_PARENT)) {
            ParentLoginResponse loginResponse = CommonCalls.getParentData(Subjects.this);
            base = loginResponse.getUsername() + ":" + loginResponse.getPassword();
        } else if (CommonCalls.getUserType(Subjects.this).equals(Values.TYPE_STUDENT)) {
            LoginResponse loginResponse = CommonCalls.getUserData(Subjects.this);
            base = loginResponse.getUsername() + ":" + loginResponse.getPassword();
            username = loginResponse.getUsername();
        }
        String authHeader = "Basic " + Base64.encodeToString(base.getBytes(), Base64.NO_WRAP);

        Call<SubjectResponseList> call = clientAPIs.getSubjects(username, authHeader);
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
                        Toast.makeText(Subjects.this, Values.DATA_ERROR, Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<SubjectResponseList> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(Subjects.this, Values.SERVER_ERROR, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
