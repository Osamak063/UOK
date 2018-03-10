package com.example.osamakhalid.schoolsystem.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by HAMI on 10/03/2018.
 */

public class TimeTable_Response_Model {

    @SerializedName("day")
    @Expose
    private String day;
    @SerializedName("data")
    @Expose
    private List<TimeTable_Data_Model> data = null;

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public List<TimeTable_Data_Model> getData() {
        return data;
    }

    public void setData(List<TimeTable_Data_Model> data) {
        this.data = data;
    }

}
