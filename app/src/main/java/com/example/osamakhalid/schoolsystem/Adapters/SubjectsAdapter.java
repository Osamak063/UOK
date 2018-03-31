package com.example.osamakhalid.schoolsystem.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.osamakhalid.schoolsystem.Model.SubjectResponse;
import com.example.osamakhalid.schoolsystem.R;

import java.util.List;

/**
 * Created by Osama Khalid on 2/21/2018.
 */

public class SubjectsAdapter extends RecyclerView.Adapter<SubjectsAdapter.ViewHolder> {
    Context context;
    List<SubjectResponse> subjectModels;

    public SubjectsAdapter(List<SubjectResponse> subjectModels, Context context) {
        this.context = context;
        this.subjectModels = subjectModels;
    }

    @Override
    public SubjectsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.subject_view_layout, parent, false);
        return new SubjectsAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(SubjectsAdapter.ViewHolder holder, int position) {

        final SubjectResponse subjectModel = subjectModels.get(position);
        holder.name.setText(subjectModel.getSubjectName());
        holder.code.setText( subjectModel.getSubjectCode());
        holder.author.setText( subjectModel.getSubjectAuthor());
       // holder.teacher.setText( subjectModel.getTeacher());
    }

    @Override
    public int getItemCount() {
        return subjectModels.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView name, author, code, teacher;

        public ViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.subject_name);
            author = itemView.findViewById(R.id.subject_author);
            code = itemView.findViewById(R.id.subject_code);
        //    teacher = itemView.findViewById(R.id.subject_teacher);


        }

    }

}
