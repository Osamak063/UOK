package com.example.osamakhalid.schoolsystem.Activites;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.osamakhalid.schoolsystem.Adapters.HomeWork_Adapter;
import com.example.osamakhalid.schoolsystem.Model.Homework_Model;
import com.example.osamakhalid.schoolsystem.R;

import java.util.ArrayList;
import java.util.List;

public class HomeWork extends AppCompatActivity {

    public RecyclerView recyclerView;
    public RecyclerView.Adapter adapter;
    public List<Homework_Model> listItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_work);
        listItems = new ArrayList<>();

       listItems.add(new Homework_Model("Mathematics","ex# 1.1 to ex# 5.2","16 march,2018"));
        listItems.add(new Homework_Model("Physics","ex# 1.1 to ex# 5.2","16 march,2018"));
        listItems.add(new Homework_Model("Chemistry","ex# 1.1 to ex# 5.2","16 march,2018"));
        listItems.add(new Homework_Model("Urdu","revise chapter# 4","16 march,2018"));
        listItems.add(new Homework_Model("English","solve ex# 5.2","16 march,2018"));


        recyclerView =  findViewById(R.id.homeworkview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new HomeWork_Adapter(listItems,getApplicationContext());
        recyclerView.setAdapter(adapter);

    }
}
