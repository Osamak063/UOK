package com.example.osamakhalid.schoolsystem.ConnectionInterface;

import com.example.osamakhalid.schoolsystem.APIs.ConnectionURLS;
import com.example.osamakhalid.schoolsystem.Model.AlertResponse_Model;
import com.example.osamakhalid.schoolsystem.Model.Exam_Model;
import com.example.osamakhalid.schoolsystem.Model.LoginResponse;
import com.example.osamakhalid.schoolsystem.Model.NewsAndEventsResponseList;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;


public interface ClientAPIs {

    @FormUrlEncoded

    @POST(ConnectionURLS.LOGIN_URL)
    Call<LoginResponse> loginUser(@Field("username") String userName, @Field("password") String password, @Header("Authorization") String authHeader);

    @GET(ConnectionURLS.EXAMRESULT_URL)
    Call<Exam_Model> examResult(@Header("Authorization") String authHeader);

    @GET(ConnectionURLS.NEWS_AND_EVENTS_URL)
    Call<NewsAndEventsResponseList> getNewsAndEvents(@Header("Authorization") String authHeader);

    @GET(ConnectionURLS.NOTICE_BOARD_URL)
    Call<AlertResponse_Model> getNoticeData(@Header("Authorization") String authHeader);

}

