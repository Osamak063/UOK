package com.example.osamakhalid.schoolsystem.Fragments;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.osamakhalid.schoolsystem.BaseConnection.RetrofitInitialize;
import com.example.osamakhalid.schoolsystem.ConnectionInterface.ClientAPIs;
import com.example.osamakhalid.schoolsystem.Consts.Values;
import com.example.osamakhalid.schoolsystem.GlobalCalls.CommonCalls;
import com.example.osamakhalid.schoolsystem.Interfaces.MyDataInterface;
import com.example.osamakhalid.schoolsystem.Model.LeavesResponse;
import com.example.osamakhalid.schoolsystem.Model.LoginResponse;
import com.example.osamakhalid.schoolsystem.Model.ParentLoginResponse;
import com.example.osamakhalid.schoolsystem.Model.ParentModels.ParentStudentData;
import com.example.osamakhalid.schoolsystem.Model.SubmitLeaveResponse;
import com.example.osamakhalid.schoolsystem.Model.TeacherData_Model;
import com.example.osamakhalid.schoolsystem.Model.Teacher_Model;
import com.example.osamakhalid.schoolsystem.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;


public class SubmitLeaveFragment extends Fragment implements MyDataInterface{

    public List<String> teachersUsername;
    Spinner teacher_spinner;
    EditText title, fromDate, toDate, details;
    Button submitLeave;
    String spinnerValue,username;
    public RecyclerView recyclerView;
    public RecyclerView.Adapter adapter;
    public List<LeavesResponse> listItems;
    private Retrofit retrofit;
    private ClientAPIs clientAPIs;
    Spinner submit_leave_spinner;
    private List<TeacherData_Model> teachers_list;
    private List<ParentStudentData> parentStudentDataList;
    private ArrayList<String> childrenUsernames;
    ArrayAdapter<String> ArrayAdapter;
    ProgressDialog progressDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if (container != null) {
            container.removeAllViews();
        }
        teachers_list = new ArrayList<>();
        teachersUsername = new ArrayList<>();
//        Bundle args = getArguments();
     //       getTeacherData();

        View view = inflater.inflate(R.layout.fragment_submit_leave2, container, false);
        teacher_spinner = view.findViewById(R.id.teacher_spinner);
        title = view.findViewById(R.id.submit_leave_title);
        fromDate = view.findViewById(R.id.from_date);
        toDate = view.findViewById(R.id.to_date);
        details = view.findViewById(R.id.leave_detail);
        submitLeave = view.findViewById(R.id.submit_leave);
//        if (teachersUsername.size() > 0) {
//            spinnerValue = teachersUsername.get(0);
//        }
        submit_leave_spinner = view.findViewById(R.id.submitleave_spinner);
        parentStudentDataList = CommonCalls.getChildrenOfParentList(getActivity());
        childrenUsernames = new ArrayList<>();
        for (ParentStudentData data : parentStudentDataList) {
            childrenUsernames.add(data.getName());
        }
        if (CommonCalls.getUserType(getActivity()).equals(Values.TYPE_PARENT)) {

            submit_leave_spinner.setVisibility(View.VISIBLE);
            ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter
                    (getActivity(), android.R.layout.simple_spinner_item, childrenUsernames);
            spinnerArrayAdapter.setDropDownViewResource(android.R.layout
                    .simple_spinner_dropdown_item);
            submit_leave_spinner.setAdapter(spinnerArrayAdapter);
            submit_leave_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    username = parentStudentDataList.get(i).getUsername();
                    if ((username!= null)){
                        getTeacherData();
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });

        }else if(CommonCalls.getUserType(getActivity()).equals(Values.TYPE_STUDENT)){
            getTeacherData();
        }


