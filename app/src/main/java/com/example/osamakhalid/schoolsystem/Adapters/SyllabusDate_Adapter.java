package com.example.osamakhalid.schoolsystem.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.osamakhalid.schoolsystem.Interfaces.ItemClickListenerDate;
import com.example.osamakhalid.schoolsystem.Model.SyllabusData_Model;
import com.example.osamakhalid.schoolsystem.R;

import java.util.List;

/**
 * Created by HAMI on 08/03/2018.
 */

public class SyllabusDate_Adapter extends RecyclerView.Adapter<SyllabusDate_Adapter.ViewHolder> {

    Context context;
    List<SyllabusData_Model> syllabusData_modelList;
    private ItemClickListenerDate itemClickListener;

    public SyllabusDate_Adapter( List<SyllabusData_Model> syllabusData_modelList,Context context) {
        this.context = context;
        this.syllabusData_modelList = syllabusData_modelList;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.syllabus_date_view_layout,parent,false);
        return new SyllabusDate_Adapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        SyllabusData_Model syllabusData_model = syllabusData_modelList.get(position);
        holder.subject_date.setText(syllabusData_model.getDate());

    }
    public void setItemClickListener(ItemClickListenerDate itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    @Override
    public int getItemCount() {
        if(syllabusData_modelList != null){
            return syllabusData_modelList.size();
        }
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView subject_date;

        public ViewHolder(View itemView) {
            super(itemView);
            subject_date = itemView.findViewById(R.id.date_view);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (itemClickListener != null) {
                Log.e("Onclick","Called");
                SyllabusData_Model syllabusData_model = syllabusData_modelList.get(getAdapterPosition());
                itemClickListener.onClick(view, syllabusData_model.getData());
            }
        }
    }
}
