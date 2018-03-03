package com.example.osamakhalid.schoolsystem.Activites;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.widget.Toast;

import com.example.osamakhalid.schoolsystem.Adapters.LeavesAdapter;
import com.example.osamakhalid.schoolsystem.Adapters.NewsAndEvents_Adapter;
import com.example.osamakhalid.schoolsystem.BaseConnection.RetrofitInitialize;
import com.example.osamakhalid.schoolsystem.ConnectionInterface.ClientAPIs;
import com.example.osamakhalid.schoolsystem.Consts.Values;
import com.example.osamakhalid.schoolsystem.GlobalCalls.CommonCalls;
import com.example.osamakhalid.schoolsystem.Model.HolidayResponse;
import com.example.osamakhalid.schoolsystem.Model.HolidayResponseList;
import com.example.osamakhalid.schoolsystem.Model.LeavesResponse;
import com.example.osamakhalid.schoolsystem.Model.LeavesResponseList;
import com.example.osamakhalid.schoolsystem.Model.LoginResponse;
import com.example.osamakhalid.schoolsystem.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class Leaves extends AppCompatActivity {
    public RecyclerView recyclerView;
    public RecyclerView.Adapter adapter;
    public List<LeavesResponse> listItems;
    private Retrofit retrofit;
    private ClientAPIs clientAPIs;
    LoginResponse userData;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leaves);
        listItems = new ArrayList<>();
        progressDialog = CommonCalls.createDialouge(this, "", Values.DIALOGUE_MSG);
        recyclerView = findViewById(R.id.leaves_recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        userData = CommonCalls.getUserData(Leaves.this);
        String base = userData.getUsername() + ":" + userData.getPassword();
        String authHeader = "Basic " + Base64.encodeToString(base.getBytes(), Base64.NO_WRAP);
        getData(authHeader);
        adapter = new LeavesAdapter(listItems, getApplicationContext());
        recyclerView.setAdapter(adapter);
    }

    public void getData(String authHeader) {
        retrofit = RetrofitInitialize.getApiClient();
        clientAPIs = retrofit.create(ClientAPIs.class);
        Call<LeavesResponseList> call = clientAPIs.getLeaves(userData.getUsername(), userData.getUsertype(), authHeader);
        call.enqueue(new Callback<LeavesResponseList>() {
            @Override
            public void onResponse(Call<LeavesResponseList> call, Response<LeavesResponseList> response) {
                if (response.isSuccessful()) {
                    LeavesResponseList leavesList = response.body();
                    if (leavesList != null && leavesList.getLeavesData() != null) {
                        progressDialog.dismiss();
                        listItems.addAll(leavesList.getLeavesData());
                        adapter.notifyDataSetChanged();
                    } else {
                        progressDialog.dismiss();
                        Toast.makeText(Leaves.this, "Leaves not available yet.", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<LeavesResponseList> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(Leaves.this, "Sorry something went wrong.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
