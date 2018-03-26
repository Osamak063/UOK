package com.example.osamakhalid.schoolsystem.Activites;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.osamakhalid.schoolsystem.Adapters.NewsAndEvents_Adapter;
import com.example.osamakhalid.schoolsystem.BaseConnection.RetrofitInitialize;
import com.example.osamakhalid.schoolsystem.ConnectionInterface.ClientAPIs;
import com.example.osamakhalid.schoolsystem.Consts.Values;
import com.example.osamakhalid.schoolsystem.GlobalCalls.CommonCalls;
import com.example.osamakhalid.schoolsystem.Model.ItemClickListener;
import com.example.osamakhalid.schoolsystem.Model.LoginResponse;
import com.example.osamakhalid.schoolsystem.Model.NewsAndEventsResponse;
import com.example.osamakhalid.schoolsystem.Model.NewsAndEventsResponseList;
import com.example.osamakhalid.schoolsystem.Model.ParentLoginResponse;
import com.example.osamakhalid.schoolsystem.R;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class NewsAndEvents extends AppCompatActivity implements ItemClickListener {
    public RecyclerView recyclerView;
    public NewsAndEvents_Adapter adapter;
    public List<NewsAndEventsResponse> listItems;
    private SharedPreferences mPrefs;
    private Gson gson;
    private Retrofit retrofit;
    private ClientAPIs clientAPIs;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_and_events);
        listItems = new ArrayList<>();
        progressDialog = CommonCalls.createDialouge(this,"", Values.DIALOGUE_MSG);
        recyclerView = findViewById(R.id.news_recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        getData();
        adapter = new NewsAndEvents_Adapter(listItems, this);
        adapter.setItemClickListener(this);
        recyclerView.setAdapter(adapter);
    }

    public void getData() {
        retrofit = RetrofitInitialize.getApiClient();
        clientAPIs = retrofit.create(ClientAPIs.class);
        String base = null;
        if (CommonCalls.getUserType(NewsAndEvents.this).equals(Values.TYPE_PARENT)) {
            ParentLoginResponse loginResponse = CommonCalls.getParentData(NewsAndEvents.this);
            base = loginResponse.getUsername() + ":" + loginResponse.getPassword();
        } else if (CommonCalls.getUserType(NewsAndEvents.this).equals(Values.TYPE_STUDENT)) {
            LoginResponse loginResponse = CommonCalls.getUserData(NewsAndEvents.this);
            base = loginResponse.getUsername() + ":" + loginResponse.getPassword();

        }
        String authHeader = "Basic " + Base64.encodeToString(base.getBytes(), Base64.NO_WRAP);
        Call<NewsAndEventsResponseList> call = clientAPIs.getNewsAndEvents(authHeader);
        call.enqueue(new Callback<NewsAndEventsResponseList>() {
            @Override
            public void onResponse(Call<NewsAndEventsResponseList> call, Response<NewsAndEventsResponseList> response) {
                if (response.isSuccessful()) {
                    NewsAndEventsResponseList newsAndEvents = response.body();
                    if (newsAndEvents != null && newsAndEvents.getNewsAndEventsLists()!=null) {
                        progressDialog.dismiss();
                        listItems.addAll(newsAndEvents.getNewsAndEventsLists());
                        adapter.notifyDataSetChanged();
                    }
                    else {
                        progressDialog.dismiss();
                        Toast.makeText(NewsAndEvents.this, "News and events not available yet.", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<NewsAndEventsResponseList> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(NewsAndEvents.this, Values.SERVER_ERROR, Toast.LENGTH_SHORT).show();
            }
        });
    }


    @Override
    public void onClick(View view, String name) {
        Log.e("NewsActivityEventID",name);
        Intent i = new Intent(NewsAndEvents.this,NewsandEventDetail.class);
        i.putExtra("eventID",name);
        startActivity(i);

    }
}
