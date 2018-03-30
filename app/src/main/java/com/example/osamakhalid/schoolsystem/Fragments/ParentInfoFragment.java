package com.example.osamakhalid.schoolsystem.Fragments;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.osamakhalid.schoolsystem.Adapters.MessagesAdapter;
import com.example.osamakhalid.schoolsystem.BaseConnection.RetrofitInitialize;
import com.example.osamakhalid.schoolsystem.ConnectionInterface.ClientAPIs;
import com.example.osamakhalid.schoolsystem.Consts.Values;
import com.example.osamakhalid.schoolsystem.GlobalCalls.CommonCalls;
import com.example.osamakhalid.schoolsystem.Model.LoginResponse;
import com.example.osamakhalid.schoolsystem.Model.MessagesFavResponse;
import com.example.osamakhalid.schoolsystem.Model.ParentLoginResponse;
import com.example.osamakhalid.schoolsystem.Model.UserParentInfoResponse;
import com.example.osamakhalid.schoolsystem.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;


public class ParentInfoFragment extends Fragment {
    LoginResponse userData;
    List<MessagesFavResponse> listItems;
    RecyclerView recyclerView;
    public MessagesAdapter adapter;
    private Retrofit retrofit;
    private ClientAPIs clientAPIs;
    ProgressDialog progressDialog;
    TextView user_name, user_roll_no, user_gender, user_email, user_address, user_religon, user_phone;
    private String username;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if (container != null) {
            container.removeAllViews();
        }
        View view = inflater.inflate(R.layout.fragment_parent_info, container, false);
        user_name = view.findViewById(R.id.name_user_profile);
        user_roll_no = view.findViewById(R.id.user_roll_number);
        user_gender = view.findViewById(R.id.user_gender);
        user_email = view.findViewById(R.id.user_email);
        user_phone = view.findViewById(R.id.user_number);
        user_address = view.findViewById(R.id.user_address);
        user_religon = view.findViewById(R.id.user_religon);
        progressDialog = CommonCalls.createDialouge(getActivity(), "", Values.DIALOGUE_MSG);
         getData();
        return view;
    }

    public void getData() {
        retrofit = RetrofitInitialize.getApiClient();
        clientAPIs = retrofit.create(ClientAPIs.class);
        String base = null;
        if (CommonCalls.getUserType(getActivity()).equals(Values.TYPE_PARENT)) {
            ParentLoginResponse loginResponse = CommonCalls.getParentData(getActivity());
            base = loginResponse.getUsername() + ":" + loginResponse.getPassword();
            username = loginResponse.getUsername();
        } else if (CommonCalls.getUserType(getActivity()).equals(Values.TYPE_STUDENT)) {
            LoginResponse loginResponse = CommonCalls.getUserData(getActivity());
            base = loginResponse.getUsername() + ":" + loginResponse.getPassword();
            username = loginResponse.getUsername();
        }
        String authHeader = "Basic " + Base64.encodeToString(base.getBytes(), Base64.NO_WRAP);

        Call<UserParentInfoResponse> call = clientAPIs.getParentInfo(username, authHeader);
        call.enqueue(new Callback<UserParentInfoResponse>() {
            @Override
            public void onResponse(Call<UserParentInfoResponse> call, Response<UserParentInfoResponse> response) {
                if (response.isSuccessful()) {
                    UserParentInfoResponse userParentInfoResponse = response.body();
                    if (userParentInfoResponse != null) {
                        progressDialog.dismiss();
                        user_roll_no.setText(userParentInfoResponse.getMotherName());
                        user_gender.setText(userParentInfoResponse.getFatherProfession());
                        user_name.setText(userParentInfoResponse.getName());
                        user_email.setText(userParentInfoResponse.getEmail());
                        user_phone.setText(userParentInfoResponse.getPhone());
                        user_address.setText(userParentInfoResponse.getAddress());
                    } else {
                        progressDialog.dismiss();
                        Toast.makeText(getActivity(), "Favorite messages not available yet.", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<UserParentInfoResponse> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(getActivity(), "Sorry something went wrong.", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
