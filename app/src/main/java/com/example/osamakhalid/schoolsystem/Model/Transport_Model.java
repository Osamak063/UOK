package com.example.osamakhalid.schoolsystem.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Osama Khalid on 2/1/2018.
 */

public class Transport_Model {
    @SerializedName("transportID")
    @Expose
    private String transportID;
    @SerializedName("route")
    @Expose
    private String route;
    @SerializedName("vehicle")
    @Expose
    private String vehicle;
    @SerializedName("fare")
    @Expose
    private String fare;
    @SerializedName("note")
    @Expose
    private String note;

    public String getTransportID() {
        return transportID;
    }

    public void setTransportID(String transportID) {
        this.transportID = transportID;
    }

    public String getRoute() {
        return route;
    }

    public void setRoute(String route) {
        this.route = route;
    }

    public String getVehicle() {
        return vehicle;
    }

    public void setVehicle(String vehicle) {
        this.vehicle = vehicle;
    }

    public String getFare() {
        return fare;
    }

    public void setFare(String fare) {
        this.fare = fare;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

}
