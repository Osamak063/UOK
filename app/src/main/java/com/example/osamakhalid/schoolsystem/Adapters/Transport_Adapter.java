package com.example.osamakhalid.schoolsystem.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.osamakhalid.schoolsystem.Model.Transport_Model;
import com.example.osamakhalid.schoolsystem.R;

import java.util.List;

/**
 * Created by Osama Khalid on 2/1/2018.
 */

public class Transport_Adapter extends RecyclerView.Adapter<Transport_Adapter.ViewHolder> {
    Context context;
    List<Transport_Model> transportmodel;
    Transport_Model transportModel;

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
        holder.transportID.setText("Transport ID: "+trans_model.getTransportID());
        holder.route.setText("Route: "+trans_model.getRoute());
        holder.vehicle.setText("Vehicle: "+trans_model.getVehicle());
        holder.fare.setText("Fare "+trans_model.getFare());
        holder.note.setText("Note: "+trans_model.getNote());


    }

    @Override
    public int getItemCount() {
        return transportmodel.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView transportID,route,vehicle,fare,note;


        public ViewHolder(View itemView) {
            super(itemView);

            transportID = itemView.findViewById(R.id.transport_id);
            route =itemView.findViewById(R.id.route_view);
            vehicle= itemView.findViewById(R.id.vehicle_view);
            fare = itemView.findViewById(R.id.fare_charges_view);
            note = itemView.findViewById(R.id.notice_View);
        }
    }
}
