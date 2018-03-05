package com.example.osamakhalid.schoolsystem.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Osama Khalid on 3/3/2018.
 */

public class LeavesResponseList {
    @SerializedName("leaves_data")
    @Expose
    private List<LeavesResponse> leavesData = null;

    public List<LeavesResponse> getLeavesData() {
        return leavesData;
    }

    public void setLeavesData(List<LeavesResponse> leavesData) {
        this.leavesData = leavesData;
    }
}
