package com.example.osamakhalid.schoolsystem.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.osamakhalid.schoolsystem.Model.Book_Model;
import com.example.osamakhalid.schoolsystem.Model.Chat_Model;
import com.example.osamakhalid.schoolsystem.R;

import java.util.List;

/**
 * Created by Osama Khalid on 2/4/2018.
 */

public class Books_Adapter extends RecyclerView.Adapter<Books_Adapter.ViewHolder> {
    List<Book_Model> bookModels;

    public Books_Adapter(List<Book_Model> bookModels) {
        this.bookModels = bookModels;
    }

    @Override
    public Books_Adapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.book_view_layout, parent, false);
        return new Books_Adapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(Books_Adapter.ViewHolder holder, int position) {
        final Book_Model bookmodel = bookModels.get(position);
        holder.bookName.setText(bookmodel.getName());
        holder.course.setText(bookmodel.getCourse());
    }

    @Override
    public int getItemCount() {
        return bookModels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView bookName;
        TextView course;

        public ViewHolder(View itemView) {
            super(itemView);
            bookName = itemView.findViewById(R.id.book_name);
            course = itemView.findViewById(R.id.course);
        }
    }
}
