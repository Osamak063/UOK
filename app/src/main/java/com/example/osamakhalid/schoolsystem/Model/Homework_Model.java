package com.example.osamakhalid.schoolsystem.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by HAMI on 16/02/2018.
 */

public class Homework_Model  {

    @SerializedName("homework_date_data")
    @Expose
    private List<Homework_Data_Model> homeworkDateData = null;

    public List<Homework_Data_Model> getHomeworkDateData() {
        return homeworkDateData;
    }

    public void setHomeworkDateData(List<Homework_Data_Model> homeworkDateData) {
        this.homeworkDateData = homeworkDateData;
    }
}
