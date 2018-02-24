package com.example.osamakhalid.schoolsystem.Activites;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;

import com.example.osamakhalid.schoolsystem.Adapters.Teachers_Adapter;
import com.example.osamakhalid.schoolsystem.Consts.Values;
import com.example.osamakhalid.schoolsystem.GlobalCalls.CommonCalls;
import com.example.osamakhalid.schoolsystem.Model.ItemClickListener;
import com.example.osamakhalid.schoolsystem.Model.TeacherData_Model;
import com.example.osamakhalid.schoolsystem.R;

import java.util.List;

public class Profile extends AppCompatActivity implements ItemClickListener {
    RecyclerView recyclerView;
    public Teachers_Adapter adapter;
    public static String teacher_id ;
    public static List<TeacherData_Model> teacher_list;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        recyclerView = findViewById(R.id.profile_recycler_view);
         progressDialog = CommonCalls.createDialouge(this,"", Values.DIALOGUE_MSG);

        //getTeacherData();
        teacher_list = DashboardActivity.teacherData_models;
        teacher_id = teacher_list.get(0).getTeacherId();
        if(teacher_list!= null){
            progressDialog.dismiss();

            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
            adapter = new Teachers_Adapter(teacher_list, getApplicationContext());
            adapter.setItemClickListener(this);
            recyclerView.setAdapter(adapter);

        }

    }

    @Override
    public void onClick(View view, String name) {
        Intent i = new Intent(this, TeacherProfile.class);
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