//        if (CommonCalls.getUserType(getActivity()).equals(Values.TYPE_PARENT)) {
//
//            submit_leave_spinner.setVisibility(View.VISIBLE);
//            ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter
//                    (getActivity(), android.R.layout.simple_spinner_item, childrenUsernames);
//            spinnerArrayAdapter.setDropDownViewResource(android.R.layout
//                    .simple_spinner_dropdown_item);
//            submit_leave_spinner.setAdapter(spinnerArrayAdapter);
//            submit_leave_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//                @Override
//                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//                    username = parentStudentDataList.get(i).getUsername();
//                    if ((username!= null)){
//                        listItems.clear();
//                        getTeacherData();
//                    }
//                }
//
//                @Override
//                public void onNothingSelected(AdapterView<?> adapterView) {
//
//                }
//            });
//
//        }
//        else if(CommonCalls.getUserType(getActivity()).equals(Values.TYPE_STUDENT)){
//            getData();
//        }




        submitLeave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressDialog = CommonCalls.createDialouge(getActivity(), "", Values.DIALOGUE_MSG);
                getData();
                details.setText("");
                title.setText("");
                toDate.setText("");
                fromDate.setText("");

            }
        });
        teacher_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//                Log.e("TeacherName",teachers_list.get(i).getTeacherUsername());
                spinnerValue = teachers_list.get(i).getTeacherUsername();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        return view;
    }

    public void getData() {
        retrofit = RetrofitInitialize.getApiClient();
        clientAPIs = retrofit.create(ClientAPIs.class);
        String base = null;
        if (CommonCalls.getUserType(getActivity()).equals(Values.TYPE_PARENT)) {
            ParentLoginResponse loginResponse = CommonCalls.getParentData(getActivity());
            base = loginResponse.getUsername() + ":" + loginResponse.getPassword();
        } else if (CommonCalls.getUserType(getActivity()).equals(Values.TYPE_STUDENT)) {
            LoginResponse loginResponse = CommonCalls.getUserData(getActivity());
            base = loginResponse.getUsername() + ":" + loginResponse.getPassword();
            username = loginResponse.getUsername();
        }
        String authHeader = "Basic " + Base64.encodeToString(base.getBytes(), Base64.NO_WRAP);
        Call<SubmitLeaveResponse> call = clientAPIs.submitLeave(title.getText().toString(), details.getText().toString(), fromDate.getText().toString(),
                toDate.getText().toString(), username, spinnerValue, authHeader);
        call.enqueue(new Callback<SubmitLeaveResponse>() {
            @Override
            public void onResponse(Call<SubmitLeaveResponse> call, Response<SubmitLeaveResponse> response) {
                if (response.isSuccessful()) {
                    SubmitLeaveResponse submitLeaveResponse = response.body();
                    progressDialog.dismiss();
                    Toast.makeText(getActivity(), submitLeaveResponse.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<SubmitLeaveResponse> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(getActivity(), Values.DATA_ERROR, Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void getTeacherData() {

        Retrofit retrofit = RetrofitInitialize.getApiClient();
        ClientAPIs clientAPIs = retrofit.create(ClientAPIs.class);
        String base = null;
        if (CommonCalls.getUserType(getActivity()).equals(Values.TYPE_PARENT)) {
            ParentLoginResponse loginResponse = CommonCalls.getParentData(getActivity());
            base = loginResponse.getUsername() + ":" + loginResponse.getPassword();
        } else if (CommonCalls.getUserType(getActivity()).equals(Values.TYPE_STUDENT)) {
            LoginResponse loginResponse = CommonCalls.getUserData(getActivity());
            base = loginResponse.getUsername() + ":" + loginResponse.getPassword();
            username = loginResponse.getUsername();

        }
        final String authHeader = "Basic " + Base64.encodeToString(base.getBytes(), Base64.NO_WRAP);
        Call<Teacher_Model> call = clientAPIs.getCourseTeacherData(username, authHeader);
        call.enqueue(new Callback<Teacher_Model>() {
            @Override
            public void onResponse(Call<Teacher_Model> call, Response<Teacher_Model> response) {

                if (response.isSuccessful()) {

                    Teacher_Model teacher_model = response.body();
                    if (teacher_model != null ) {
                        if(teacher_model.getTeacherData()!=null){
                            teachers_list.addAll(teacher_model.getTeacherData());
                            for (TeacherData_Model d: teacher_model.getTeacherData()) {
                                Log.e("TeacherName",d.getName());
                                teachersUsername.add(d.getName());
                            }
                            ArrayAdapter = new ArrayAdapter
                                    (getActivity(), android.R.layout.simple_spinner_item, teachersUsername);
                            ArrayAdapter.setDropDownViewResource(android.R.layout
                                    .simple_spinner_dropdown_item);
                            teacher_spinner.setAdapter(ArrayAdapter);

                        }else{
                            Toast.makeText(getActivity(), "Teachers not available yet.", Toast.LENGTH_SHORT).show();

                        }
                    } else {
                        Toast.makeText(getActivity(), "Teachers not available yet.", Toast.LENGTH_SHORT).show();
                    }

                }

            }

            @Override
            public void onFailure(Call<Teacher_Model> call, Throwable t) {

                Toast.makeText(getActivity(), Values.SERVER_ERROR, Toast.LENGTH_SHORT).show();

            }
        });
    }


    @Override
    public void onLond(List<TeacherData_Model> list) {
        if(list.size()>0){
            for(int i=0;i<list.size();i++){
                teachersUsername.add(list.get(i).getName());
            }
        }

    }
}
