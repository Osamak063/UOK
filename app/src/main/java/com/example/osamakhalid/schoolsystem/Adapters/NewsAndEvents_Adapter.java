package com.example.osamakhalid.schoolsystem.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.osamakhalid.schoolsystem.Model.News_Model;
import com.example.osamakhalid.schoolsystem.R;

import java.util.List;

/**
 * Created by Osama Khalid on 2/2/2018.
 */

public class NewsAndEvents_Adapter extends RecyclerView.Adapter<NewsAndEvents_Adapter.ViewHolder>{
        Context context;
        List<News_Model> newsModels;

public NewsAndEvents_Adapter(List<News_Model> transportmodel,Context context){
        this.context=context;
        this.newsModels=transportmodel;
        }

@Override
public NewsAndEvents_Adapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.news_view_layout,parent,false);
        return new NewsAndEvents_Adapter.ViewHolder(v);
        }

@Override
public void onBindViewHolder(NewsAndEvents_Adapter.ViewHolder holder,int position){

final News_Model news_model=newsModels.get(position);
        holder.news.setText(news_model.getNews());
        holder.date.setText(news_model.getDate());
        }

@Override
public int getItemCount(){
        return newsModels.size();
        }

public class ViewHolder extends RecyclerView.ViewHolder {

    TextView news;
    TextView date;

    public ViewHolder(View itemView) {
        super(itemView);
        news = itemView.findViewById(R.id.news);
        date = itemView.findViewById(R.id.date);
    }
}
}
