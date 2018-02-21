package com.example.osamakhalid.schoolsystem.GlobalCalls;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;

import com.example.osamakhalid.schoolsystem.Model.LoginResponse;
import com.google.gson.Gson;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by HAMI on 19/02/2018.
 */

public class CommonCalls extends AppCompatActivity {

    public static void saveUserData(LoginResponse loginResponse,Context context) {
        SharedPreferences mPrefs = context.getSharedPreferences("UserData", 0);
        SharedPreferences.Editor prefsEditor = mPrefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(loginResponse);
        prefsEditor.putString("UserObject", json);
        prefsEditor.commit();
    }

    public static LoginResponse getUserData(Context context) {
        SharedPreferences mPrefs = context.getSharedPreferences("UserData", 0);
        Gson gson = new Gson();
        String json = mPrefs.getString("UserObject", "");
        LoginResponse userData = gson.fromJson(json, LoginResponse.class);
        return userData;
    }

    public static String getCurrentDate(){
        Date now = new Date();
        Date alsoNow = Calendar.getInstance().getTime();
        String nowAsString = new SimpleDateFormat("yyyy-MM-dd").format(now);

    return nowAsString;

    }


}
