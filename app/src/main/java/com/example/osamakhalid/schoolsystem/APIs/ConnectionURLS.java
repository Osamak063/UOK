package com.example.osamakhalid.schoolsystem.APIs;

/**
 * Created by HAMI on 06/10/2017.
 */

public class ConnectionURLS {

    public static final String BASE_URL = "http://demo.simsportal.com/api/";  //Base URL

    public static final String LOGIN_URL = "login/user/";
    public static final String EXAMRESULT_URL = "student/results/type/Student/username/student01/lang/english";
    public static final String NEWS_AND_EVENTS_URL = "student/news_and_events/";

    public static final String HOLIDAY_URL = "student/holiday/type/Student/lang/english";
    public static final String NOTICE_BOARD_URL = "student/notice_board";

    public static final String HOMEWORK_URL = "http://demo.simsportal.com/api/student/homework_by_date/";

    public static final String ATTENDANCE_URL = "student/student_attendence/id/1/type/Student/username/student01/lang/english";
    public static final String EXAM_SCHEDULE_URL = "student/exams_schedule/username/student01/type/Student";
    public static final String SUBJECTS_URL = "student/subjects/username/student01/type/Student";

    public static final String TEACHER_DETAILS_URL = "http://demo.simsportal.com/api/student/teachers/";

    public static final String TEACHER_PERSONAL_PROFILE = "http://demo.simsportal.com/api/student/teacher_profile/";

    public static final String MESSAGE_INBOX_URL = "student/message_inbox/";
    public static final String MESSAGE_SENT_URL = "student/message_sent/";
    public static final String MESSAGE_FAV_URL = "student/fav_message/";
    public static final String MESSAGE_TRASH_URL = "student/trash_message/";
    public static final String MESSAGE_CHAT_URL = "student/message_view/";
}
