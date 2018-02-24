package com.example.osamakhalid.schoolsystem.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Osama Khalid on 2/24/2018.
 */

public class ExamScheduleResponseList {
    @SerializedName("exams_schedule_data")
    @Expose
    private List<ExamScheduleResponse> examsScheduleData ;

    public List<ExamScheduleResponse> getExamsScheduleData() {
        return examsScheduleData;
    }

    public void setExamsScheduleData(List<ExamScheduleResponse> examsScheduleData) {
        this.examsScheduleData = examsScheduleData;
    }
}
