package com.example.osamakhalid.schoolsystem.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by HAMI on 19/02/2018.
 */

public class Exam_Model {

    @SerializedName("exams")
    @Expose
    private List<ExamResult_Model> exams = null;

    public List<ExamResult_Model> getExams() {
        return exams;
    }

    public void setExams(List<ExamResult_Model> exams) {
        this.exams = exams;
    }
}
