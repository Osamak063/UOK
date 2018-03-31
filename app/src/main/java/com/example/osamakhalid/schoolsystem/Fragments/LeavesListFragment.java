package com.example.osamakhalid.schoolsystem.Fragments;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.osamakhalid.schoolsystem.Activites.DashboardActivity;
import com.example.osamakhalid.schoolsystem.Adapters.LeavesAdapter;
import com.example.osamakhalid.schoolsystem.BaseConnection.RetrofitInitialize;
import com.example.osamakhalid.schoolsystem.ConnectionInterface.ClientAPIs;
import com.example.osamakhalid.schoolsystem.Consts.Values;
import com.example.osamakhalid.schoolsystem.GlobalCalls.CommonCalls;
import com.example.osamakhalid.schoolsystem.Model.LeavesResponse;
import com.example.osamakhalid.schoolsystem.Model.LeavesResponseList;
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


public class LeavesListFragment extends Fragment {
    public RecyclerView recyclerView;
    public RecyclerView.Adapter adapter;
    public List<LeavesResponse> listItems;
    private Retrofit retrofit;
    private ClientAPIs clientAPIs;
    LoginResponse userData;
    ProgressDialog progressDialog;
    private Spinner leave_spinner;
    private List<ParentStudentData> parentStudentDataList;
    private ArrayList<String> childrenUsernames;
    private String username,userType;
    Toolbar toolbar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if (container != null) {
            container.removeAllViews();
        }
        View view = inflater.inflate(R.layout.fragment_leaves_list2, container, false);
        //Setting up Toolbar
        toolbar = view.findViewById(R.id.toolBar);
        toolbar.setTitle("Leave");
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity) getActivity()).getSupportActionBar();
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), DashboardActivity.class));
            }
        });
        listItems = new ArrayList<>();
        progressDialog = CommonCalls.createDialouge(getActivity(), "", Values.DIALOGUE_MSG);
        recyclerView = view.findViewById(R.id.leaves_recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        leave_spinner = view.findViewById(R.id.leave_spinner);

        if (CommonCalls.getUserType(getActivity()).equals(Values.TYPE_PARENT)) {
            parentStudentDataList = CommonCalls.getChildrenOfParentList(getActivity());
            childrenUsernames = new ArrayList<>();
            for (ParentStudentData data : parentStudentDataList) {
                childrenUsernames.add(data.getName());
            }

            leave_spinner.setVisibility(View.VISIBLE);
            ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter
                    (getActivity(), android.R.layout.simple_spinner_item, childrenUsernames);
            spinnerArrayAdapter.setDropDownViewResource(android.R.layout
                    .simple_spinner_dropdown_item);
            leave_spinner.setAdapter(spinnerArrayAdapter);
            leave_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    username = parentStudentDataList.get(i).getUsername();
                    if ((username!= null)){
                        listItems.clear();
                       getData();
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });

        }
        else if(CommonCalls.getUserType(getActivity()).equals(Values.TYPE_STUDENT)){
            getData();
        }

        adapter = new LeavesAdapter(listItems, getActivity().getApplicationContext());
        recyclerView.setAdapter(adapter);
        return view;
    }

    public void getData() {
        retrofit = RetrofitInitialize.getApiClient();
        clientAPIs = retrofit.create(ClientAPIs.class);
        String base = null;
        if (CommonCalls.getUserType(getActivity()).equals(Values.TYPE_PARENT)) {
            ParentLoginResponse loginResponse = CommonCalls.getParentData(getActivity());
            base = loginResponse.getUsername() + ":" + loginResponse.getPassword();
            userType = loginResponse.getUsertype();
        } else if (CommonCalls.getUserType(getActivity()).equals(Values.TYPE_STUDENT)) {
            LoginResponse loginResponse = CommonCalls.getUserData(getActivity());
            base = loginResponse.getUsername() + ":" + loginResponse.getPassword();
            username = loginResponse.getUsername();
            userType = loginResponse.getUsertype();

        }
        String authHeader = "Basic " + Base64.encodeToString(base.getBytes(), Base64.NO_WRAP);
        Call<LeavesResponseList> call = clientAPIs.getLeaves(username,userType, authHeader);
        call.enqueue(new Callback<LeavesResponseList>() {
            @Override
            public void onResponse(Call<LeavesResponseList> call, Response<LeavesResponseList> response) {
                if (response.isSuccessful()) {
                    LeavesResponseList leavesList = response.body();
                    if (leavesList != null) {
                        if(leavesList.getLeavesData()!=null){
                            progressDialog.dismiss();
                            listItems.addAll(leavesList.getLeavesData());
                            adapter.notifyDataSetChanged();
                        }else{
                            progressDialog.dismiss();
                            adapter.notifyDataSetChanged();
                            Toast.makeText(getActivity(), Values.DATA_ERROR, Toast.LENGTH_SHORT).show();

                        }

                    } else {
                        progressDialog.dismiss();
                        Toast.makeText(getActivity(), Values.DATA_ERROR, Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<LeavesResponseList> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(getActivity(), Values.SERVER_ERROR, Toast.LENGTH_SHORT).show();
            }
        });
    }


}
