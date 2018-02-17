package com.example.osamakhalid.schoolsystem.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.osamakhalid.schoolsystem.Model.ExamResult_Model;
import com.example.osamakhalid.schoolsystem.R;

import java.util.List;

/**
 * Created by HAMI on 17/01/2018.
 */

public class exam_adapter extends RecyclerView.Adapter<exam_adapter.ViewHolder> {

    public List<ExamResult_Model> listItems;
    Context context;


    public exam_adapter(List<ExamResult_Model> listItems, Context context) {
        this.listItems = listItems;
        this.context = context;
    }

    //setting the cardview
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.exam_result_view, parent, false);

        return new ViewHolder(v);
    }

    //binding coming data
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        final ExamResult_Model e_model = listItems.get(position);

        holder.subject.setText(e_model.getSubject());
        holder.marks.setText(e_model.getMarks());
        holder.date.setText(e_model.getDate());

        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, e_model.getSubject(), Toast.LENGTH_LONG).show();
            }
        });

    }

    //setting count to recyclerview
    @Override
    public int getItemCount() {
        return listItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView subject, marks, date;
        public LinearLayout linearLayout;

        public ViewHolder(View itemView) {
            super(itemView);

            //binding views of cardviews
            subject = itemView.findViewById(R.id.subject);
            marks = itemView.findViewById(R.id.sub_marks);
            date = itemView.findViewById(R.id.date);
            linearLayout = itemView.findViewById(R.id.examLayout);
        }
    }
}
