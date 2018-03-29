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
import com.example.osamakhalid.schoolsystem.Model.MessagesFavResponse;
import com.example.osamakhalid.schoolsystem.Model.MessagesFavResponseList;
import com.example.osamakhalid.schoolsystem.Model.ParentLoginResponse;
import com.example.osamakhalid.schoolsystem.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class FavoriteMessagesFragment extends Fragment implements ItemClickListener {
    List<MessagesFavResponse> listItems;
    RecyclerView recyclerView;
    public MessagesAdapter adapter;
    private Retrofit retrofit;
    private ClientAPIs clientAPIs;
    ProgressDialog progressDialog;
    LoginResponse userData;
    OnFavoriteFragmentInteractionListener mListener;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if (container != null) {
            container.removeAllViews();
        }
        View view = inflater.inflate(R.layout.fragment_favorite_messages, container, false);
        setHasOptionsMenu(true);
        listItems = new ArrayList<>();
        progressDialog = CommonCalls.createDialouge(getActivity(), "", Values.DIALOGUE_MSG);
        recyclerView = view.findViewById(R.id.favmessages_recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity().getBaseContext()));
        adapter = new MessagesAdapter(getActivity().getApplicationContext(), "fav", listItems);
        recyclerView.setAdapter(adapter);
        String base = null;
        if (CommonCalls.getUserType(getActivity().getApplicationContext()).equals(Values.TYPE_PARENT)) {
            ParentLoginResponse loginResponse = CommonCalls.getParentData(getActivity().getApplicationContext());
            base = loginResponse.getUsername() + ":" + loginResponse.getPassword();
            String authHeader = "Basic " + Base64.encodeToString(base.getBytes(), Base64.NO_WRAP);
            getParentData(authHeader,loginResponse.getUsername());
        } else if (CommonCalls.getUserType(getActivity().getApplicationContext()).equals(Values.TYPE_STUDENT)) {
            LoginResponse loginResponse = CommonCalls.getUserData(getActivity().getApplicationContext());
            base = loginResponse.getUsername() + ":" + loginResponse.getPassword();
            String authHeader = "Basic " + Base64.encodeToString(base.getBytes(), Base64.NO_WRAP);
            getData(authHeader,loginResponse.getUsername());
        }
        adapter.setItemClickListener(this);
        return view;
    }

    public void getData(String authHeader,String username) {
        retrofit = RetrofitInitialize.getApiClient();
        clientAPIs = retrofit.create(ClientAPIs.class);
        Call<MessagesFavResponseList> call = clientAPIs.getMessagesFav(username, authHeader);
        call.enqueue(new Callback<MessagesFavResponseList>() {
            @Override
            public void onResponse(Call<MessagesFavResponseList> call, Response<MessagesFavResponseList> response) {
                if (response.isSuccessful()) {
                    MessagesFavResponseList messageFavResponseList = response.body();
                    if (messageFavResponseList != null && messageFavResponseList.getMessageData() != null) {
                        progressDialog.dismiss();
                        listItems.addAll(messageFavResponseList.getMessageData());
                        adapter.notifyDataSetChanged();
                    } else {
                        progressDialog.dismiss();
                        Toast.makeText(getActivity(), "Favorite messages not available yet.", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<MessagesFavResponseList> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(getActivity(), "Sorry something went wrong.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void getParentData(String authHeader,String username) {
        retrofit = RetrofitInitialize.getApiClient();
        clientAPIs = retrofit.create(ClientAPIs.class);
        Call<MessagesFavResponseList> call = clientAPIs.getParentMessagesFav(username, authHeader);
        call.enqueue(new Callback<MessagesFavResponseList>() {
            @Override
            public void onResponse(Call<MessagesFavResponseList> call, Response<MessagesFavResponseList> response) {
                if (response.isSuccessful()) {
                    MessagesFavResponseList messageFavResponseList = response.body();
                    if (messageFavResponseList != null && messageFavResponseList.getMessageData() != null) {
                        progressDialog.dismiss();
                        listItems.addAll(messageFavResponseList.getMessageData());
                        adapter.notifyDataSetChanged();
                    } else {
                        progressDialog.dismiss();
                        Toast.makeText(getActivity(), "Favorite messages not available yet.", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<MessagesFavResponseList> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(getActivity(), "Sorry something went wrong.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public interface OnFavoriteFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(String messageId);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mListener = (FavoriteMessagesFragment.OnFavoriteFragmentInteractionListener) context;
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
