package com.example.osamakhalid.schoolsystem.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Osama Khalid on 2/21/2018.
 */

public class SubjectResponseList {
    @SerializedName("subject_data")
    @Expose
    private List<SubjectResponse> subjectData = null;

    public List<SubjectResponse> getSubjectData() {
        return subjectData;
    }

    public void setSubjectData(List<SubjectResponse> subjectData) {
        this.subjectData = subjectData;
    }

}
