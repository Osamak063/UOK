package com.example.osamakhalid.schoolsystem.Activites;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.widget.Toast;

import com.example.osamakhalid.schoolsystem.Adapters.NewsAndEvents_Adapter;
import com.example.osamakhalid.schoolsystem.BaseConnection.RetrofitInitialize;
import com.example.osamakhalid.schoolsystem.ConnectionInterface.ClientAPIs;
import com.example.osamakhalid.schoolsystem.GlobalCalls.CommonCalls;
import com.example.osamakhalid.schoolsystem.Model.Alert_Model;
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

public class Alert extends AppCompatActivity {

    public RecyclerView recyclerView;
    public RecyclerView.Adapter adapter;
    public List<HolidayResponse> listItems;
    private Retrofit retrofit;
    private ClientAPIs clientAPIs;
    LoginResponse userData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alert);
        listItems = new ArrayList<>();
        recyclerView = findViewById(R.id.alert);


        //setting up recyclerview
        recyclerView = findViewById(R.id.alert);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        userData = CommonCalls.getUserData(Alert.this);
        String base = userData.getUsername() + ":" + userData.getPassword();
        String authHeader = "Basic " + Base64.encodeToString(base.getBytes(), Base64.NO_WRAP);
        getData(authHeader);
        adapter = new NewsAndEvents_Adapter(listItems, getApplicationContext(), "holiday");
        recyclerView.setAdapter(adapter);
    }

    public void getData(String authHeader) {
        retrofit = RetrofitInitialize.getApiClient();
        clientAPIs = retrofit.create(ClientAPIs.class);
        Call<HolidayResponseList> call = clientAPIs.getHolidays(userData.getUsertype(), "english", authHeader);
        call.enqueue(new Callback<HolidayResponseList>() {
            @Override
            public void onResponse(Call<HolidayResponseList> call, Response<HolidayResponseList> response) {
                if (response.isSuccessful()) {
                    HolidayResponseList holidayList = response.body();
                    if (holidayList != null && holidayList.getHolidayList() != null) {
                        listItems.addAll(holidayList.getHolidayList());
                        adapter.notifyDataSetChanged();
                    } else {
                        Toast.makeText(Alert.this, "Holiday alert not available yet.", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<HolidayResponseList> call, Throwable t) {
                Toast.makeText(Alert.this, "Sorry something went wrong.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
