package com.example.osamakhalid.schoolsystem.Fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.osamakhalid.schoolsystem.Activites.Messages;
import com.example.osamakhalid.schoolsystem.Activites.ParentTeacherChat;
import com.example.osamakhalid.schoolsystem.Adapters.MessagesAdapter;
import com.example.osamakhalid.schoolsystem.Adapters.Teachers_Adapter;
import com.example.osamakhalid.schoolsystem.BaseConnection.RetrofitInitialize;
import com.example.osamakhalid.schoolsystem.ConnectionInterface.ClientAPIs;
import com.example.osamakhalid.schoolsystem.Consts.Values;
import com.example.osamakhalid.schoolsystem.GlobalCalls.CommonCalls;
import com.example.osamakhalid.schoolsystem.Model.ItemClickListener;
import com.example.osamakhalid.schoolsystem.Model.LoginResponse;
import com.example.osamakhalid.schoolsystem.Model.MessagesInboxResponse;
import com.example.osamakhalid.schoolsystem.Model.MessagesInboxResponseList;
import com.example.osamakhalid.schoolsystem.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class InboxFragment extends Fragment implements ItemClickListener {
    List<MessagesInboxResponse> listItems;
    RecyclerView recyclerView;
    public MessagesAdapter adapter;
    private Retrofit retrofit;
    private ClientAPIs clientAPIs;
    ProgressDialog progressDialog;
    LoginResponse userData;

    private OnInboxFragmentInteractionListener mListener;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if (container != null) {
            container.removeAllViews();
        }
        View view = inflater.inflate(R.layout.fragment_inbox, container, false);
        setHasOptionsMenu(true);
        listItems = new ArrayList<>();
        progressDialog = CommonCalls.createDialouge(getActivity(), "", Values.DIALOGUE_MSG);
        recyclerView = view.findViewById(R.id.messages_recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity().getBaseContext()));
        userData = CommonCalls.getUserData(getActivity().getBaseContext());
        String base = userData.getUsername() + ":" + userData.getPassword();
        String authHeader = "Basic " + Base64.encodeToString(base.getBytes(), Base64.NO_WRAP);
        getData(authHeader);
        adapter = new MessagesAdapter(listItems, getActivity().getApplicationContext());
        recyclerView.setAdapter(adapter);
        adapter.setItemClickListener(this);
        return view;
    }

    public void getData(String authHeader) {
        retrofit = RetrofitInitialize.getApiClient();
        clientAPIs = retrofit.create(ClientAPIs.class);
        Call<MessagesInboxResponseList> call = clientAPIs.getMessagesInbox(userData.getUsername(), userData.getUsertype(), authHeader);
        call.enqueue(new Callback<MessagesInboxResponseList>() {
            @Override
            public void onResponse(Call<MessagesInboxResponseList> call, Response<MessagesInboxResponseList> response) {
                if (response.isSuccessful()) {
                    MessagesInboxResponseList messageInboxResponseList = response.body();
                    if (messageInboxResponseList != null && messageInboxResponseList.getMessageData() != null) {
                        progressDialog.dismiss();
                        listItems.addAll(messageInboxResponseList.getMessageData());
                        adapter.notifyDataSetChanged();
                    } else {
                        progressDialog.dismiss();
                        Toast.makeText(getActivity(), "Inbox not available yet.", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<MessagesInboxResponseList> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(getActivity(), "Sorry something went wrong.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onClick(View view, String messageId) {
        mListener.onFragmentInteraction(messageId);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater Inflater) {
        getActivity().getMenuInflater().inflate(R.menu.search_item_menu, menu);
        MenuItem item = menu.findItem(R.id.search_item);
        SearchView searchView = (SearchView) item.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                adapter.getFilter().filter(s);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                adapter.getFilter().filter(s);
                return false;
            }
        });
        super.onCreateOptionsMenu(menu, Inflater);
    }

    public interface OnInboxFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(String messageId);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mListener = (OnInboxFragmentInteractionListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement OnHeadlineSelectedListener");
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}
