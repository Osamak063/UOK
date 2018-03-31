package com.example.osamakhalid.schoolsystem.Activites;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;
import android.support.v7.widget.Toolbar;

import com.example.osamakhalid.schoolsystem.Adapters.FeesAdapter;
import com.example.osamakhalid.schoolsystem.BaseConnection.RetrofitInitialize;
import com.example.osamakhalid.schoolsystem.ConnectionInterface.ClientAPIs;
import com.example.osamakhalid.schoolsystem.Consts.Values;
import com.example.osamakhalid.schoolsystem.GlobalCalls.CommonCalls;
import com.example.osamakhalid.schoolsystem.Model.FeesResponse;
import com.example.osamakhalid.schoolsystem.Model.FeesResponseList;
import com.example.osamakhalid.schoolsystem.Model.LoginResponse;
import com.example.osamakhalid.schoolsystem.Model.ParentLoginResponse;
import com.example.osamakhalid.schoolsystem.Model.ParentModels.ParentStudentData;
import com.example.osamakhalid.schoolsystem.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class Fees extends AppCompatActivity {

    public RecyclerView recyclerView;
    public RecyclerView.Adapter adapter;
    public List<FeesResponse> listItems;
    private Retrofit retrofit;
    private ClientAPIs clientAPIs;
    String username;
    ProgressDialog progressDialog;
    LinearLayoutManager manager;
    boolean isScrolling = false;
    int currentItems, totalItems, scrollOutItems, offset = 0;
    ProgressBar progressBar;
    private String base;
    List<ParentStudentData> parentStudentDataList;
    List<String> childrenUsernames;
    private Spinner Fees_Spinner;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fees);
        //Setting up Toolbar
        toolbar = findViewById(R.id.toolBar);
        toolbar.setTitle("Fees & Invoice");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Fees.this, DashboardActivity.class));
            }
        });
        listItems = new ArrayList<>();
        manager = new LinearLayoutManager(this);
        recyclerView = (RecyclerView) findViewById(R.id.fees_and_invoice_recycler_view);
        progressBar = findViewById(R.id.progressbar_fees);
        progressDialog = CommonCalls.createDialouge(Fees.this, "", Values.DIALOGUE_MSG);

        //   userData = CommonCalls.getUserData(Fees.this);
        //  String base = userData.getUsername() + ":" + userData.getPassword();
        Fees_Spinner = findViewById(R.id.Fees_spinner);


        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(manager);
// For Parent APIs
        if (CommonCalls.getUserType(this).equals(Values.TYPE_PARENT)) {

            parentStudentDataList = CommonCalls.getChildrenOfParentList(Fees.this);
            childrenUsernames = new ArrayList<>();
            for (ParentStudentData data : parentStudentDataList) {
                childrenUsernames.add(data.getName());
            }

            final int offset = 0;
            Fees_Spinner.setVisibility(View.VISIBLE);
            ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter
                    (Fees.this, android.R.layout.simple_spinner_item, childrenUsernames);
            spinnerArrayAdapter.setDropDownViewResource(android.R.layout
                    .simple_spinner_dropdown_item);
            Fees_Spinner.setAdapter(spinnerArrayAdapter);
            Fees_Spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    username = parentStudentDataList.get(i).getUsername();
                    listItems.clear();
                    if (username != null) {
                        getData(offset);
                    }


                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });

        }
        if (CommonCalls.getUserType(this).equals(Values.TYPE_STUDENT)) {
            getData(offset);


        }
        adapter = new FeesAdapter(listItems, getApplicationContext());
        recyclerView.setAdapter(adapter);


        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
                    isScrolling = true;
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                currentItems = manager.getChildCount();
                totalItems = manager.getItemCount();
                scrollOutItems = manager.findFirstVisibleItemPosition();
                offset = totalItems - 1;
                if (isScrolling && (currentItems + scrollOutItems == totalItems)) {
                    isScrolling = false;
                    progressBar.setVisibility(View.VISIBLE);
                    if(offset>0){
                    getData(offset);}
                }
            }
        });
    }

    public void getData(int offset) {
        retrofit = RetrofitInitialize.getApiClient();
        clientAPIs = retrofit.create(ClientAPIs.class);
        if (CommonCalls.getUserType(Fees.this).equals(Values.TYPE_PARENT)) {
            ParentLoginResponse loginResponse = CommonCalls.getParentData(Fees.this);
            base = loginResponse.getUsername() + ":" + loginResponse.getPassword();
        } else if (CommonCalls.getUserType(Fees.this).equals(Values.TYPE_STUDENT)) {
            LoginResponse loginResponse = CommonCalls.getUserData(Fees.this);
            base = loginResponse.getUsername() + ":" + loginResponse.getPassword();
            username = loginResponse.getUsername();
        }
        String authHeader = "Basic " + Base64.encodeToString(base.getBytes(), Base64.NO_WRAP);
        Call<FeesResponseList> call = clientAPIs.getFeesAndInvoice(username, Values.LANGUAGE, 10, offset, authHeader);
        call.enqueue(new Callback<FeesResponseList>() {
            @Override
            public void onResponse(Call<FeesResponseList> call, Response<FeesResponseList> response) {
                if (response.isSuccessful()) {
                    FeesResponseList feesList = response.body();
                    if (feesList != null ) {
                        if(feesList.getFeeData() != null){
                            progressDialog.dismiss();
                            listItems.addAll(feesList.getFeeData());
                            adapter.notifyDataSetChanged();
                            progressBar.setVisibility(View.GONE);
                        }else{
                            progressDialog.dismiss();
                            adapter.notifyDataSetChanged();
                            progressBar.setVisibility(View.GONE);
                        }

                    } else {
                        progressDialog.dismiss();
                        Toast.makeText(Fees.this, "No more fees and invoice.", Toast.LENGTH_SHORT).show();
                        progressBar.setVisibility(View.GONE);
                    }
                }
            }

            @Override
            public void onFailure(Call<FeesResponseList> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(Fees.this, Values.DIALOGUE_MSG, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
