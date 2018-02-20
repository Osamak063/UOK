package com.example.osamakhalid.schoolsystem.Model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Osama Khalid on 2/20/2018.
 */

public class HolidayResponseList {
    @SerializedName("holiday_data")
    private List<HolidayResponse> holidayList;

    public List<HolidayResponse> getHolidayList() {
        return holidayList;
    }
}
