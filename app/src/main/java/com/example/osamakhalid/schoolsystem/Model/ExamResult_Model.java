package com.example.osamakhalid.schoolsystem.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by HAMI on 17/01/2018.
 */

public class ExamResult_Model {

    @SerializedName("exam_name")
    @Expose
    private String examName;
    @SerializedName("data")
    @Expose
    private List<ExamResult_Data> data = null;

    public String getExamName() {
        return examName;
    }

    public void setExamName(String examName) {
        this.examName = examName;
    }

    public List<ExamResult_Data> getData() {
        return data;
    }

    public void setData(List<ExamResult_Data> data) {
        this.data = data;
    }


}
