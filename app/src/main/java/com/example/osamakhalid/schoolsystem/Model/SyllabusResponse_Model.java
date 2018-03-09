package com.example.osamakhalid.schoolsystem.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by HAMI on 08/03/2018.
 */

public class SyllabusResponse_Model {

    @SerializedName("syllabus_data")
    @Expose
    private List<SyllabusData_Model> syllabusData = null;

    public List<SyllabusData_Model> getSyllabusData() {
        return syllabusData;
    }

    public void setSyllabusData(List<SyllabusData_Model> syllabusData) {
        this.syllabusData = syllabusData;
    }

}
