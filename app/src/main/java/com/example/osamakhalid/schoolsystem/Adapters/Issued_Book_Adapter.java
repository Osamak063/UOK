package com.example.osamakhalid.schoolsystem.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.osamakhalid.schoolsystem.Model.LibraryData_Model;
import com.example.osamakhalid.schoolsystem.R;

import java.util.List;


public class Issued_Book_Adapter extends RecyclerView.Adapter<Issued_Book_Adapter.ViewHolder> {


    Context context;
    public List<LibraryData_Model> libraryData_models;

    public Issued_Book_Adapter(List<LibraryData_Model> libraryData_models, Context context) {
        this.context = context;
        this.libraryData_models = libraryData_models;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
       View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.library_view_layout,parent,false);
       return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        final LibraryData_Model libraryDataModel = libraryData_models.get(position);
        holder.b_serial.setText("Serial No : "+libraryDataModel.getSerialNo());
        holder.b_name.setText("Book Name : "+libraryDataModel.getBook());
        holder.b_author.setText("Author : "+libraryDataModel.getAuthor());
        holder.b_issue.setText("Issue Date : "+libraryDataModel.getIssueDate());
        holder.b_due_date.setText("Due Date : "+libraryDataModel.getDueDate());
        holder.b_return.setText("Return Date : "+libraryDataModel.getReturnDate());
        holder.b_fine.setText("Fine : "+libraryDataModel.getFine());

    }

    @Override
    public int getItemCount() {
        if(libraryData_models != null){
            return libraryData_models.size();
        }
        else{
            return 0;
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView b_serial,b_name,b_author,b_issue,b_due_date,b_return,b_fine;

        public ViewHolder(View itemView) {
            super(itemView);

            b_serial = itemView.findViewById(R.id.book__serial);
            b_name = itemView.findViewById(R.id.book__name);
            b_author = itemView.findViewById(R.id.book__author);
            b_issue = itemView.findViewById(R.id.book__issue_date);
            b_due_date = itemView.findViewById(R.id.book__due_date);
            b_return = itemView.findViewById(R.id.book__return_date);
            b_fine = itemView.findViewById(R.id.book__fine);

        }
    }
}
