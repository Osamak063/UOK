package com.example.osamakhalid.schoolsystem.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.osamakhalid.schoolsystem.Interfaces.ItemClickListenerDate;
import com.example.osamakhalid.schoolsystem.Model.SyllabusMultiple_Subject_Model;
import com.example.osamakhalid.schoolsystem.R;

import java.util.List;

/**
 * Created by HAMI on 08/03/2018.
 */

public class SyllabusSubject_Adapter extends RecyclerView.Adapter<SyllabusSubject_Adapter.ViewHolder> {

    Context context;
    List<SyllabusMultiple_Subject_Model> syllabusData_modelList;
    private ItemClickListenerDate itemClickListener;

    public SyllabusSubject_Adapter(List<SyllabusMultiple_Subject_Model> syllabusData_modelList, Context context) {
        this.context = context;
        this.syllabusData_modelList = syllabusData_modelList;
    }


    @Override
    public SyllabusSubject_Adapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.syllabus_subject_view_layout, parent, false);
        return new SyllabusSubject_Adapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(SyllabusSubject_Adapter.ViewHolder holder, int position) {

        SyllabusMultiple_Subject_Model syllabusData_model = syllabusData_modelList.get(position);
        holder.subject_name.setText(syllabusData_model.getSubject());
        holder.subject_des.setText(syllabusData_model.getDesc());

    }


    @Override
    public int getItemCount() {
        if (syllabusData_modelList != null) {
            return syllabusData_modelList.size();
        }
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView subject_name, subject_des;

        public ViewHolder(View itemView) {
            super(itemView);

            subject_name = itemView.findViewById(R.id.subject_name);
            subject_des = itemView.findViewById(R.id.subject_description);

        }


    }
}
