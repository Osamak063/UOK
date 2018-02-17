package com.example.osamakhalid.schoolsystem.Activites;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.osamakhalid.schoolsystem.Adapters.NewsAndEvents_Adapter;
import com.example.osamakhalid.schoolsystem.Adapters.Transport_Adapter;
import com.example.osamakhalid.schoolsystem.Model.News_Model;
import com.example.osamakhalid.schoolsystem.Model.Transport_Model;
import com.example.osamakhalid.schoolsystem.R;

import java.util.ArrayList;
import java.util.List;

public class NewsAndEvents extends AppCompatActivity {
    public RecyclerView recyclerView;
    public RecyclerView.Adapter adapter;
    public List<News_Model> listItems;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_and_events);
        listItems = new ArrayList<>();
        // Dummy Data
        listItems.add(new News_Model("Eid Milan Party","4 August 2018"));
        listItems.add(new News_Model("Farewell Party","10 October 2018"));
        listItems.add(new News_Model("Kashmir Day","5 February 2018"));
        listItems.add(new News_Model("Sports Day","5 January 2018"));
        listItems.add(new News_Model("Debate Competition","10 November 2018"));
        listItems.add(new News_Model("Eid-E-Milad Un Nabi S.A.W","25 December 2018"));
        recyclerView =  findViewById(R.id.news_recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new NewsAndEvents_Adapter(listItems,getApplicationContext());
        recyclerView.setAdapter(adapter);
    }
}
