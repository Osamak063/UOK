package com.example.osamakhalid.schoolsystem.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by HAMI on 08/03/2018.
 */

public class SyllabusData_Model {
    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("data")
    @Expose
    private List<SyllabusMultiple_Subject_Model> data = null;
    @SerializedName("section")
    @Expose
    private String section;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public List<SyllabusMultiple_Subject_Model> getData() {
        return data;
    }

    public void setData(List<SyllabusMultiple_Subject_Model> data) {
        this.data = data;
    }

    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section;
    }

}
