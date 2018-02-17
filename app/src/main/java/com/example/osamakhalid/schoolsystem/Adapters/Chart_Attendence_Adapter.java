package com.example.osamakhalid.schoolsystem.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.osamakhalid.schoolsystem.Model.Alert_Model;
import com.example.osamakhalid.schoolsystem.Model.ItemClickListener;
import com.example.osamakhalid.schoolsystem.R;

import java.util.List;

/**
 * Created by HAMI on 16/02/2018.
 */

public class Chart_Attendence_Adapter extends RecyclerView.Adapter<Chart_Attendence_Adapter.ViewHolder>{

    Context context;
    List<Alert_Model> teachermodels;

    private ItemClickListener itemClickListener;

    public Chart_Attendence_Adapter(List<Alert_Model> teachermodels, Context context) {
        this.context = context;
        this.teachermodels = teachermodels;

    }

    @Override
    public Chart_Attendence_Adapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.alert_view_layout, parent, false);
        return new Chart_Attendence_Adapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(Chart_Attendence_Adapter.ViewHolder holder, int position) {

        final Alert_Model teacher_model = teachermodels.get(position);
        holder.name.setText(teacher_model.getNotification());
    }

    @Override
    public int getItemCount() {
        return teachermodels.size();
    }

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }



    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        LinearLayout linearLayout;
        TextView name;

        public ViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.notification);
            linearLayout = itemView.findViewById(R.id.monthView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            Log.e("Adapter Click: ","here");
            if (itemClickListener != null) {
                Alert_Model teacher_model = teachermodels.get(getAdapterPosition());
                itemClickListener.onClick(view, teacher_model.getNotification());
            }
        }
    }
}
