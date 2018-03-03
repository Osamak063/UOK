package com.example.osamakhalid.schoolsystem.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.osamakhalid.schoolsystem.Model.Alert_Model;
import com.example.osamakhalid.schoolsystem.Model.LeavesResponse;
import com.example.osamakhalid.schoolsystem.R;

import java.util.List;

/**
 * Created by Osama Khalid on 3/3/2018.
 */

public class LeavesAdapter extends RecyclerView.Adapter<LeavesAdapter.ViewHolder> {
    Context context;
    List<LeavesResponse> leavesModels;

    public LeavesAdapter(List<LeavesResponse> leavesModels, Context context) {
        this.context = context;
        this.leavesModels = leavesModels;
    }

    @Override
    public LeavesAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.leaves_view_layout, parent, false);
        return new LeavesAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(LeavesAdapter.ViewHolder holder, int position) {

        final LeavesResponse leavesModel = leavesModels.get(position);
        holder.title.setText("Application Title: " + leavesModel.getApplicationTitle());
        holder.submitDate.setText("Submit date: " + leavesModel.getSubmitDate());
        holder.status.setText("Status: " + leavesModel.getStatus());
        holder.to.setText("To: " + leavesModel.getTo());
    }

    @Override
    public int getItemCount() {
        return leavesModels.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView title, submitDate, status, to;


        public ViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.leaves_title);
            submitDate = itemView.findViewById(R.id.leaves_submit_date);
            status = itemView.findViewById(R.id.leaves_status);
            to = itemView.findViewById(R.id.leaves_to);
        }

    }

}
