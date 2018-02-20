package com.example.osamakhalid.schoolsystem.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.osamakhalid.schoolsystem.Model.HolidayResponse;
import com.example.osamakhalid.schoolsystem.Model.NewsAndEventsResponse;
import com.example.osamakhalid.schoolsystem.Model.News_Model;
import com.example.osamakhalid.schoolsystem.R;

import org.w3c.dom.Text;

import java.util.List;

/**
 * Created by Osama Khalid on 2/2/2018.
 */

public class NewsAndEvents_Adapter extends RecyclerView.Adapter<NewsAndEvents_Adapter.ViewHolder> {
    Context context;
    List<NewsAndEventsResponse> newsModels;
    List<HolidayResponse> holidayModels;
    String type = "news";

    public NewsAndEvents_Adapter(List<NewsAndEventsResponse> transportmodel, Context context) {
        this.context = context;
        this.newsModels = transportmodel;
    }

    public NewsAndEvents_Adapter(List<HolidayResponse> holidayModels, Context context, String type) {
        this.context = context;
        this.holidayModels = holidayModels;
        this.type = type;
    }

    @Override
    public NewsAndEvents_Adapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.news_view_layout, parent, false);
        return new NewsAndEvents_Adapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(NewsAndEvents_Adapter.ViewHolder holder, int position) {
        if (type.equals("holiday")) {
            HolidayResponse model = holidayModels.get(position);
            holder.title.setText("Title: " + model.getTitle());
            holder.fromDate.setText("From: " + model.getFromDate());
            holder.toDate.setText("To: " + model.getToDate());
            holder.details.setText("Details: " + model.getDetails());
        } else {
            NewsAndEventsResponse model = newsModels.get(position);
            holder.title.setText("News Title: " + model.getTitle());
            holder.fromDate.setText("From: " + model.getFromDate());
            holder.toDate.setText("To: " + model.getToDate());
            holder.details.setText("Details: " + model.getDetails());
        }
    }

    @Override
    public int getItemCount() {
        if (type.equals("holiday")) {
            return holidayModels.size();
        } else {
            return newsModels.size();
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView title;
        TextView fromDate;
        TextView toDate;
        TextView details;

        public ViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title_news);
            fromDate = itemView.findViewById(R.id.from_date_news);
            toDate = itemView.findViewById(R.id.to_date_news);
            details = itemView.findViewById(R.id.details_news);
        }
    }
}
