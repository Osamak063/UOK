package com.example.osamakhalid.schoolsystem.ConnectionInterface;

import com.example.osamakhalid.schoolsystem.APIs.ConnectionURLS;
import com.example.osamakhalid.schoolsystem.Model.AlertResponse_Model;
import com.example.osamakhalid.schoolsystem.Model.AllBooks_Model;
import com.example.osamakhalid.schoolsystem.Model.AttendanceResponse;
import com.example.osamakhalid.schoolsystem.Model.ChatResponse;
import com.example.osamakhalid.schoolsystem.Model.ExamScheduleResponseList;
import com.example.osamakhalid.schoolsystem.Model.Exam_Model;
import com.example.osamakhalid.schoolsystem.Model.FeesResponseList;
import com.example.osamakhalid.schoolsystem.Model.GalleryResponse_Model;
import com.example.osamakhalid.schoolsystem.Model.HolidayResponseList;
import com.example.osamakhalid.schoolsystem.Model.Homework_Model;
import com.example.osamakhalid.schoolsystem.Model.LeavesResponseList;
import com.example.osamakhalid.schoolsystem.Model.Libray_Model;
import com.example.osamakhalid.schoolsystem.Model.LoginResponse;
import com.example.osamakhalid.schoolsystem.Model.MessagesFavResponseList;
import com.example.osamakhalid.schoolsystem.Model.MessagesInboxResponseList;
import com.example.osamakhalid.schoolsystem.Model.MessagesSentResponseList;
import com.example.osamakhalid.schoolsystem.Model.MessagesTrashResponseList;
import com.example.osamakhalid.schoolsystem.Model.NewsAndEventsResponseList;
import com.example.osamakhalid.schoolsystem.Model.SubjectResponseList;
import com.example.osamakhalid.schoolsystem.Model.SubmitLeaveResponse;
import com.example.osamakhalid.schoolsystem.Model.SyllabusResponse_Model;
import com.example.osamakhalid.schoolsystem.Model.TeacherPersonalProfile;
import com.example.osamakhalid.schoolsystem.Model.Teacher_Model;
import com.example.osamakhalid.schoolsystem.Model.TimeTable_Model;
import com.example.osamakhalid.schoolsystem.Model.TransportResponse_Model;
import com.example.osamakhalid.schoolsystem.Model.Transport_Model;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;
import retrofit2.http.Streaming;
import retrofit2.http.Url;


public interface ClientAPIs {

    @FormUrlEncoded
    @POST(ConnectionURLS.LOGIN_URL)
    Call<LoginResponse> loginUser(@Field("username") String userName, @Field("password") String password, @Header("Authorization") String authHeader);

    @GET(ConnectionURLS.EXAMRESULT_URL)
    Call<Exam_Model> examResult(@Header("Authorization") String authHeader);

    @GET(ConnectionURLS.NEWS_AND_EVENTS_URL)
    Call<NewsAndEventsResponseList> getNewsAndEvents(@Header("Authorization") String authHeader);

    @GET(ConnectionURLS.HOLIDAY_URL)
    Call<HolidayResponseList> getHolidays(@Query("lang") String lang, @Header("Authorization") String authHeader);

    @GET(ConnectionURLS.HOMEWORK_URL)
    Call<Homework_Model> getHomeWOrk(@Query("date") String date, @Query("username") String username, @Header("Authorization") String authHeader);

    @GET(ConnectionURLS.NOTICE_BOARD_URL)
    Call<AlertResponse_Model> getNoticeData(@Header("Authorization") String authHeader);

    @GET(ConnectionURLS.SUBJECTS_URL)
    Call<SubjectResponseList> getSubjects(@Query("username") String username, @Header("Authorization") String authHeader);

    @GET(ConnectionURLS.ATTENDANCE_URL)
    Call<AttendanceResponse> getAttendance(@Query("id") String id, @Query("username") String username, @Query("lang") String lang, @Query("monthyear") String monthYear, @Header("Authorization") String authHeader);

