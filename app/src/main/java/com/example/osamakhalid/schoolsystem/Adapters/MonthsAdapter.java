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
 * Created by Osama Khalid on 2/22/2018.
 */

public class MonthsAdapter extends RecyclerView.Adapter<MonthsAdapter.ViewHolder> {

    Context context;
    List<String> monthsName;
    private ItemClickListener itemClickListener;
    public MonthsAdapter(List<String> monthsName, Context context) {
        this.context = context;
        this.monthsName = monthsName;
    }

    @Override
    public MonthsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.month_view_layout, parent, false);
        return new MonthsAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(MonthsAdapter.ViewHolder holder, int position) {

        final String month = monthsName.get(position);
        holder.monthName.setText(month);
    }

    @Override
    public int getItemCount() {
        return monthsName.size();
    }

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView monthName;


        public ViewHolder(View itemView) {
            super(itemView);
            monthName = itemView.findViewById(R.id.month_name);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            Log.e("Adapter Click: ","here");
            if (itemClickListener != null) {
                String monthname = monthsName.get(getAdapterPosition());
                itemClickListener.onClick(view, monthname);
            }
        }

    }
}
