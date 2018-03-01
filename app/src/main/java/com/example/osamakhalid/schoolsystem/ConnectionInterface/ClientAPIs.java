package com.example.osamakhalid.schoolsystem.ConnectionInterface;

import com.example.osamakhalid.schoolsystem.APIs.ConnectionURLS;
import com.example.osamakhalid.schoolsystem.Model.AlertResponse_Model;
import com.example.osamakhalid.schoolsystem.Model.AttendanceResponse;
import com.example.osamakhalid.schoolsystem.Model.ChatResponse;
import com.example.osamakhalid.schoolsystem.Model.ExamScheduleResponseList;
import com.example.osamakhalid.schoolsystem.Model.Exam_Model;
import com.example.osamakhalid.schoolsystem.Model.HolidayResponseList;
import com.example.osamakhalid.schoolsystem.Model.Homework_Model;
import com.example.osamakhalid.schoolsystem.Model.LoginResponse;
import com.example.osamakhalid.schoolsystem.Model.MessagesFavResponseList;
import com.example.osamakhalid.schoolsystem.Model.MessagesInboxResponseList;
import com.example.osamakhalid.schoolsystem.Model.MessagesSentResponseList;
import com.example.osamakhalid.schoolsystem.Model.MessagesTrashResponse;
import com.example.osamakhalid.schoolsystem.Model.MessagesTrashResponseList;
import com.example.osamakhalid.schoolsystem.Model.NewsAndEventsResponseList;
import com.example.osamakhalid.schoolsystem.Model.SubjectResponseList;
import com.example.osamakhalid.schoolsystem.Model.TeacherPersonalProfile;
import com.example.osamakhalid.schoolsystem.Model.Teacher_Model;

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

    @GET(ConnectionURLS.EXAM_SCHEDULE_URL)
    Call<ExamScheduleResponseList> getExamSchedule(@Query("username") String username, @Query("type") String type, @Header("Authorization") String authHeader);

    @GET(ConnectionURLS.TEACHER_DETAILS_URL)
    Call<Teacher_Model> getCourseTeacherData(@Query("username") String username, @Query("type") String user_type, @Header("Authorization") String authHeader);

    @GET(ConnectionURLS.TEACHER_PERSONAL_PROFILE)
    Call<TeacherPersonalProfile> getPersonalProfile(@Query("id") String id, @Header("Authorization") String authHeader);

    @GET(ConnectionURLS.MESSAGE_INBOX_URL)
    Call<MessagesInboxResponseList> getMessagesInbox(@Query("username") String username, @Query("type") String type, @Header("Authorization") String authHeader);

    @GET(ConnectionURLS.MESSAGE_SENT_URL)
    Call<MessagesSentResponseList> getMessagesSent(@Query("username") String username, @Query("type") String type, @Header("Authorization") String authHeader);

    @GET(ConnectionURLS.MESSAGE_FAV_URL)
    Call<MessagesFavResponseList> getMessagesFav(@Query("username") String username, @Query("type") String type, @Header("Authorization") String authHeader);

    @GET(ConnectionURLS.MESSAGE_TRASH_URL)
    Call<MessagesTrashResponseList> getMessageTrash(@Query("username") String username, @Header("Authorization") String authHeader);

    @GET(ConnectionURLS.MESSAGE_CHAT_URL)
    Call<ChatResponse> getChat(@Query("messageid") String messageId, @Query("userid") String userId, @Query("type") String type, @Header("Authorization") String authHeader);

}

