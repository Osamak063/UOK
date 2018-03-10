package com.example.osamakhalid.schoolsystem.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by HAMI on 17/01/2018.
 */

public class TimeTable_Model {

    @SerializedName("routine_data")
    @Expose
    private List<TimeTable_Response_Model> routineData = null;

    public List<TimeTable_Response_Model> getRoutineData() {
        return routineData;
    }

    public void setRoutineData(List<TimeTable_Response_Model> routineData) {
        this.routineData = routineData;
    }

}
