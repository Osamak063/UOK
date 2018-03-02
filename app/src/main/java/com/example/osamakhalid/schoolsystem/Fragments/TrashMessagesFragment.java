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

import com.example.osamakhalid.schoolsystem.Adapters.MessagesAdapter;
import com.example.osamakhalid.schoolsystem.BaseConnection.RetrofitInitialize;
import com.example.osamakhalid.schoolsystem.ConnectionInterface.ClientAPIs;
import com.example.osamakhalid.schoolsystem.Consts.Values;
import com.example.osamakhalid.schoolsystem.GlobalCalls.CommonCalls;
import com.example.osamakhalid.schoolsystem.Model.ItemClickListener;
import com.example.osamakhalid.schoolsystem.Model.LoginResponse;
import com.example.osamakhalid.schoolsystem.Model.MessagesFavResponse;
import com.example.osamakhalid.schoolsystem.Model.MessagesFavResponseList;
import com.example.osamakhalid.schoolsystem.Model.MessagesTrashResponse;
import com.example.osamakhalid.schoolsystem.Model.MessagesTrashResponseList;
import com.example.osamakhalid.schoolsystem.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class TrashMessagesFragment extends Fragment implements ItemClickListener {
    List<MessagesTrashResponse> listItems;
    RecyclerView recyclerView;
    public MessagesAdapter adapter;
    private Retrofit retrofit;
    private ClientAPIs clientAPIs;
    ProgressDialog progressDialog;
    LoginResponse userData;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if (container != null) {
            container.removeAllViews();
        }
        View view = inflater.inflate(R.layout.fragment_trash_messages, container, false);
        setHasOptionsMenu(true);
        listItems = new ArrayList<>();
        progressDialog = CommonCalls.createDialouge(getActivity(), "", Values.DIALOGUE_MSG);
        recyclerView = view.findViewById(R.id.trashmessages_recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity().getBaseContext()));
        userData = CommonCalls.getUserData(getActivity().getBaseContext());
        String base = userData.getUsername() + ":" + userData.getPassword();
        String authHeader = "Basic " + Base64.encodeToString(base.getBytes(), Base64.NO_WRAP);
        getData(authHeader);
        adapter = new MessagesAdapter(getActivity().getApplicationContext(), listItems,"trash" );
        recyclerView.setAdapter(adapter);
        adapter.setItemClickListener(this);
        return view;
    }

    public void getData(String authHeader) {
        retrofit = RetrofitInitialize.getApiClient();
        clientAPIs = retrofit.create(ClientAPIs.class);
        Call<MessagesTrashResponseList> call = clientAPIs.getMessageTrash(userData.getUsername(), authHeader);
        call.enqueue(new Callback<MessagesTrashResponseList>() {
            @Override
            public void onResponse(Call<MessagesTrashResponseList> call, Response<MessagesTrashResponseList> response) {
                if (response.isSuccessful()) {
                    MessagesTrashResponseList messageTrashResponseList = response.body();
                    if (messageTrashResponseList != null && messageTrashResponseList.getMessageData() != null) {
                        progressDialog.dismiss();
                        listItems.addAll(messageTrashResponseList.getMessageData());
                        adapter.notifyDataSetChanged();
                    } else {
                        progressDialog.dismiss();
                        Toast.makeText(getActivity(), "Favorite messages not available yet.", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<MessagesTrashResponseList> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(getActivity(), "Sorry something went wrong.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onClick(View view, String name) {
        //   Intent i = new Intent(this, ParentTeacherChat.class);
        //   i.putExtra("name", name);
        //   startActivity(i);
    }


}
