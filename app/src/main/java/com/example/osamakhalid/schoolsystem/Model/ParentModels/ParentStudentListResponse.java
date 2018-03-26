package com.example.osamakhalid.schoolsystem.Model.ParentModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Osama Khalid on 3/21/2018.
 */

public class ParentStudentListResponse {
    @SerializedName("student_data")
    @Expose
    private List<ParentStudentData> studentData = null;

    public List<ParentStudentData> getStudentData() {
        return studentData;
    }

    public void setStudentData(List<ParentStudentData> studentData) {
        this.studentData = studentData;
    }
}
