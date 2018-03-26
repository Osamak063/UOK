package com.example.osamakhalid.schoolsystem.Activites;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.osamakhalid.schoolsystem.Adapters.Issued_Book_Adapter;
import com.example.osamakhalid.schoolsystem.BaseConnection.RetrofitInitialize;
import com.example.osamakhalid.schoolsystem.ConnectionInterface.ClientAPIs;
import com.example.osamakhalid.schoolsystem.Consts.Values;
import com.example.osamakhalid.schoolsystem.GlobalCalls.CommonCalls;
import com.example.osamakhalid.schoolsystem.Model.Libray_Model;
import com.example.osamakhalid.schoolsystem.Model.LoginResponse;
import com.example.osamakhalid.schoolsystem.Model.ParentLoginResponse;
import com.example.osamakhalid.schoolsystem.Model.ParentStudentData;
import com.example.osamakhalid.schoolsystem.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class Library extends AppCompatActivity {

    TextView library_id,library_fees,library_joiningdate;
    ProgressDialog progress_dialouge;
    public RecyclerView recyclerView;
    public RecyclerView.Adapter adapter;
    private String username;
    List<ParentStudentData> parentStudentDataList;
    List<String> childrenUsernames;
    private Spinner library_spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_library);

        //setting up toolbar
        Toolbar toolbar =  findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        progress_dialouge = CommonCalls.createDialouge(this,"",Values.DIALOGUE_MSG);
        library_id = findViewById(R.id.library_id);
        library_fees = findViewById(R.id.library_fee);
        library_joiningdate = findViewById(R.id.library_Jdate);
        library_spinner = findViewById(R.id.library_spinner);

        parentStudentDataList = CommonCalls.getChildrenOfParentList(Library.this);
        childrenUsernames = new ArrayList<>();
        for (ParentStudentData data : parentStudentDataList) {
            childrenUsernames.add(data.getName());
        }
        if (CommonCalls.getUserType(this).equals(Values.TYPE_PARENT)) {

            final int offset = 0;
            library_spinner.setVisibility(View.VISIBLE);
            ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter
                    (Library.this, android.R.layout.simple_spinner_item, childrenUsernames);
            spinnerArrayAdapter.setDropDownViewResource(android.R.layout
                    .simple_spinner_dropdown_item);
            library_spinner.setAdapter(spinnerArrayAdapter);
            library_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    username = parentStudentDataList.get(i).getUsername();
                    if ((username!= null)){
                        getLibraryData();
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });

        }else if(CommonCalls.getUserType(Library.this).equals(Values.TYPE_STUDENT)){
            getLibraryData();
        }
        //Loding Data in View


    }


    public void getLibraryData(){

        Retrofit retrofit = RetrofitInitialize.getApiClient();
        ClientAPIs clientAPIs = retrofit.create(ClientAPIs.class);
        String base = null;
        if (CommonCalls.getUserType(Library.this).equals(Values.TYPE_PARENT)) {
            ParentLoginResponse loginResponse = CommonCalls.getParentData(Library.this);
            base = loginResponse.getUsername() + ":" + loginResponse.getPassword();
        } else if (CommonCalls.getUserType(Library.this).equals(Values.TYPE_STUDENT)) {
            LoginResponse loginResponse = CommonCalls.getUserData(Library.this);
            base = loginResponse.getUsername() + ":" + loginResponse.getPassword();
            username = loginResponse.getUsername();

        }
        String authHeader = "Basic " + Base64.encodeToString(base.getBytes(), Base64.NO_WRAP);
        Call<Libray_Model> call = clientAPIs.getLibraryInfo(username,authHeader);
        call.enqueue(new Callback<Libray_Model>() {
            @Override
            public void onResponse(Call<Libray_Model> call, Response<Libray_Model> response) {
                if(response.isSuccessful()){

                    Libray_Model libray_model = response.body();
                    if(libray_model != null){

                        library_id.setText("Library ID :         "+libray_model.getLibraryId());
                        library_fees.setText("Library Fees :      "+libray_model.getLibraryFee());
                        library_joiningdate.setText("Joining Date :      "+libray_model.getJoiningDate());

                        if(libray_model.getBooks()!= null){
                            progress_dialouge.dismiss();
                            recyclerView = findViewById(R.id.library_books_issued);
                            recyclerView.setHasFixedSize(true);
                            recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                            adapter = new Issued_Book_Adapter(libray_model.getBooks(), getApplicationContext());
                            recyclerView.setAdapter(adapter);
                            adapter.notifyDataSetChanged();
                        }else{
                            progress_dialouge.dismiss();
                            recyclerView = findViewById(R.id.library_books_issued);
                            recyclerView.setHasFixedSize(true);
                            recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                            adapter = new Issued_Book_Adapter(libray_model.getBooks(), getApplicationContext());
                            recyclerView.setAdapter(adapter);
                            adapter.notifyDataSetChanged();
                            Toast.makeText(Library.this,Values.DATA_ERROR,Toast.LENGTH_SHORT).show();
                        }


                    }

                }

            }

            @Override
            public void onFailure(Call<Libray_Model> call, Throwable t) {
                progress_dialouge.dismiss();
                Toast.makeText(Library.this, Values.SERVER_ERROR, Toast.LENGTH_SHORT).show();
            }
        });

    }


}
