package com.example.osamakhalid.schoolsystem.Model;

/**
 * Created by Osama Khalid on 12/24/2017.
 */

public class LocationPoints {
    public double latitude , longitude ;
    LocationPoints(){}
    public LocationPoints(double latitude, double longitude){
        this.latitude=latitude;
        this.longitude=longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }
}
