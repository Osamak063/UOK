package com.example.osamakhalid.schoolsystem.Activites;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.osamakhalid.schoolsystem.Adapters.AllBooks_Adapter;
import com.example.osamakhalid.schoolsystem.BaseConnection.RetrofitInitialize;
import com.example.osamakhalid.schoolsystem.ConnectionInterface.ClientAPIs;
import com.example.osamakhalid.schoolsystem.Consts.Values;
import com.example.osamakhalid.schoolsystem.GlobalCalls.CommonCalls;
import com.example.osamakhalid.schoolsystem.Model.AllBooks_Model;
import com.example.osamakhalid.schoolsystem.Model.LoginResponse;
import com.example.osamakhalid.schoolsystem.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class Initiate_Library extends AppCompatActivity {

    ProgressDialog progress_dialouge;
    public RecyclerView recyclerView;
    public RecyclerView.Adapter adapter;
    Button issued_Button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_initiate__library);

        progress_dialouge = CommonCalls.createDialouge(this,"",Values.DIALOGUE_MSG);

        getAllBooks();

        issued_Button = findViewById(R.id.issued_books);
        issued_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Initiate_Library.this,Library.class));
            }
        });

    }


    public void getAllBooks(){

        Retrofit retrofit = RetrofitInitialize.getApiClient();
        ClientAPIs clientAPIs = retrofit.create(ClientAPIs.class);
        LoginResponse loginResponse = CommonCalls.getUserData(Initiate_Library.this);
        String base = loginResponse.getUsername() + ":" + loginResponse.getPassword();
        String authHeader = "Basic " + Base64.encodeToString(base.getBytes(), Base64.NO_WRAP);
        Call<AllBooks_Model> call = clientAPIs.getAllBooks(authHeader);
        call.enqueue(new Callback<AllBooks_Model>() {
            @Override
            public void onResponse(Call<AllBooks_Model> call, Response<AllBooks_Model> response) {

                if(response.isSuccessful()){

                    AllBooks_Model allBooks_model = response.body();

                    if(allBooks_model != null){

                        if(allBooks_model.getBooksData() != null){
                            progress_dialouge.dismiss();
                            recyclerView = findViewById(R.id.all_availble_books);
                            recyclerView.setHasFixedSize(true);
                            recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                            adapter = new AllBooks_Adapter(allBooks_model.getBooksData(), getApplicationContext());
                            recyclerView.setAdapter(adapter);
                            adapter.notifyDataSetChanged();

                        }else{
                            progress_dialouge.dismiss();
                            adapter.notifyDataSetChanged();
                            Toast.makeText(Initiate_Library.this,Values.DATA_ERROR,Toast.LENGTH_SHORT).show();

                        }


                    }


                }
            }

            @Override
            public void onFailure(Call<AllBooks_Model> call, Throwable t) {
                progress_dialouge.dismiss();
                Toast.makeText(Initiate_Library.this, Values.SERVER_ERROR,Toast.LENGTH_SHORT).show();
            }
        });

    }
}
