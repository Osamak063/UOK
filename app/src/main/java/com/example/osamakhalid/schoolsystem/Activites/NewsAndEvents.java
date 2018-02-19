package com.example.osamakhalid.schoolsystem.Activites;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;

import com.example.osamakhalid.schoolsystem.Adapters.NewsAndEvents_Adapter;
import com.example.osamakhalid.schoolsystem.Adapters.Transport_Adapter;
import com.example.osamakhalid.schoolsystem.BaseConnection.RetrofitInitialize;
import com.example.osamakhalid.schoolsystem.ConnectionInterface.ClientAPIs;
import com.example.osamakhalid.schoolsystem.Consts.Values;
import com.example.osamakhalid.schoolsystem.Model.LoginResponse;
import com.example.osamakhalid.schoolsystem.Model.NewsAndEventsResponse;
import com.example.osamakhalid.schoolsystem.Model.NewsAndEventsResponseList;
import com.example.osamakhalid.schoolsystem.Model.News_Model;
import com.example.osamakhalid.schoolsystem.Model.Transport_Model;
import com.example.osamakhalid.schoolsystem.R;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class NewsAndEvents extends AppCompatActivity {
    public RecyclerView recyclerView;
    public RecyclerView.Adapter adapter;
    public List<NewsAndEventsResponse> listItems;
    private SharedPreferences mPrefs;
    private Gson gson;
    private Retrofit retrofit;
    private ClientAPIs clientAPIs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_and_events);
        listItems = new ArrayList<>();
        recyclerView = findViewById(R.id.news_recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//        mPrefs = getSharedPreferences("UserData", 0);
//        gson = new Gson();
//        String json = mPrefs.getString("UserObject", "");
//        LoginResponse userData = gson.fromJson(json, LoginResponse.class);
        LoginResponse userData = getUserData();
        retrofit = RetrofitInitialize.getApiClient();
        clientAPIs = retrofit.create(ClientAPIs.class);
        String base = userData.getUsername() + ":" + userData.getPassword();
        String authHeader = "Basic " + Base64.encodeToString(base.getBytes(), Base64.NO_WRAP);
        getData(authHeader);
        adapter = new NewsAndEvents_Adapter(listItems, getApplicationContext());
        recyclerView.setAdapter(adapter);
    }

    public void getData(String authHeader) {
        Call<NewsAndEventsResponseList> call = clientAPIs.getNewsAndEvents(authHeader);
        call.enqueue(new Callback<NewsAndEventsResponseList>() {
            @Override
            public void onResponse(Call<NewsAndEventsResponseList> call, Response<NewsAndEventsResponseList> response) {
                if (response.isSuccessful()) {
                    NewsAndEventsResponseList newsAndEvents = response.body();
                    listItems.addAll(newsAndEvents.getNewsAndEventsLists());
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<NewsAndEventsResponseList> call, Throwable t) {

            }
        });
    }

    public LoginResponse getUserData() {
        SharedPreferences mPrefs = getSharedPreferences("UserData", 0);
        Gson gson = new Gson();
        String json = mPrefs.getString("UserObject", "");
        LoginResponse userData = gson.fromJson(json, LoginResponse.class);
        return userData;
    }
}
