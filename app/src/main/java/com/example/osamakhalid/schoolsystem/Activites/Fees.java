package com.example.osamakhalid.schoolsystem.Activites;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.osamakhalid.schoolsystem.Adapters.FeesAdapter;
import com.example.osamakhalid.schoolsystem.Adapters.NewsAndEvents_Adapter;
import com.example.osamakhalid.schoolsystem.Adapters.alert_adapter;
import com.example.osamakhalid.schoolsystem.BaseConnection.RetrofitInitialize;
import com.example.osamakhalid.schoolsystem.ConnectionInterface.ClientAPIs;
import com.example.osamakhalid.schoolsystem.Consts.Values;
import com.example.osamakhalid.schoolsystem.GlobalCalls.CommonCalls;
import com.example.osamakhalid.schoolsystem.Model.Alert_Model;
import com.example.osamakhalid.schoolsystem.Model.ExamResult_Data;
import com.example.osamakhalid.schoolsystem.Model.FeesResponse;
import com.example.osamakhalid.schoolsystem.Model.FeesResponseList;
import com.example.osamakhalid.schoolsystem.Model.HolidayResponse;
import com.example.osamakhalid.schoolsystem.Model.HolidayResponseList;
import com.example.osamakhalid.schoolsystem.Model.LoginResponse;
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
    LoginResponse userData;
    ProgressDialog progressDialog;
    LinearLayoutManager manager;
    boolean isScrolling = false;
    int currentItems, totalItems, scrollOutItems, offset = 0;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fees);
        listItems = new ArrayList<>();
        manager = new LinearLayoutManager(this);
        recyclerView = (RecyclerView) findViewById(R.id.fees_and_invoice_recycler_view);
        progressBar = findViewById(R.id.progressbar_fees);
        progressDialog = CommonCalls.createDialouge(Fees.this, "", Values.DIALOGUE_MSG);
        recyclerView.setHasFixedSize(true);
        userData = CommonCalls.getUserData(Fees.this);
        String base = userData.getUsername() + ":" + userData.getPassword();
        final String authHeader = "Basic " + Base64.encodeToString(base.getBytes(), Base64.NO_WRAP);
        getData(authHeader, offset);
        adapter = new FeesAdapter(listItems, getApplicationContext());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(manager);
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
                    getData(authHeader, offset);
                }
            }
        });
    }

    public void getData(String authHeader, int offset) {
        retrofit = RetrofitInitialize.getApiClient();
        clientAPIs = retrofit.create(ClientAPIs.class);
        Call<FeesResponseList> call = clientAPIs.getFeesAndInvoice(userData.getUsername(), "english", 10, offset, authHeader);
        call.enqueue(new Callback<FeesResponseList>() {
            @Override
            public void onResponse(Call<FeesResponseList> call, Response<FeesResponseList> response) {
                System.out.println("logg coming");
                if (response.isSuccessful()) {
                    FeesResponseList feesList = response.body();
                    if (feesList != null && feesList.getFeeData() != null) {
                        System.out.println("logg feeslist=" + feesList.getCount());
                        progressDialog.dismiss();
                        listItems.addAll(feesList.getFeeData());
                        adapter.notifyDataSetChanged();
                        progressBar.setVisibility(View.GONE);
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
