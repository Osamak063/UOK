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
        holder.notification.setText(a_model.getNotification());


    }

    @Override
    public int getItemCount() {
        return alertModel.size();
    }



    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView notification;
        LinearLayout linearLayout;


        public ViewHolder(View itemView) {
            super(itemView);

            notification = itemView.findViewById(R.id.notification);
            linearLayout = itemView.findViewById(R.id.alertLayout);



        }

    }
}
