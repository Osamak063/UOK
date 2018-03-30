package com.example.osamakhalid.schoolsystem.Activites;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.osamakhalid.schoolsystem.BaseConnection.RetrofitInitialize;
import com.example.osamakhalid.schoolsystem.ConnectionInterface.ClientAPIs;
import com.example.osamakhalid.schoolsystem.Consts.Values;
import com.example.osamakhalid.schoolsystem.GlobalCalls.CommonCalls;
import com.example.osamakhalid.schoolsystem.Model.ExamResult_Data;
import com.example.osamakhalid.schoolsystem.Model.ExamResult_Model;
import com.example.osamakhalid.schoolsystem.Model.Exam_Model;
import com.example.osamakhalid.schoolsystem.Model.LoginResponse;
import com.example.osamakhalid.schoolsystem.Model.ParentLoginResponse;
import com.example.osamakhalid.schoolsystem.Model.ParentModels.ParentStudentData;
import com.example.osamakhalid.schoolsystem.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ExamResult_Category extends AppCompatActivity implements View.OnClickListener {

    Button first_term, second_term, final_term;
    public static String exam_name;
    public static List<ExamResult_Data> final_exam, first_exam, second_exam;
    ProgressDialog progressDialog;
    private String username;
    private List<ParentStudentData> parentStudentDataList;
    private List<String> childrenUsernames;
    Spinner exam_spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exam_result__category);
        first_term = findViewById(R.id.first_term_exam);
        second_term = findViewById(R.id.second_term_exam);
        final_term = findViewById(R.id.final_exam);
        exam_spinner = findViewById(R.id.examcategory_spinner);
        parentStudentDataList = CommonCalls.getChildrenOfParentList(ExamResult_Category.this);
        childrenUsernames = new ArrayList<>();
        for (ParentStudentData data : parentStudentDataList) {
            childrenUsernames.add(data.getName());
        }

        if (CommonCalls.getUserType(this).equals(Values.TYPE_PARENT)) {

            exam_spinner.setVisibility(View.VISIBLE);
            ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter
                    (ExamResult_Category.this, android.R.layout.simple_spinner_item, childrenUsernames);
            spinnerArrayAdapter.setDropDownViewResource(android.R.layout
                    .simple_spinner_dropdown_item);
            exam_spinner.setAdapter(spinnerArrayAdapter);
            exam_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    Log.e("Username", parentStudentDataList.get(i).getUsername());
                    username = parentStudentDataList.get(i).getUsername();

                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });

        }

        first_term.setOnClickListener(this);
        second_term.setOnClickListener(this);
        final_term.setOnClickListener(this);

    }


    @Override
    public void onClick(View view) {
        progressDialog = CommonCalls.createDialouge(this, "", Values.DIALOGUE_MSG);

        switch (view.getId()) {

            case R.id.first_term_exam:
                first_termExams("First term exam");
                break;

            case R.id.second_term_exam:
                second_termExams("Second term exam");
                break;

            case R.id.final_exam:
                final_termExams("Final Exams");
                break;
        }


    }

    private void final_termExams(String e_name) {
      //  if (username != null) {
            getResultData(e_name);
     //   }

    }

    private void second_termExams(String e_name) {
     //   if (username != null) {
            getResultData(e_name);
     //   }
    }

    private void first_termExams(String e_name) {
     //   if (username != null) {
            getResultData(e_name);
      //  }
    }

    //fetching data from server
    public void getResultData(final String exam__name) {

        Retrofit retrofit = RetrofitInitialize.getApiClient();
        ClientAPIs clientAPIs = retrofit.create(ClientAPIs.class);
        String base = null;
        if (CommonCalls.getUserType(ExamResult_Category.this).equals(Values.TYPE_PARENT)) {
            ParentLoginResponse loginResponse = CommonCalls.getParentData(ExamResult_Category.this);
            base = loginResponse.getUsername() + ":" + loginResponse.getPassword();
        } else if (CommonCalls.getUserType(ExamResult_Category.this).equals(Values.TYPE_STUDENT)) {
            LoginResponse loginResponse = CommonCalls.getUserData(ExamResult_Category.this);
            base = loginResponse.getUsername() + ":" + loginResponse.getPassword();
            username = loginResponse.getUsername();

        }
        String authHeader = "Basic " + Base64.encodeToString(base.getBytes(), Base64.NO_WRAP);
        Call<Exam_Model> call = clientAPIs.examResult(username, Values.LANGUAGE, authHeader);

        call.enqueue(new Callback<Exam_Model>() {

            @Override
            public void onResponse(Call<Exam_Model> call, Response<Exam_Model> response) {

                if (response.isSuccessful()) {

                    Exam_Model exam_model = response.body();
                    if (exam_model != null) {
                        if (exam_model.getExams() != null) {

                            List<ExamResult_Model> examResultModel = exam_model.getExams();

                            if (examResultModel.get(0).getExamName().equals(exam__name)) {
                                exam_name = examResultModel.get(0).getExamName();
                                final_exam = examResultModel.get(0).getData();
                                progressDialog.dismiss();
                                Intent i = new Intent(ExamResult_Category.this, Exam_Result.class);
                                startActivity(i);
                            } else if (examResultModel.get(1).getExamName().contains(exam__name)) {

                                exam_name = examResultModel.get(1).getExamName();
                                first_exam = examResultModel.get(1).getData();
                                progressDialog.dismiss();
                                Intent i = new Intent(ExamResult_Category.this, Exam_Result.class);
                                startActivity(i);
                            } else if (examResultModel.get(2).getExamName().contains(exam__name)) {
                                exam_name = examResultModel.get(2).getExamName();
                                second_exam = examResultModel.get(2).getData();
                                progressDialog.dismiss();
                                Intent i = new Intent(ExamResult_Category.this, Exam_Result.class);
                                startActivity(i);

                            }
                        } else {
                            progressDialog.dismiss();
                            Toast.makeText(ExamResult_Category.this, Values.DATA_ERROR, Toast.LENGTH_SHORT).show();
                        }
                    }else {
                        progressDialog.dismiss();
                        Toast.makeText(ExamResult_Category.this, Values.DATA_ERROR, Toast.LENGTH_SHORT).show();
                    }

                }

            }

            @Override
            public void onFailure(Call<Exam_Model> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(ExamResult_Category.this, Values.SERVER_ERROR, Toast.LENGTH_SHORT).show();

            }
        });

    }


}
