package com.example.osamakhalid.schoolsystem.Fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
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
import com.example.osamakhalid.schoolsystem.Model.LeavesResponse;
import com.example.osamakhalid.schoolsystem.Model.LeavesResponseList;
import com.example.osamakhalid.schoolsystem.Model.LoginResponse;
import com.example.osamakhalid.schoolsystem.Model.SubmitLeaveResponse;
import com.example.osamakhalid.schoolsystem.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;


public class SubmitLeaveFragment extends Fragment {

    public List<String> teachersUsername;
    Spinner spinner;
    EditText title, fromDate, toDate, details;
    Button submitLeave;
    String spinnerValue;
    public RecyclerView recyclerView;
    public RecyclerView.Adapter adapter;
    public List<LeavesResponse> listItems;
    private Retrofit retrofit;
    private ClientAPIs clientAPIs;
    LoginResponse userData;
    ProgressDialog progressDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if (container != null) {
            container.removeAllViews();
        }
        teachersUsername = new ArrayList<>();
        Bundle args = getArguments();
        if (args != null) {
            teachersUsername = args.getStringArrayList("usernames");
        }
        View view = inflater.inflate(R.layout.fragment_submit_leave2, container, false);
        spinner = view.findViewById(R.id.teacher_spinner);
        title = view.findViewById(R.id.submit_leave_title);
        fromDate = view.findViewById(R.id.from_date);
        toDate = view.findViewById(R.id.to_date);
        details = view.findViewById(R.id.leave_detail);
        submitLeave = view.findViewById(R.id.submit_leave);
        if (teachersUsername.size() > 0) {
            spinnerValue = teachersUsername.get(0);
        }
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter
                (getActivity(), android.R.layout.simple_spinner_item, teachersUsername);
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout
                .simple_spinner_dropdown_item);
        spinner.setAdapter(spinnerArrayAdapter);
        submitLeave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressDialog = CommonCalls.createDialouge(getActivity(), "", Values.DIALOGUE_MSG);
                userData = CommonCalls.getUserData(getActivity());
                String base = userData.getUsername() + ":" + userData.getPassword();
                String authHeader = "Basic " + Base64.encodeToString(base.getBytes(), Base64.NO_WRAP);
                getData(authHeader);
                details.setText("");
                title.setText("");
                toDate.setText("");
                fromDate.setText("");
            }
        });
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                spinnerValue = teachersUsername.get(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        return view;
    }

    public void getData(String authHeader) {
        retrofit = RetrofitInitialize.getApiClient();
        clientAPIs = retrofit.create(ClientAPIs.class);
        Call<SubmitLeaveResponse> call = clientAPIs.submitLeave(title.getText().toString(), details.getText().toString(), fromDate.getText().toString(),
                toDate.getText().toString(), userData.getUsername(), spinnerValue, authHeader);
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
                Toast.makeText(getActivity(), "Sorry something went wrong.", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
