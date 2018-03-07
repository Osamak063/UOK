package com.example.osamakhalid.schoolsystem.Fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.osamakhalid.schoolsystem.Activites.Leaves;
import com.example.osamakhalid.schoolsystem.Adapters.LeavesAdapter;
import com.example.osamakhalid.schoolsystem.BaseConnection.RetrofitInitialize;
import com.example.osamakhalid.schoolsystem.ConnectionInterface.ClientAPIs;
import com.example.osamakhalid.schoolsystem.Consts.Values;
import com.example.osamakhalid.schoolsystem.GlobalCalls.CommonCalls;
import com.example.osamakhalid.schoolsystem.Model.LeavesResponse;
import com.example.osamakhalid.schoolsystem.Model.LeavesResponseList;
import com.example.osamakhalid.schoolsystem.Model.LoginResponse;
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if (container != null) {
            container.removeAllViews();
        }
        View view = inflater.inflate(R.layout.fragment_leaves_list2, container, false);
        listItems = new ArrayList<>();
        progressDialog = CommonCalls.createDialouge(getActivity(), "", Values.DIALOGUE_MSG);
        recyclerView = view.findViewById(R.id.leaves_recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        userData = CommonCalls.getUserData(getActivity());
        String base = userData.getUsername() + ":" + userData.getPassword();
        String authHeader = "Basic " + Base64.encodeToString(base.getBytes(), Base64.NO_WRAP);
        getData(authHeader);
        adapter = new LeavesAdapter(listItems, getActivity().getApplicationContext());
        recyclerView.setAdapter(adapter);
        return view;
    }

    public void getData(String authHeader) {
        retrofit = RetrofitInitialize.getApiClient();
        clientAPIs = retrofit.create(ClientAPIs.class);
        Call<LeavesResponseList> call = clientAPIs.getLeaves(userData.getUsername(), userData.getUsertype(), authHeader);
        call.enqueue(new Callback<LeavesResponseList>() {
            @Override
            public void onResponse(Call<LeavesResponseList> call, Response<LeavesResponseList> response) {
                if (response.isSuccessful()) {
                    LeavesResponseList leavesList = response.body();
                    if (leavesList != null && leavesList.getLeavesData() != null) {
                        progressDialog.dismiss();
                        listItems.addAll(leavesList.getLeavesData());
                        adapter.notifyDataSetChanged();
                    } else {
                        progressDialog.dismiss();
                        Toast.makeText(getActivity(), "Leaves not available yet.", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<LeavesResponseList> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(getActivity(), "Sorry something went wrong.", Toast.LENGTH_SHORT).show();
            }
        });
    }


}
