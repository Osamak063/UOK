package com.example.osamakhalid.schoolsystem.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Osama Khalid on 2/22/2018.
 */

public class AttendanceResponse {
    @SerializedName("month")
    @Expose
    private String month;
    @SerializedName("data")
    @Expose
    private AttendanceDataResponse data;

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public AttendanceDataResponse getData() {
        return data;
    }

    public void setData(AttendanceDataResponse data) {
        this.data = data;
    }
}
