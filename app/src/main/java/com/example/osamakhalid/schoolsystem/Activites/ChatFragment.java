package com.example.osamakhalid.schoolsystem.Activites;

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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.osamakhalid.schoolsystem.Adapters.Chat_Adapter;
import com.example.osamakhalid.schoolsystem.Adapters.MessagesAdapter;
import com.example.osamakhalid.schoolsystem.BaseConnection.RetrofitInitialize;
import com.example.osamakhalid.schoolsystem.ConnectionInterface.ClientAPIs;
import com.example.osamakhalid.schoolsystem.Consts.Values;
import com.example.osamakhalid.schoolsystem.GlobalCalls.CommonCalls;
import com.example.osamakhalid.schoolsystem.Model.ChatDataResponse;
import com.example.osamakhalid.schoolsystem.Model.ChatResponse;
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


public class ChatFragment extends Fragment {
    List<ChatDataResponse> listItems;
    RecyclerView recyclerView;
    public Chat_Adapter adapter;
    private Retrofit retrofit;
    private ClientAPIs clientAPIs;
    ProgressDialog progressDialog;
    LoginResponse userData;
    EditText inputText;
    Button sendButton;
    String messageId;
    ImageView attachmentIcon;
    TextView attachmentName;
    LinearLayout attachmentLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if (container != null) {
            container.removeAllViews();
        }
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            messageId = bundle.getString("id");
        }
        View view = inflater.inflate(R.layout.fragment_chat, container, false);
        setHasOptionsMenu(true);
        listItems = new ArrayList<>();
        progressDialog = CommonCalls.createDialouge(getActivity(), "", Values.DIALOGUE_MSG);
        recyclerView = (RecyclerView) view.findViewById(R.id.chat_recycler_view);
        attachmentIcon = view.findViewById(R.id.chat_attachment_icon);
        attachmentName = view.findViewById(R.id.chat_attachment_name);
        attachmentLayout = view.findViewById(R.id.chat_attachment_layout);
        inputText = (EditText) view.findViewById(R.id.chat_input_msg);
        sendButton = (Button) view.findViewById(R.id.chat_send_msg);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity().getBaseContext()));
        userData = CommonCalls.getUserData(getActivity().getBaseContext());
        String base = userData.getUsername() + ":" + userData.getPassword();
        String authHeader = "Basic " + Base64.encodeToString(base.getBytes(), Base64.NO_WRAP);
        getData(authHeader);
        adapter = new Chat_Adapter(listItems, getActivity().getApplicationContext());
        recyclerView.setAdapter(adapter);
        return view;
    }

    public void getData(String authHeader) {
        retrofit = RetrofitInitialize.getApiClient();
        clientAPIs = retrofit.create(ClientAPIs.class);
        Call<ChatResponse> call = clientAPIs.getChat(messageId, userData.getCreateUserID(), authHeader);
        call.enqueue(new Callback<ChatResponse>() {
            @Override
            public void onResponse(Call<ChatResponse> call, Response<ChatResponse> response) {
                if (response.isSuccessful()) {
                    ChatResponse chatResponse = response.body();
                    if (chatResponse != null && chatResponse.getMessagesData() != null) {
                        progressDialog.dismiss();
                        if (!chatResponse.getAttachmentName().equals("")) {
                            attachmentLayout.setVisibility(View.VISIBLE);
                            attachmentName.setText(chatResponse.getAttachmentName());
                            attachmentIcon.setBackgroundResource(R.drawable.file_icon);
                        } else {
                            attachmentLayout.setVisibility(View.GONE);
                        }
                        listItems.addAll(chatResponse.getMessagesData());
                        adapter.notifyDataSetChanged();
                        recyclerView.scrollToPosition(listItems.size() - 1);
                    } else {
                        progressDialog.dismiss();
                        Toast.makeText(getActivity(), "Chat not available yet.", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<ChatResponse> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(getActivity(), "Sorry something went wrong.", Toast.LENGTH_SHORT).show();
            }
        });
    }


}
