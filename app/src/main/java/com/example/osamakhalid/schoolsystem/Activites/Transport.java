package com.example.osamakhalid.schoolsystem.Activites;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.osamakhalid.schoolsystem.Adapters.Transport_Adapter;
import com.example.osamakhalid.schoolsystem.BaseConnection.RetrofitInitialize;
import com.example.osamakhalid.schoolsystem.ConnectionInterface.ClientAPIs;
import com.example.osamakhalid.schoolsystem.Consts.Values;
import com.example.osamakhalid.schoolsystem.GlobalCalls.CommonCalls;
import com.example.osamakhalid.schoolsystem.Model.LoginResponse;
import com.example.osamakhalid.schoolsystem.Model.TransportResponse_Model;
import com.example.osamakhalid.schoolsystem.Model.Transport_Model;
import com.example.osamakhalid.schoolsystem.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class Transport extends AppCompatActivity {
    public RecyclerView recyclerView;
    public RecyclerView.Adapter adapter;
    public List<Transport_Model> listItems;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transport);
        listItems = new ArrayList<>();
        progressDialog = CommonCalls.createDialouge(this,"",Values.DIALOGUE_MSG);
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
        if (id == R.id.personal_transport) {
            progressDialog = CommonCalls.createDialouge(this,"",Values.DIALOGUE_MSG);
            getPersonalTranportDetails();

        }
        return super.onOptionsItemSelected(item);
    }

    private void getPersonalTranportDetails() {

        Retrofit retrofit = RetrofitInitialize.getApiClient();
        ClientAPIs clientAPIs = retrofit.create(ClientAPIs.class);
        LoginResponse loginResponse = CommonCalls.getUserData(Transport.this);
        String base = loginResponse.getUsername() + ":" + loginResponse.getPassword();
        String authHeader = "Basic " + Base64.encodeToString(base.getBytes(), Base64.NO_WRAP);
        Call<Transport_Model> call = clientAPIs.getPersonalTranportData(loginResponse.getStudentID(),authHeader);
        call.enqueue(new Callback<Transport_Model>() {
            @Override
            public void onResponse(Call<Transport_Model> call, Response<Transport_Model> response) {
                if(response.isSuccessful()){

                    Transport_Model transport_model = response.body();
                    if(transport_model != null){
                        listItems = new ArrayList<>();
                        progressDialog.dismiss();
                        listItems.add(transport_model);
                        recyclerView =  findViewById(R.id.transport_recycler_view);
                        recyclerView.setHasFixedSize(true);
                        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                        adapter = new Transport_Adapter(listItems,getApplicationContext());
                        recyclerView.setAdapter(adapter);

                    }else{
                        Toast.makeText(Transport.this, Values.Error,Toast.LENGTH_SHORT).show();

                    }

                }
            }

            @Override
            public void onFailure(Call<Transport_Model> call, Throwable t) {
                progressDialog.dismiss();
            }
        });

    }


    public void getAllTransportDetail(){

        Retrofit retrofit = RetrofitInitialize.getApiClient();
        ClientAPIs clientAPIs = retrofit.create(ClientAPIs.class);
        LoginResponse loginResponse = CommonCalls.getUserData(Transport.this);
        String base = loginResponse.getUsername() + ":" + loginResponse.getPassword();
        String authHeader = "Basic " + Base64.encodeToString(base.getBytes(), Base64.NO_WRAP);
        Call<TransportResponse_Model> call = clientAPIs.getTranportData(loginResponse.getUsertype(), authHeader);
        call.enqueue(new Callback<TransportResponse_Model>() {
            @Override
            public void onResponse(Call<TransportResponse_Model> call, Response<TransportResponse_Model> response) {
                if(response.isSuccessful()){

                    TransportResponse_Model transport_model = response.body();
                    if(transport_model != null){
                        progressDialog.dismiss();
                        recyclerView =  findViewById(R.id.transport_recycler_view);
                        recyclerView.setHasFixedSize(true);
                        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

                        adapter = new Transport_Adapter(transport_model.getTransportData(),getApplicationContext());
                        recyclerView.setAdapter(adapter);

                    }else{
                        Toast.makeText(Transport.this, Values.Error,Toast.LENGTH_SHORT).show();

                    }

                }

            }

            @Override
            public void onFailure(Call<TransportResponse_Model> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(Transport.this, Values.SERVER_ERROR,Toast.LENGTH_SHORT).show();

            }
        });
    }


}