    @GET(ConnectionURLS.EXAM_SCHEDULE_URL)
    Call<ExamScheduleResponseList> getExamSchedule(@Query("username") String username, @Header("Authorization") String authHeader);

    @GET(ConnectionURLS.TEACHER_DETAILS_URL)
    Call<Teacher_Model> getCourseTeacherData(@Query("username") String username, @Header("Authorization") String authHeader);

    @GET(ConnectionURLS.TEACHER_PERSONAL_PROFILE)
    Call<TeacherPersonalProfile> getPersonalProfile(@Query("id") String id, @Header("Authorization") String authHeader);


    @GET(ConnectionURLS.SYLLABUS_URL)
    Call<SyllabusResponse_Model> getSyllabus(@Query("username") String username, @Query("lang") String lang, @Query("monthyear") String monthyear, @Header("Authorization") String authHeader);

    @GET(ConnectionURLS.TRANSPORT_ALL)
    Call<TransportResponse_Model> getTranportData(@Header("Authorization") String authHeader);

    @GET(ConnectionURLS.TRANSPORT_PERSONAL)
    Call<Transport_Model> getPersonalTranportData(@Query("studentID") String studentID, @Header("Authorization") String authHeade);

    @GET(ConnectionURLS.MESSAGE_INBOX_URL)
    Call<MessagesInboxResponseList> getMessagesInbox(@Query("username") String username, @Header("Authorization") String authHeader);

    @GET(ConnectionURLS.MESSAGE_SENT_URL)
    Call<MessagesSentResponseList> getMessagesSent(@Query("username") String username, @Header("Authorization") String authHeader);

    @GET(ConnectionURLS.MESSAGE_FAV_URL)
    Call<MessagesFavResponseList> getMessagesFav(@Query("username") String username, @Header("Authorization") String authHeader);

    @GET(ConnectionURLS.MESSAGE_TRASH_URL)
    Call<MessagesTrashResponseList> getMessageTrash(@Query("username") String username,
                                                    @Header("Authorization") String authHeader);

    @GET(ConnectionURLS.MESSAGE_CHAT_URL)
    Call<ChatResponse> getChat(@Query("messageid") String messageId, @Query("userid") String userId,
                               @Header("Authorization") String authHeader);

   @GET(ConnectionURLS.BOOK_ISSUE_DATE)
    Call<Libray_Model> getLibraryInfo(@Query("username") String username,@Header("Authorization") String authHeader);

    @GET(ConnectionURLS.ALL_BOOKS_RECORD)
    Call<AllBooks_Model> getAllBooks(@Header("Authorization") String authHeader);

    @Streaming
    @GET
    Call<ResponseBody> downloadFileWithDynamicUrlAsync(@Url String fileUrl);

    @FormUrlEncoded
    @POST(ConnectionURLS.SUBMIT_LEAVES_URL)
    Call<SubmitLeaveResponse> submitLeave(@Field("title") String title, @Field("details") String details,
                                          @Field("fdate") String fDate, @Field("tdate") String toDate,
                                          @Field("fromusername") String username, @Field("to") String to,
                                          @Header("Authorization") String authHeader);

    @GET(ConnectionURLS.LEAVES_URL)
    Call<LeavesResponseList> getLeaves(@Query("username") String username, @Query("type") String type,
                                       @Header("Authorization") String authHeader);

    @GET(ConnectionURLS.MEDIALIBRARY_URL)
    Call<GalleryResponse_Model> getPhotoGalleryImages(@Query("user") String username,
                                                      @Header("Authorization") String authHeader);


    @GET(ConnectionURLS.FEES_AND_INVOICE_URL)
    Call<FeesResponseList> getFeesAndInvoice(@Query("username") String username, @Query("lang") String lang,
                                             @Query("limit") int limit, @Query("offset") int offset,
                                             @Header("Authorization") String authHeader);

    @GET(ConnectionURLS.TIME_TABLE_URL)
    Call<TimeTable_Model> getTimeTable(@Query("username") String username, @Query("lang") String lang,
                                       @Header("Authorization") String authHeader);


}

