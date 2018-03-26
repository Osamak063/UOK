package com.example.osamakhalid.schoolsystem.Activites;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.osamakhalid.schoolsystem.BaseConnection.RetrofitInitialize;
import com.example.osamakhalid.schoolsystem.ConnectionInterface.ClientAPIs;
import com.example.osamakhalid.schoolsystem.Consts.Values;
import com.example.osamakhalid.schoolsystem.GlobalCalls.CommonCalls;
import com.example.osamakhalid.schoolsystem.Model.LoginResponse;
import com.example.osamakhalid.schoolsystem.Model.ParentLoginResponse;
import com.example.osamakhalid.schoolsystem.Model.TeacherPersonalProfile;
import com.example.osamakhalid.schoolsystem.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class TeacherProfile extends AppCompatActivity {
    TextView teacherName,teacher_ID,teacher_religon,activity_view_text,
    teacher_desigination,teacher_DOB,teacher_Email,teacher_address,teacher_gender,teacher_phone,teacher_joining_date;
    ImageView teacher_profile_pic;
    static String id;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_profile);
        Intent i= getIntent();
        progressDialog = CommonCalls.createDialouge(this,"",Values.DIALOGUE_MSG);
        id = i.getStringExtra("name");
        teacherName = findViewById(R.id.profile_teacher_name);
        teacher_ID = findViewById(R.id.teacher_id);
        teacher_desigination = findViewById(R.id.teacher_desigination);
        teacher_DOB = findViewById(R.id.teacher_DOB);
        teacher_Email = findViewById(R.id.teacher_email);
        teacher_address = findViewById(R.id.teacher_address);
        teacher_gender = findViewById(R.id.teacher_gender);
        teacher_phone = findViewById(R.id.teacher_phone);
        teacher_religon = findViewById(R.id.teacher_religon);
        teacher_joining_date= findViewById(R.id.teacher_joining);
        teacher_profile_pic = findViewById(R.id.teacher_profile_pic);
        activity_view_text = findViewById(R.id.profile_view_text);
        getTeacherProfile();
     //   teacherName.setText(name);
    }

    public void getTeacherProfile(){

        final Retrofit[] retrofit = {RetrofitInitialize.getApiClient()};
        ClientAPIs clientAPIs = retrofit[0].create(ClientAPIs.class);
        String base = null;
        if (CommonCalls.getUserType(TeacherProfile.this).equals(Values.TYPE_PARENT)) {
            ParentLoginResponse loginResponse = CommonCalls.getParentData(TeacherProfile.this);
            base = loginResponse.getUsername() + ":" + loginResponse.getPassword();
        } else if (CommonCalls.getUserType(TeacherProfile.this).equals(Values.TYPE_STUDENT)) {
            LoginResponse loginResponse = CommonCalls.getUserData(TeacherProfile.this);
            base = loginResponse.getUsername() + ":" + loginResponse.getPassword();

        }
        String authHeader = "Basic " + Base64.encodeToString(base.getBytes(), Base64.NO_WRAP);
        Call<TeacherPersonalProfile> call = clientAPIs.getPersonalProfile(id,authHeader);
        call.enqueue(new Callback<TeacherPersonalProfile>() {
            @Override
            public void onResponse(Call<TeacherPersonalProfile> call, Response<TeacherPersonalProfile> response) {
                if(response.isSuccessful()){
                    progressDialog.dismiss();
                    TeacherPersonalProfile teacherPersonalProfile = response.body();
                    activity_view_text.setText(teacherPersonalProfile.getName());
                    teacher_ID.setText(teacherPersonalProfile.getTeacherID());
                    teacherName.setText(teacherPersonalProfile.getName());
                    teacher_desigination.setText(teacherPersonalProfile.getDesignation());
                    teacher_DOB.setText(teacherPersonalProfile.getDateOfBirth());
                    teacher_Email.setText(teacherPersonalProfile.getEmail());
                    teacher_religon.setText(teacherPersonalProfile.getReligion());
                    teacher_gender.setText(teacherPersonalProfile.getSex());
                    teacher_address.setText(teacherPersonalProfile.getAddress());
                    teacher_phone.setText(teacherPersonalProfile.getPhone());
                    teacher_joining_date.setText(teacherPersonalProfile.getJoiningDate());
                    Glide.with(TeacherProfile.this).load(teacherPersonalProfile.getImage()).into(teacher_profile_pic);


                }

            }

            @Override
            public void onFailure(Call<TeacherPersonalProfile> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(TeacherProfile.this, Values.SERVER_ERROR,Toast.LENGTH_SHORT).show();
            }
        });


    }


}
