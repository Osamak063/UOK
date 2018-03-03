package com.example.osamakhalid.schoolsystem.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by HAMI on 01/03/2018.
 */

public class TransportResponse_Model {

    @SerializedName("transport_data")
    @Expose
    private List<Transport_Model> transportData = null;

    public List<Transport_Model> getTransportData() {
        return transportData;
    }

    public void setTransportData(List<Transport_Model> transportData) {
        this.transportData = transportData;
    }
}
