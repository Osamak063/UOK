package com.example.osamakhalid.schoolsystem.Activites;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.osamakhalid.schoolsystem.Adapters.Teachers_Adapter;
import com.example.osamakhalid.schoolsystem.BaseConnection.RetrofitInitialize;
import com.example.osamakhalid.schoolsystem.ConnectionInterface.ClientAPIs;
import com.example.osamakhalid.schoolsystem.Consts.Values;
import com.example.osamakhalid.schoolsystem.GlobalCalls.CommonCalls;
import com.example.osamakhalid.schoolsystem.Model.ItemClickListener;
import com.example.osamakhalid.schoolsystem.Model.LoginResponse;
import com.example.osamakhalid.schoolsystem.Model.MessagesInboxResponse;
import com.example.osamakhalid.schoolsystem.Model.MessagesInboxResponseList;
import com.example.osamakhalid.schoolsystem.Model.NewsAndEventsResponseList;
import com.example.osamakhalid.schoolsystem.Model.TeacherData_Model;
import com.example.osamakhalid.schoolsystem.R;
import com.google.android.gms.wearable.MessageApi;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class Messages extends AppCompatActivity implements ItemClickListener {
    List<MessagesInboxResponse> listItems;
    RecyclerView recyclerView;
    public Teachers_Adapter adapter;
    private Retrofit retrofit;
    private ClientAPIs clientAPIs;
    ProgressDialog progressDialog;
    LoginResponse userData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messages);

        //teacher_modelList = new ArrayList<>();
        recyclerView = findViewById(R.id.messages_recycler_view);
//        teacher_modelList.add(new Teacher_Model("Asim Ali", "Courses: Science,Maths"));
//        teacher_modelList.add(new Teacher_Model("Muhammad Saeed", "Courses: Islamiat,Urdu"));
//        teacher_modelList.add(new Teacher_Model("Madiha Khurram", "Courses: English"));
//        teacher_modelList.add(new Teacher_Model("Shaista Raees", "Courses: Physics,Chemistry"));
//        teacher_modelList.add(new Teacher_Model("Sadiq Ali Khan", "Courses: Computer Science"));

        listItems = new ArrayList<>();
        progressDialog = CommonCalls.createDialouge(this, "", Values.DIALOGUE_MSG);
        recyclerView = findViewById(R.id.messages_recycler_view);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        userData = CommonCalls.getUserData(Messages.this);
        String base = userData.getUsername() + ":" + userData.getPassword();
        String authHeader = "Basic " + Base64.encodeToString(base.getBytes(), Base64.NO_WRAP);
        getData(authHeader);
        // adapter = new Teachers_Adapter(listItems, getApplicationContext());
        recyclerView.setAdapter(adapter);
        adapter.setItemClickListener(this);
    }

    public void getData(String authHeader) {
        retrofit = RetrofitInitialize.getApiClient();
        clientAPIs = retrofit.create(ClientAPIs.class);
        Call<MessagesInboxResponseList> call = clientAPIs.getMessagesInbox(userData.getUsername(), authHeader);
        call.enqueue(new Callback<MessagesInboxResponseList>() {
            @Override
            public void onResponse(Call<MessagesInboxResponseList> call, Response<MessagesInboxResponseList> response) {
                if (response.isSuccessful()) {
                    MessagesInboxResponseList messageInboxResponseList = response.body();
                    if (messageInboxResponseList != null && messageInboxResponseList.getMessageData() != null) {
                        progressDialog.dismiss();
                        listItems.addAll(messageInboxResponseList.getMessageData());
                        adapter.notifyDataSetChanged();
                    } else {
                        progressDialog.dismiss();
                        Toast.makeText(Messages.this, "Inbox not available yet.", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<MessagesInboxResponseList> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(Messages.this, "Sorry something went wrong.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onClick(View view, String name) {
        Intent i = new Intent(this, ParentTeacherChat.class);
        i.putExtra("name", name);
        startActivity(i);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_item_menu, menu);
        MenuItem item = menu.findItem(R.id.search_item);
        SearchView searchView = (SearchView) item.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                adapter.getFilter().filter(s);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                adapter.getFilter().filter(s);
                return false;
            }
        });
        return true;
    }
}
