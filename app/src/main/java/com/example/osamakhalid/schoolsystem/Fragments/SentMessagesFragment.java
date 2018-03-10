package com.example.osamakhalid.schoolsystem.Fragments;

import android.app.ProgressDialog;
import android.content.Context;
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
import com.example.osamakhalid.schoolsystem.Model.MessagesSentResponse;
import com.example.osamakhalid.schoolsystem.Model.MessagesSentResponseList;
import com.example.osamakhalid.schoolsystem.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;


public class SentMessagesFragment extends Fragment implements ItemClickListener {
    List<MessagesSentResponse> listItems;
    RecyclerView recyclerView;
    public MessagesAdapter adapter;
    private Retrofit retrofit;
    private ClientAPIs clientAPIs;
    ProgressDialog progressDialog;
    LoginResponse userData;
    OnSentFragmentInteractionListener mListener;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if (container != null) {
            container.removeAllViews();
        }
        View view = inflater.inflate(R.layout.fragment_sent_messages, container, false);
        setHasOptionsMenu(true);
        listItems = new ArrayList<>();
        progressDialog = CommonCalls.createDialouge(getActivity(), "", Values.DIALOGUE_MSG);
        recyclerView = view.findViewById(R.id.sentMessages_recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity().getBaseContext()));
        userData = CommonCalls.getUserData(getActivity().getBaseContext());
        String base = userData.getUsername() + ":" + userData.getPassword();
        String authHeader = "Basic " + Base64.encodeToString(base.getBytes(), Base64.NO_WRAP);
        getData(authHeader);
        adapter = new MessagesAdapter(listItems, getActivity().getApplicationContext(), "sent");
        recyclerView.setAdapter(adapter);
        adapter.setItemClickListener(this);
        return view;
    }

    public void getData(String authHeader) {
        retrofit = RetrofitInitialize.getApiClient();
        clientAPIs = retrofit.create(ClientAPIs.class);
        Call<MessagesSentResponseList> call = clientAPIs.getMessagesSent(userData.getUsername(), authHeader);
        call.enqueue(new Callback<MessagesSentResponseList>() {
            @Override
            public void onResponse(Call<MessagesSentResponseList> call, Response<MessagesSentResponseList> response) {
                if (response.isSuccessful()) {
                    MessagesSentResponseList messageSentResponseList = response.body();
                    if (messageSentResponseList != null && messageSentResponseList.getMessageData() != null) {
                        progressDialog.dismiss();
                        listItems.addAll(messageSentResponseList.getMessageData());
                        adapter.notifyDataSetChanged();
                    } else {
                        progressDialog.dismiss();
                        Toast.makeText(getActivity(), "Sent messages not available yet.", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<MessagesSentResponseList> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(getActivity(), "Sorry something went wrong.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public interface OnSentFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(String messageId);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mListener = (SentMessagesFragment.OnSentFragmentInteractionListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement OnHeadlineSelectedListener");
        }
    }


    @Override
    public void onClick(View view, String messageId) {
        mListener.onFragmentInteraction(messageId);
    }


}
