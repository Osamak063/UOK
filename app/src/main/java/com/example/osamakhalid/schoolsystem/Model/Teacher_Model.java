package com.example.osamakhalid.schoolsystem.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Osama Khalid on 2/3/2018.
 */

public class Teacher_Model {

    @SerializedName("teacher_data")
    @Expose
    private List<TeacherData_Model> teacherData = null;

    public List<TeacherData_Model> getTeacherData() {
        return teacherData;
    }

    public void setTeacherData(List<TeacherData_Model> teacherData) {
        this.teacherData = teacherData;
    }


}
