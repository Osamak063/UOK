package com.example.osamakhalid.schoolsystem.Activites;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.osamakhalid.schoolsystem.Adapters.Transport_Adapter;
import com.example.osamakhalid.schoolsystem.BaseConnection.RetrofitInitialize;
import com.example.osamakhalid.schoolsystem.ConnectionInterface.ClientAPIs;
import com.example.osamakhalid.schoolsystem.Consts.Values;
import com.example.osamakhalid.schoolsystem.GlobalCalls.CommonCalls;
import com.example.osamakhalid.schoolsystem.Model.LoginResponse;
import com.example.osamakhalid.schoolsystem.Model.ParentLoginResponse;
import com.example.osamakhalid.schoolsystem.Model.ParentModels.ParentChildData_Model;
import com.example.osamakhalid.schoolsystem.Model.ParentModels.ParentStudentData;
import com.example.osamakhalid.schoolsystem.Model.TransportResponse_Model;
import com.example.osamakhalid.schoolsystem.Model.Transport_Model;
import com.example.osamakhalid.schoolsystem.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;


public class Transport extends AppCompatActivity{
    private  ArrayList<ParentChildData_Model> listData;
    public RecyclerView recyclerView;
    public RecyclerView.Adapter adapter;
    public List<Transport_Model> listItems;
    List<ParentChildData_Model> dataList;
    public Spinner transport_spinner;
    ProgressDialog progressDialog;
    String StudentID = null;
    LoginResponse loginResponse;
    String base;
    List<ParentStudentData> parentStudentDataList;
    List<String>childrenUsernames;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transport);
        //Setting up Toolbar
        toolbar = findViewById(R.id.toolBar);
        toolbar.setTitle("Transport");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Transport.this, DashboardActivity.class));
            }
        });
        //ParentLoginResponse loginResponse = CommonCalls.getParentData(Transport.this);
        listItems = new ArrayList<>();
        listData = new ArrayList<>();
        loginResponse = CommonCalls.getUserData(Transport.this);
        progressDialog = CommonCalls.createDialouge(this, "", Values.DIALOGUE_MSG);
      //  list = CommonCalls.getParentChildsInfo(Transport.this);
        getAllTransportDetail();


    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.perosnal_transport, menu);
        return true;
    }


        @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        Log.e("ID : ", Integer.toString(id));
        if (id == R.id.personal_transport) {
        //    progressDialog = CommonCalls.createDialouge(this, "", Values.DIALOGUE_MSG);
            transport_spinner = findViewById(R.id.transport_spinner);


            if (CommonCalls.getUserType(Transport.this).equals(Values.TYPE_PARENT)) {

                listItems = new ArrayList<>();
                adapter = new Transport_Adapter(listItems, getApplicationContext());
                adapter.notifyDataSetChanged();
                recyclerView.setAdapter(adapter);
                transport_spinner.setVisibility(View.VISIBLE);
                parentStudentDataList = CommonCalls.getChildrenOfParentList(Transport.this);
                childrenUsernames= new ArrayList<>();
                for (ParentStudentData data : parentStudentDataList) {
                    childrenUsernames.add(data.getName());
                }

                transport_spinner.setVisibility(View.VISIBLE);
                ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter
                        (Transport.this, android.R.layout.simple_spinner_item, childrenUsernames);
                spinnerArrayAdapter.setDropDownViewResource(android.R.layout
                        .simple_spinner_dropdown_item);
                transport_spinner.setAdapter(spinnerArrayAdapter);
                transport_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        StudentID = parentStudentDataList.get(i).getStudentID();
                        System.out.println("Data : "+StudentID);
                        if(StudentID != null){
                            getPersonalTranportDetails(StudentID);
                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });

            }
             if(CommonCalls.getUserType(Transport.this).equals(Values.TYPE_STUDENT)){
                StudentID = loginResponse.getStudentID();
                 getPersonalTranportDetails(StudentID);
            }


        }
        return super.onOptionsItemSelected(item);
    }

    private void getPersonalTranportDetails(String StudentID) {

        Retrofit retrofit = RetrofitInitialize.getApiClient();
        ClientAPIs clientAPIs = retrofit.create(ClientAPIs.class);
        if(CommonCalls.getUserType(Transport.this).equals(Values.TYPE_PARENT)){
            ParentLoginResponse loginResponse = CommonCalls.getParentData(Transport.this);
            base = loginResponse.getUsername() + ":" + loginResponse.getPassword();
        }else if(CommonCalls.getUserType(Transport.this).equals(Values.TYPE_STUDENT)){
            LoginResponse loginResponse = CommonCalls.getUserData(Transport.this);
            base = loginResponse.getUsername() + ":" + loginResponse.getPassword();

        }
        String authHeader = "Basic " + Base64.encodeToString(base.getBytes(), Base64.NO_WRAP);
        Call<Transport_Model> call = clientAPIs.getPersonalTranportData(StudentID, authHeader);
        call.enqueue(new Callback<Transport_Model>() {
            @Override
            public void onResponse(Call<Transport_Model> call, Response<Transport_Model> response) {
                if (response.isSuccessful()) {

                    Transport_Model transport_model = response.body();
                    if (transport_model != null) {
                        listItems = new ArrayList<>();
                        progressDialog.dismiss();
                        listItems.add(transport_model);
                        recyclerView = findViewById(R.id.transport_recycler_view);
                        recyclerView.setHasFixedSize(true);
                        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                        adapter = new Transport_Adapter(listItems, getApplicationContext());
                        adapter.notifyDataSetChanged();
                        recyclerView.setAdapter(adapter);

                    } else {
                        progressDialog.dismiss();
                        Toast.makeText(Transport.this, Values.Error, Toast.LENGTH_SHORT).show();

                    }

                }
            }

            @Override
            public void onFailure(Call<Transport_Model> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(Transport.this, Values.SERVER_ERROR, Toast.LENGTH_SHORT).show();

            }
        });

    }


    public void getAllTransportDetail() {
        Retrofit retrofit = RetrofitInitialize.getApiClient();
        ClientAPIs clientAPIs = retrofit.create(ClientAPIs.class);
        if(CommonCalls.getUserType(Transport.this).equals(Values.TYPE_PARENT)){
            ParentLoginResponse loginResponse = CommonCalls.getParentData(Transport.this);
            base = loginResponse.getUsername() + ":" + loginResponse.getPassword();
        }else if(CommonCalls.getUserType(Transport.this).equals(Values.TYPE_STUDENT)){
            LoginResponse loginResponse = CommonCalls.getUserData(Transport.this);
            base = loginResponse.getUsername() + ":" + loginResponse.getPassword();
        }
        String authHeader = "Basic " + Base64.encodeToString(base.getBytes(), Base64.NO_WRAP);
        Call<TransportResponse_Model> call = clientAPIs.getTranportData(authHeader);
        call.enqueue(new Callback<TransportResponse_Model>() {
            @Override
            public void onResponse(Call<TransportResponse_Model> call, Response<TransportResponse_Model> response) {
                if (response.isSuccessful()) {

                    TransportResponse_Model transport_model = response.body();
                    if (transport_model != null) {
                        progressDialog.dismiss();
                        recyclerView = findViewById(R.id.transport_recycler_view);
                        recyclerView.setHasFixedSize(true);
                        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                        adapter = new Transport_Adapter(transport_model.getTransportData(), getApplicationContext());
                        adapter.notifyDataSetChanged();
                        recyclerView.setAdapter(adapter);

                    } else {
                        progressDialog.dismiss();
                        Toast.makeText(Transport.this, Values.Error, Toast.LENGTH_SHORT).show();

                    }

                }

            }

            @Override
            public void onFailure(Call<TransportResponse_Model> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(Transport.this, Values.SERVER_ERROR, Toast.LENGTH_SHORT).show();

            }
        });
    }





}
