package com.example.osamakhalid.schoolsystem.Model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Osama Khalid on 2/19/2018.
 */

public class NewsAndEventsResponseList {
    @SerializedName("data")
    private List<NewsAndEventsResponse> newsAndEventsList;

    public List<NewsAndEventsResponse> getNewsAndEventsLists() {
        return newsAndEventsList;
    }
}
