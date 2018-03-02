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
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.osamakhalid.schoolsystem.Adapters.HomeWork_Adapter;
import com.example.osamakhalid.schoolsystem.BaseConnection.RetrofitInitialize;
import com.example.osamakhalid.schoolsystem.ConnectionInterface.ClientAPIs;
import com.example.osamakhalid.schoolsystem.Consts.Values;
import com.example.osamakhalid.schoolsystem.GlobalCalls.CommonCalls;
import com.example.osamakhalid.schoolsystem.Model.Homework_Data_Model;
import com.example.osamakhalid.schoolsystem.Model.Homework_Model;
import com.example.osamakhalid.schoolsystem.Model.LoginResponse;
import com.example.osamakhalid.schoolsystem.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class HomeWork extends AppCompatActivity {

    public RecyclerView recyclerView;
    public RecyclerView.Adapter adapter;
    public String day=null,month=null,year=null;
    ProgressDialog progressDialog;
    private List<Homework_Data_Model> listItem;
    Button check;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_work);
        fill_spinners();
      //  progressDialog = CommonCalls.createDialouge(HomeWork.this,"", Values.DIALOGUE_MSG);
        check = findViewById(R.id.checkHomeWork);
        check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressDialog = CommonCalls.createDialouge(HomeWork.this,"", Values.DIALOGUE_MSG);
                String date = year+"-"+month+"-"+day;
                getHomeWork(date);

            }
        });




    }

    public void fill_spinners(){

        //day Spinner
        Spinner d_spinner = findViewById(R.id.day_spinner);
        ArrayAdapter<CharSequence> arrayAdapter = ArrayAdapter.createFromResource(this, R.array.days,
                android.R.layout.simple_spinner_item);
        d_spinner.setAdapter(arrayAdapter);
        d_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int i, long l) {
                day = parent.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        //month Spinner
        Spinner m_spinner = findViewById(R.id.month_spinner);
        ArrayAdapter<CharSequence> arrayAdapter1 = ArrayAdapter.createFromResource(this, R.array.months,
                android.R.layout.simple_spinner_item);
        m_spinner.setAdapter(arrayAdapter1);
        m_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int i, long l) {
                month = CommonCalls.getMonth(parent.getItemAtPosition(i).toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        //yeat Spinner
        Spinner y_spinner = findViewById(R.id.year__spinner);
        ArrayAdapter<CharSequence> arrayAdapter11 = ArrayAdapter.createFromResource(this, R.array.years,
                android.R.layout.simple_spinner_item);
        y_spinner.setAdapter(arrayAdapter11);
        y_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int i, long l) {
              //  progressDialog = CommonCalls.createDialouge(HomeWork.this,"", Values.DIALOGUE_MSG);
                year = parent.getItemAtPosition(i).toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


    }





    //Homework Function
    public void getHomeWork(String new_date){

        final Retrofit[] retrofit = {RetrofitInitialize.getApiClient()};
        ClientAPIs clientAPIs = retrofit[0].create(ClientAPIs.class);
        LoginResponse loginResponse = CommonCalls.getUserData(HomeWork.this);
        String base = loginResponse.getUsername() + ":" + loginResponse.getPassword();
        String authHeader = "Basic " + Base64.encodeToString(base.getBytes(), Base64.NO_WRAP);
        if(new_date == null){
            new_date = CommonCalls.getCurrentDate();
        }
        Log.e("Parameter",new_date+"++"+loginResponse.getUsername());
        Call<Homework_Model> call = clientAPIs.getHomeWOrk(new_date,loginResponse.getUsername(),authHeader);
        call.enqueue(new Callback<Homework_Model>() {
            @Override
            public void onResponse(Call<Homework_Model> call, Response<Homework_Model> response) {
                if(response.isSuccessful()){
                    progressDialog.dismiss();
                    Homework_Model homework_model = response.body();
                    if(homework_model != null){

                      if(homework_model.getHomeworkDateData() != null){
                            listItem = homework_model.getHomeworkDateData();
                            recyclerView =  findViewById(R.id.homeworkview);
                            recyclerView.setHasFixedSize(true);
                            recyclerView.setLayoutManager(new LinearLayoutManager(HomeWork.this));
                            adapter = new HomeWork_Adapter(listItem,HomeWork.this);
                            recyclerView.setAdapter(adapter);

                        }else{
                                progressDialog.dismiss();
                                listItem.clear();
                                adapter.notifyDataSetChanged();
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
