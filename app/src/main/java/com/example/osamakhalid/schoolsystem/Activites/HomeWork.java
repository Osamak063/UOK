package com.example.osamakhalid.schoolsystem.Activites;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.widget.TextView;
import android.widget.Toast;

import com.example.osamakhalid.schoolsystem.Adapters.HomeWork_Adapter;
import com.example.osamakhalid.schoolsystem.BaseConnection.RetrofitInitialize;
import com.example.osamakhalid.schoolsystem.ConnectionInterface.ClientAPIs;
import com.example.osamakhalid.schoolsystem.Consts.Values;
import com.example.osamakhalid.schoolsystem.GlobalCalls.CommonCalls;
import com.example.osamakhalid.schoolsystem.Model.Homework_Model;
import com.example.osamakhalid.schoolsystem.Model.LoginResponse;
import com.example.osamakhalid.schoolsystem.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class HomeWork extends AppCompatActivity {

    public RecyclerView recyclerView;
    public RecyclerView.Adapter adapter;
    TextView date_view;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_work);
        date_view = findViewById(R.id.date_section);
        progressDialog = CommonCalls.createDialouge(HomeWork.this,"", Values.DIALOGUE_MSG);
        getHomeWork();



    }


    //Homework Function
    public void getHomeWork(){

        final Retrofit[] retrofit = {RetrofitInitialize.getApiClient()};
        ClientAPIs clientAPIs = retrofit[0].create(ClientAPIs.class);
        LoginResponse loginResponse = CommonCalls.getUserData(HomeWork.this);
        String base = loginResponse.getUsername() + ":" + loginResponse.getPassword();
        String authHeader = "Basic " + Base64.encodeToString(base.getBytes(), Base64.NO_WRAP);
        String date = CommonCalls.getCurrentDate();
        Call<Homework_Model> call = clientAPIs.getHomeWOrk("2018-02-14",authHeader);
        call.enqueue(new Callback<Homework_Model>() {
            @Override
            public void onResponse(Call<Homework_Model> call, Response<Homework_Model> response) {
                if(response.isSuccessful()){

                    Homework_Model homework_model = response.body();
                    if(homework_model != null){
                        date_view.setText(homework_model.getHomeworkDateData().get(0).getDate());
                        if(homework_model.getHomeworkDateData() != null){
                            progressDialog.dismiss();
                            recyclerView =  findViewById(R.id.homeworkview);
                            recyclerView.setHasFixedSize(true);
                            recyclerView.setLayoutManager(new LinearLayoutManager(HomeWork.this));

                            adapter = new HomeWork_Adapter(homework_model.getHomeworkDateData(),getApplicationContext());
                            recyclerView.setAdapter(adapter);
                            //Log.e("Response","Received!");
                        }


                    }
                }
            }

            @Override
            public void onFailure(Call<Homework_Model> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(HomeWork.this,Values.SERVER_ERROR,Toast.LENGTH_SHORT).show();
            }
        });

    }
}
