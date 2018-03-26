package com.example.osamakhalid.schoolsystem.Activites;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.widget.Toast;

import com.example.osamakhalid.schoolsystem.Adapters.NewsAndEvents_Adapter;
import com.example.osamakhalid.schoolsystem.BaseConnection.RetrofitInitialize;
import com.example.osamakhalid.schoolsystem.ConnectionInterface.ClientAPIs;
import com.example.osamakhalid.schoolsystem.Consts.Values;
import com.example.osamakhalid.schoolsystem.GlobalCalls.CommonCalls;
import com.example.osamakhalid.schoolsystem.Model.HolidayResponse;
import com.example.osamakhalid.schoolsystem.Model.HolidayResponseList;
import com.example.osamakhalid.schoolsystem.Model.LoginResponse;
import com.example.osamakhalid.schoolsystem.Model.ParentLoginResponse;
import com.example.osamakhalid.schoolsystem.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class Alert extends AppCompatActivity {

    public RecyclerView recyclerView;
    public RecyclerView.Adapter adapter;
    public List<HolidayResponse> listItems;
    private Retrofit retrofit;
    private ClientAPIs clientAPIs;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alert);
        listItems = new ArrayList<>();
        recyclerView = findViewById(R.id.alert);
        progressDialog = CommonCalls.createDialouge(this, "", Values.DIALOGUE_MSG);

        //setting up recyclerview
        recyclerView = findViewById(R.id.alert);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        getData();
        adapter = new NewsAndEvents_Adapter(listItems, getApplicationContext(), "holiday");
        recyclerView.setAdapter(adapter);
    }

    public void getData() {
        retrofit = RetrofitInitialize.getApiClient();
        clientAPIs = retrofit.create(ClientAPIs.class);
        String base = null;
        if (CommonCalls.getUserType(Alert.this).equals(Values.TYPE_PARENT)) {
            ParentLoginResponse loginResponse = CommonCalls.getParentData(Alert.this);
            base = loginResponse.getUsername() + ":" + loginResponse.getPassword();
        } else if (CommonCalls.getUserType(Alert.this).equals(Values.TYPE_STUDENT)) {
            LoginResponse loginResponse = CommonCalls.getUserData(Alert.this);
            base = loginResponse.getUsername() + ":" + loginResponse.getPassword();

        }
        String authHeader = "Basic " + Base64.encodeToString(base.getBytes(), Base64.NO_WRAP);
        Call<HolidayResponseList> call = clientAPIs.getHolidays("english", authHeader);
        call.enqueue(new Callback<HolidayResponseList>() {
            @Override
            public void onResponse(Call<HolidayResponseList> call, Response<HolidayResponseList> response) {
                if (response.isSuccessful()) {
                    HolidayResponseList holidayList = response.body();
                    if (holidayList != null && holidayList.getHolidayList() != null) {
                        progressDialog.dismiss();
                        listItems.addAll(holidayList.getHolidayList());
                        adapter.notifyDataSetChanged();
                    } else {
                        progressDialog.dismiss();
                        Toast.makeText(Alert.this, Values.DATA_ERROR, Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<HolidayResponseList> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(Alert.this, Values.DIALOGUE_MSG, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
