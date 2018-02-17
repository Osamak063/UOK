package com.example.osamakhalid.schoolsystem.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.osamakhalid.schoolsystem.Model.Alert_Model;
import com.example.osamakhalid.schoolsystem.Model.Transport_Model;
import com.example.osamakhalid.schoolsystem.R;

import org.w3c.dom.Text;

import java.util.List;

/**
 * Created by Osama Khalid on 2/1/2018.
 */

public class Transport_Adapter extends RecyclerView.Adapter<Transport_Adapter.ViewHolder> {
    Context context;
    List<Transport_Model> transportmodel;

    public Transport_Adapter(List<Transport_Model> transportmodel, Context context) {
        this.context = context;
        this.transportmodel = transportmodel;
    }

    @Override
    public Transport_Adapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.transport_view_layout, parent, false);
        return new Transport_Adapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(Transport_Adapter.ViewHolder holder, int position) {

        final Transport_Model trans_model = transportmodel.get(position);
        holder.vanNum.setText(trans_model.getVanNum());
        holder.route.setText(trans_model.getRoute());

    }

    @Override
    public int getItemCount() {
        return transportmodel.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView vanNum;
        TextView route;

        public ViewHolder(View itemView) {
            super(itemView);
            vanNum = itemView.findViewById(R.id.van_num);
            route = itemView.findViewById(R.id.route);
        }
    }
}
