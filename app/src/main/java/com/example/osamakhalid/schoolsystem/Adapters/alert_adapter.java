package com.example.osamakhalid.schoolsystem.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.osamakhalid.schoolsystem.Model.Alert_Model;
import com.example.osamakhalid.schoolsystem.R;

import java.util.List;

/**
 * Created by HAMI on 17/01/2018.
 */

public class alert_adapter extends RecyclerView.Adapter<alert_adapter.ViewHolder> {

    Context context;
    List<Alert_Model> alertModel;

    public alert_adapter( List<Alert_Model> alertModel,Context context) {
        this.context = context;
        this.alertModel = alertModel;
    }

    @Override
    public alert_adapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.alert_view_layout,parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(alert_adapter.ViewHolder holder, int position) {

        final  Alert_Model a_model =  alertModel.get(position);
        holder.title.setText(a_model.getTitle());
        holder.notification.setText(a_model.getNotice());
        holder.due_date.setText(a_model.getDate());


    }

    @Override
    public int getItemCount() {
        if(alertModel.size()>0){
            return alertModel.size();

        }
        return 0;
    }



    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView notification,title,due_date;
        LinearLayout linearLayout;


        public ViewHolder(View itemView) {
            super(itemView);
            due_date = itemView.findViewById(R.id.notice_date);
            title = itemView.findViewById(R.id.notice_name);
            notification = itemView.findViewById(R.id.notification);
            linearLayout = itemView.findViewById(R.id.alertLayout);



        }

    }
}
