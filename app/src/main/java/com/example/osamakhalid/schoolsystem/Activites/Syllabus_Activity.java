package com.example.osamakhalid.schoolsystem.Activites;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
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

import com.example.osamakhalid.schoolsystem.Adapters.SyllabusDate_Adapter;
import com.example.osamakhalid.schoolsystem.BaseConnection.RetrofitInitialize;
import com.example.osamakhalid.schoolsystem.ConnectionInterface.ClientAPIs;
import com.example.osamakhalid.schoolsystem.Consts.Values;
import com.example.osamakhalid.schoolsystem.GlobalCalls.CommonCalls;
import com.example.osamakhalid.schoolsystem.Interfaces.ItemClickListenerDate;
import com.example.osamakhalid.schoolsystem.Model.LoginResponse;
import com.example.osamakhalid.schoolsystem.Model.SyllabusMultiple_Subject_Model;
import com.example.osamakhalid.schoolsystem.Model.SyllabusResponse_Model;
import com.example.osamakhalid.schoolsystem.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class Syllabus_Activity extends AppCompatActivity implements ItemClickListenerDate{

    Spinner month_spinner, year_spinner;
    ProgressDialog progressDialog;
    String month = null, year = null;
    public RecyclerView recyclerView;
    public SyllabusDate_Adapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_syllabus_);


        month_spinner = findViewById(R.id.month___spinner);
        ArrayAdapter<CharSequence> arrayAdapter = ArrayAdapter.createFromResource(this, R.array.months,
                android.R.layout.simple_spinner_item);
        month_spinner.setAdapter(arrayAdapter);
        month_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int i, long l) {
                if (parent.getItemAtPosition(i).toString() != null) {
                    month = CommonCalls.getMonth(parent.getItemAtPosition(i).toString());
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        year_spinner = findViewById(R.id.year___spinner);
        ArrayAdapter<CharSequence> yearAdapter = ArrayAdapter.createFromResource(this, R.array.years,
                android.R.layout.simple_spinner_item);
        year_spinner.setAdapter(yearAdapter);
        year_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int i, long l) {
                if (parent.getItemAtPosition(i).toString() != null) {
                    year = parent.getItemAtPosition(i).toString();

                    if (month != null && year != null) {
                        getSyllabusData(year + "-" + month);

                    }
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


    }


    public void getSyllabusData(String month_year) {

        Retrofit retrofit = RetrofitInitialize.getApiClient();
        ClientAPIs clientAPIs = retrofit.create(ClientAPIs.class);
        final LoginResponse loginResponse = CommonCalls.getUserData(Syllabus_Activity.this);
        String base = loginResponse.getUsername() + ":" + loginResponse.getPassword();
        String authHeader = "Basic " + Base64.encodeToString(base.getBytes(), Base64.NO_WRAP);
        Call<SyllabusResponse_Model> call = clientAPIs.getSyllabus(loginResponse.getUsername(),
                Values.LANGUAGE, month_year, authHeader);
        call.enqueue(new Callback<SyllabusResponse_Model>() {
            @Override
            public void onResponse(Call<SyllabusResponse_Model> call, Response<SyllabusResponse_Model> response) {
                if(response.isSuccessful()){

                    SyllabusResponse_Model syllabusResponse_model = response.body();
                    if(syllabusResponse_model != null){

                        recyclerView = findViewById(R.id.syllabus_recyclerview);
                        recyclerView.setHasFixedSize(true);
                        recyclerView.setLayoutManager(new LinearLayoutManager(Syllabus_Activity.this));
                        adapter = new SyllabusDate_Adapter(syllabusResponse_model.getSyllabusData(),getApplicationContext());
                        adapter.setItemClickListener(Syllabus_Activity.this);
                        recyclerView.setAdapter(adapter);
                        adapter.notifyDataSetChanged();

                    }else{
                        adapter.notifyDataSetChanged();
                        Toast.makeText(getApplicationContext(),Values.DATA_ERROR,Toast.LENGTH_SHORT).show();

                    }

                }

            }

            @Override
            public void onFailure(Call<SyllabusResponse_Model> call, Throwable t) {
                Toast.makeText(getApplicationContext(),Values.SERVER_ERROR,Toast.LENGTH_SHORT).show();
            }
        });


    }

    @Override
    public void onClick(View view, List<SyllabusMultiple_Subject_Model> galleryData_models) {
        Log.e("","Clicked!!!!");
        Intent mIntent = new Intent(Syllabus_Activity.this, SyllabusSubject_Activity.class);
        mIntent.putParcelableArrayListExtra("syllabus_data", (ArrayList<? extends Parcelable>) galleryData_models);
        startActivity(mIntent);
    }
}