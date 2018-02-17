package com.example.osamakhalid.schoolsystem.Activites;

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
import com.example.osamakhalid.schoolsystem.Model.ItemClickListener;
import com.example.osamakhalid.schoolsystem.Model.Teacher_Model;
import com.example.osamakhalid.schoolsystem.R;

import java.util.ArrayList;
import java.util.List;

public class Messages extends AppCompatActivity implements ItemClickListener {
    List<Teacher_Model> teacher_modelList;
    RecyclerView recyclerView;
    public Teachers_Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messages);
        teacher_modelList = new ArrayList<>();
        recyclerView = (RecyclerView) findViewById(R.id.messages_recycler_view);
        teacher_modelList.add(new Teacher_Model("Asim Ali", "Courses: Science,Maths"));
        teacher_modelList.add(new Teacher_Model("Muhammad Saeed", "Courses: Islamiat,Urdu"));
        teacher_modelList.add(new Teacher_Model("Madiha Khurram", "Courses: English"));
        teacher_modelList.add(new Teacher_Model("Shaista Raees", "Courses: Physics,Chemistry"));
        teacher_modelList.add(new Teacher_Model("Sadiq Ali Khan", "Courses: Computer Science"));
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new Teachers_Adapter(teacher_modelList, getApplicationContext());
        recyclerView.setAdapter(adapter);
        adapter.setItemClickListener(this);
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
