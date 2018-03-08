package com.example.osamakhalid.schoolsystem.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.osamakhalid.schoolsystem.Model.Alert_Model;
import com.example.osamakhalid.schoolsystem.Model.FeesResponse;
import com.example.osamakhalid.schoolsystem.R;

import java.util.List;

/**
 * Created by Osama Khalid on 3/7/2018.
 */

public class FeesAdapter extends RecyclerView.Adapter<FeesAdapter.ViewHolder> {
    Context context;
    List<FeesResponse> feesList;

    public FeesAdapter(List<FeesResponse> feesList, Context context) {
        this.context = context;
        this.feesList = feesList;
    }

    @Override
    public FeesAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.fees_view_layout, parent, false);
        return new FeesAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(FeesAdapter.ViewHolder holder, int position) {

        final FeesResponse fees = feesList.get(position);
        holder.studentName.setText(fees.getStudent());
        holder.status.setText(fees.getStatus());
        holder.date.setText(fees.getDate());
        holder.dueAmount.setText(fees.getDueAmount());
        holder.amount.setText(fees.getAmount());
        holder.feeType.setText(fees.getFeeType());
    }

    @Override
    public int getItemCount() {

        if (feesList != null) {
            return feesList.size();
        }
        else{
            return 0;
        }
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView feeType, date, status, studentName, amount, dueAmount;


        public ViewHolder(View itemView) {
            super(itemView);
            studentName = itemView.findViewById(R.id.fees_student_name);
            status = itemView.findViewById(R.id.fees_status);
            date = itemView.findViewById(R.id.fees_date);
            feeType = itemView.findViewById(R.id.fees_fee_type);
            amount = itemView.findViewById(R.id.fees_amount);
            dueAmount = itemView.findViewById(R.id.fees_due_amount);
        }

    }
}

