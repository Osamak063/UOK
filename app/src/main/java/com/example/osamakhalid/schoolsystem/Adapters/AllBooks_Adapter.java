package com.example.osamakhalid.schoolsystem.Model;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.osamakhalid.schoolsystem.Model.AllBooks_Data_Model;
import com.example.osamakhalid.schoolsystem.R;

import java.util.List;


public class AllBooks_Adapter extends RecyclerView.Adapter<AllBooks_Adapter.ViewHolder>{
     Context context;
        public List<AllBooks_Data_Model> libraryData_models;

        public AllBooks_Adapter(List<AllBooks_Data_Model> libraryData_models, Context context) {
            this.context = context;
            this.libraryData_models = libraryData_models;
        }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.all_library_book_layout,parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        final AllBooks_Data_Model libraryDataModel = libraryData_models.get(position);
        holder.b_serial.setText(libraryDataModel.getBookID());
        holder.b_name.setText(libraryDataModel.getBook());
        holder.b_author.setText(libraryDataModel.getAuthor());
        holder.b_sub_code.setText(libraryDataModel.getSubjectCode());
        holder.b_price.setText(libraryDataModel.getPrice());
        holder.b_qty.setText(libraryDataModel.getQuantity());
        holder.b_due_qty.setText(libraryDataModel.getDueQuantity());
        holder.b_rack.setText(libraryDataModel.getRack());

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

            TextView b_serial,b_name,b_author,b_sub_code,b_price,b_qty,b_due_qty,b_rack;

            public ViewHolder(View itemView) {
                super(itemView);

                b_serial = itemView.findViewById(R.id.book__id);
                b_name = itemView.findViewById(R.id.book___name);
                b_author = itemView.findViewById(R.id.book___author);
                b_sub_code = itemView.findViewById(R.id.book__subject_code);
                b_price = itemView.findViewById(R.id.book__price);
                b_qty = itemView.findViewById(R.id.book__quantity);
                b_due_qty = itemView.findViewById(R.id.book__due_qty);
                b_rack = itemView.findViewById(R.id.book__rack);

            }
        }
    }






