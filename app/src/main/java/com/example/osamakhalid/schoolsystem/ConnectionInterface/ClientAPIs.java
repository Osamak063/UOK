package com.example.osamakhalid.schoolsystem.ConnectionInterface;

import com.example.osamakhalid.schoolsystem.APIs.ConnectionURLS;
import com.example.osamakhalid.schoolsystem.Model.AlertResponse_Model;
import com.example.osamakhalid.schoolsystem.Model.AttendanceResponse;
import com.example.osamakhalid.schoolsystem.Model.Exam_Model;
import com.example.osamakhalid.schoolsystem.Model.HolidayResponseList;
import com.example.osamakhalid.schoolsystem.Model.Homework_Model;
import com.example.osamakhalid.schoolsystem.Model.LoginResponse;
import com.example.osamakhalid.schoolsystem.Model.NewsAndEventsResponseList;
import com.example.osamakhalid.schoolsystem.Model.SubjectResponseList;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;


public interface ClientAPIs {

    @FormUrlEncoded

    @POST(ConnectionURLS.LOGIN_URL)
    Call<LoginResponse> loginUser(@Field("username") String userName, @Field("password") String password, @Header("Authorization") String authHeader);

    @GET(ConnectionURLS.EXAMRESULT_URL)
    Call<Exam_Model> examResult(@Header("Authorization") String authHeader);

    @GET(ConnectionURLS.NEWS_AND_EVENTS_URL)
    Call<NewsAndEventsResponseList> getNewsAndEvents(@Header("Authorization") String authHeader);

    @GET(ConnectionURLS.HOLIDAY_URL)
    Call<HolidayResponseList> getHolidays(@Query("type") String type, @Query("lang") String lang, @Header("Authorization") String authHeader);

    @GET(ConnectionURLS.HOMEWORK_URL)
    Call<Homework_Model> getHomeWOrk(@Query("date") String userName, @Header("Authorization") String authHeader);

    @GET(ConnectionURLS.NOTICE_BOARD_URL)
    Call<AlertResponse_Model> getNoticeData(@Header("Authorization") String authHeader);

    @GET(ConnectionURLS.SUBJECTS_URL)
    Call<SubjectResponseList> getSubjects(@Query("type") String type, @Query("username") String username, @Header("Authorization") String authHeader);

    @GET(ConnectionURLS.ATTENDANCE_URL)
    Call<AttendanceResponse> getAttendance(@Query("id") String id, @Query("type") String type, @Query("username") String username, @Query("lang") String lang, @Query("monthyear") String monthYear, @Header("Authorization") String authHeader);
}

