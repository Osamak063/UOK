package com.example.osamakhalid.schoolsystem.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Osama Khalid on 3/7/2018.
 */

public class FeesResponseList {
    @SerializedName("count")
    @Expose
    private Integer count;
    @SerializedName("fee_data")
    @Expose
    private List<FeesResponse> feeData = null;

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public List<FeesResponse> getFeeData() {
        return feeData;
    }

    public void setFeeData(List<FeesResponse> feeData) {
        this.feeData = feeData;
    }
}
