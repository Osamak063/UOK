package com.example.osamakhalid.schoolsystem.Activites;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.bumptech.glide.Glide;
import com.example.osamakhalid.schoolsystem.BaseConnection.RetrofitInitialize;
import com.example.osamakhalid.schoolsystem.ConnectionInterface.ClientAPIs;
import com.example.osamakhalid.schoolsystem.Consts.Values;
import com.example.osamakhalid.schoolsystem.GlobalCalls.CommonCalls;
import com.example.osamakhalid.schoolsystem.Model.LoginResponse;
import com.example.osamakhalid.schoolsystem.Model.NewsandEventDetailResponse;
import com.example.osamakhalid.schoolsystem.Model.ParentLoginResponse;
import com.example.osamakhalid.schoolsystem.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class NewsandEventDetail extends AppCompatActivity {

    TextView event_title,event_description;
    ImageView event_image;
    String event_id;
    private ProgressDialog progress_dialouge;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newsand_event_detail);
        //Setting up Toolbar
        toolbar = findViewById(R.id.toolBar);
        toolbar.setTitle("Details");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(NewsandEventDetail.this, NewsAndEvents.class));
            }
        });
        progress_dialouge = CommonCalls.createDialouge(this,"",Values.DIALOGUE_MSG);

        event_title = findViewById(R.id.event_title);
        event_description = findViewById(R.id.description_text_view);
        event_image = findViewById(R.id.event_image);

        Bundle args = getIntent().getExtras();
        event_id = args.getString("eventID");
        if(event_id != null){
           // event_id = args.getString("eventID");
            Log.e("ActivityEventID",event_id);
        }
        getDetailsofNews();

    }

    public void getDetailsofNews(){

        Retrofit retrofit = RetrofitInitialize.getApiClient();
        ClientAPIs clientAPIs = retrofit.create(ClientAPIs.class);
        String base = null;
        if (CommonCalls.getUserType(NewsandEventDetail.this).equals(Values.TYPE_PARENT)) {
            ParentLoginResponse loginResponse = CommonCalls.getParentData(NewsandEventDetail.this);
            base = loginResponse.getUsername() + ":" + loginResponse.getPassword();
        } else if (CommonCalls.getUserType(NewsandEventDetail.this).equals(Values.TYPE_STUDENT)) {
            LoginResponse loginResponse = CommonCalls.getUserData(NewsandEventDetail.this);
            base = loginResponse.getUsername() + ":" + loginResponse.getPassword();
        }
        String authHeader = "Basic " + Base64.encodeToString(base.getBytes(), Base64.NO_WRAP);
        Call<NewsandEventDetailResponse> call = clientAPIs.getNewsDetails(event_id,authHeader);
        call.enqueue(new Callback<NewsandEventDetailResponse>() {
            @Override
            public void onResponse(Call<NewsandEventDetailResponse> call, Response<NewsandEventDetailResponse> response) {
                if(response.isSuccessful()){

                    NewsandEventDetailResponse newsandEventDetailResponse = response.body();
                    if(newsandEventDetailResponse != null){
                        progress_dialouge.dismiss();
                        Log.e("InResponse",newsandEventDetailResponse.getTitle());
                        event_title.setText(newsandEventDetailResponse.getTitle());
                        event_description.setText(newsandEventDetailResponse.getDetails());
                        Glide.with(NewsandEventDetail.this).load(newsandEventDetailResponse.getImage()
                                + newsandEventDetailResponse.getPhoto())
                                .into(event_image);
                    }


                }

            }

            @Override
            public void onFailure(Call<NewsandEventDetailResponse> call, Throwable t) {
                Toast.makeText(NewsandEventDetail.this, Values.SERVER_ERROR,Toast.LENGTH_SHORT).show();
            }
        });

    }
}
