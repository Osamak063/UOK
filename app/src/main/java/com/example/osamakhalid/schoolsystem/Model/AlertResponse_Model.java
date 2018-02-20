package com.example.osamakhalid.schoolsystem.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by HAMI on 20/02/2018.
 */

public class AlertResponse_Model {

    @SerializedName("notice_data")
    @Expose
    private List<Alert_Model> noticeData = null;

    public List<Alert_Model> getNoticeData() {
        return noticeData;
    }

    public void setNoticeData(List<Alert_Model> noticeData) {
        this.noticeData = noticeData;
    }
}
