package com.example.osamakhalid.schoolsystem.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.example.osamakhalid.schoolsystem.Model.ItemClickListener;
import com.example.osamakhalid.schoolsystem.Model.TeacherData_Model;
import com.example.osamakhalid.schoolsystem.R;

import java.util.ArrayList;
import java.util.List;


public class Teachers_Adapter extends RecyclerView.Adapter<Teachers_Adapter.ViewHolder> implements Filterable {
    Context context;
    List<TeacherData_Model> teachermodels;
    private final List<TeacherData_Model> BackupTeachers;
    private ItemClickListener itemClickListener;

    public Teachers_Adapter(List<TeacherData_Model> teachermodels, Context context) {
        this.context = context;
        this.teachermodels = teachermodels;
        this.BackupTeachers = teachermodels;
    }

    @Override
    public Teachers_Adapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.teachers_view_layout, parent, false);
        return new Teachers_Adapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(Teachers_Adapter.ViewHolder holder, int position) {

        final TeacherData_Model teacher_model = teachermodels.get(position);
        holder.name.setText("Name:"+teacher_model.getName());
        holder.courses.setText("Courses: "+teacher_model.getSubject());

    }

    @Override
    public int getItemCount() {
        return teachermodels.size();
    }

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    teachermodels = BackupTeachers;
                } else {
                    List<TeacherData_Model> filteredList = new ArrayList<>();
                    for (TeacherData_Model row : BackupTeachers) {
                        if (row.getName().toLowerCase().contains(charString.toLowerCase())) {
                            filteredList.add(row);
                        }
                    }

                    teachermodels = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = teachermodels;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                teachermodels = (ArrayList<TeacherData_Model>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView name;
        TextView courses;

        public ViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.teacher_name);
            courses = itemView.findViewById(R.id.teacher_courses);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (itemClickListener != null) {
                TeacherData_Model teacher_model = teachermodels.get(getAdapterPosition());
                itemClickListener.onClick(view, teacher_model.getTeacherId());
            }
        }
    }
}
