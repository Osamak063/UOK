package com.example.osamakhalid.schoolsystem.Activites;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
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
import com.example.osamakhalid.schoolsystem.BaseConnection.RetrofitInitialize;
import com.example.osamakhalid.schoolsystem.ConnectionInterface.ClientAPIs;
import com.example.osamakhalid.schoolsystem.Consts.Values;
import com.example.osamakhalid.schoolsystem.GlobalCalls.CommonCalls;
import com.example.osamakhalid.schoolsystem.Model.ChatDataResponse;
import com.example.osamakhalid.schoolsystem.Model.ChatResponse;
import com.example.osamakhalid.schoolsystem.Model.LoginResponse;
import com.example.osamakhalid.schoolsystem.Model.ParentLoginResponse;
import com.example.osamakhalid.schoolsystem.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
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
    TextView attachmentNameTextView;
    LinearLayout attachmentLayout;
    String attachmentUrl;
    String attachmentDir;

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
        attachmentNameTextView = view.findViewById(R.id.chat_attachment_name);
        attachmentLayout = view.findViewById(R.id.chat_attachment_layout);
        inputText = (EditText) view.findViewById(R.id.chat_input_msg);
        sendButton = (Button) view.findViewById(R.id.chat_send_msg);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity().getBaseContext()));
        adapter = new Chat_Adapter(listItems, getActivity().getApplicationContext());
        recyclerView.setAdapter(adapter);
        String base = null;
        if (CommonCalls.getUserType(getActivity().getApplicationContext()).equals(Values.TYPE_PARENT)) {
            ParentLoginResponse loginResponse = CommonCalls.getParentData(getActivity().getApplicationContext());
            base = loginResponse.getUsername() + ":" + loginResponse.getPassword();
            String authHeader = "Basic " + Base64.encodeToString(base.getBytes(), Base64.NO_WRAP);
            getParentData(authHeader,loginResponse.getCreateUserID());
        } else if (CommonCalls.getUserType(getActivity().getApplicationContext()).equals(Values.TYPE_STUDENT)) {
            LoginResponse loginResponse = CommonCalls.getUserData(getActivity().getApplicationContext());
            base = loginResponse.getUsername() + ":" + loginResponse.getPassword();
            String authHeader = "Basic " + Base64.encodeToString(base.getBytes(), Base64.NO_WRAP);
            getData(authHeader,loginResponse.getCreateUserID());
        }
        attachmentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                downloadAttachment(attachmentUrl);
            }
        });
        return view;
    }

    public void getData(String authHeader,String userid) {
        retrofit = RetrofitInitialize.getApiClient();
        clientAPIs = retrofit.create(ClientAPIs.class);
        Call<ChatResponse> call = clientAPIs.getChat(messageId, userid, authHeader);
        call.enqueue(new Callback<ChatResponse>() {
            @Override
            public void onResponse(Call<ChatResponse> call, Response<ChatResponse> response) {
                if (response.isSuccessful()) {
                    ChatResponse chatResponse = response.body();
                    if (chatResponse != null && chatResponse.getMessagesData() != null) {
                        progressDialog.dismiss();
                        setAttachment(chatResponse.getAttachmentName());
                        attachmentUrl = chatResponse.getAttachmentUrl();
                        attachmentDir = chatResponse.getAttachmentDir();
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

    public void getParentData(String authHeader,String userid) {
        retrofit = RetrofitInitialize.getApiClient();
        clientAPIs = retrofit.create(ClientAPIs.class);
        System.out.println("logg message id="+messageId+" user id="+userid);
        Call<ChatResponse> call = clientAPIs.getParentChat(messageId, userid, authHeader);
        call.enqueue(new Callback<ChatResponse>() {
            @Override
            public void onResponse(Call<ChatResponse> call, Response<ChatResponse> response) {
                if (response.isSuccessful()) {
                    ChatResponse chatResponse = response.body();
                    if (chatResponse != null && chatResponse.getMessagesData() != null) {
                        progressDialog.dismiss();
                        setAttachment(chatResponse.getAttachmentName());
                        attachmentUrl = chatResponse.getAttachmentUrl();
                        attachmentDir = chatResponse.getAttachmentDir();
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

    public void setAttachment(String attachmentName) {
        if (!attachmentName.equals("")) {
            attachmentLayout.setVisibility(View.VISIBLE);
            attachmentNameTextView.setText(attachmentName);
            attachmentIcon.setBackgroundResource(R.drawable.file_icon);
        } else {
            attachmentLayout.setVisibility(View.GONE);
        }

    }

    public void downloadAttachment(String url) {
        clientAPIs = retrofit.create(ClientAPIs.class);
        Call<ResponseBody> call = clientAPIs.downloadFileWithDynamicUrlAsync(url);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, final Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    System.out.println("logg response successful");
                    new AsyncTask<Void, Void, Void>() {
                        @Override
                        protected Void doInBackground(Void... voids) {
                            boolean writtenToDisk = writeResponseBodyToDisk(response.body(), attachmentNameTextView.getText().toString());
                            return null;
                        }
                    }.execute();
                } else {
                    Toast.makeText(getActivity(), "Attachment not available.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(getActivity(), "Download failed.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private boolean writeResponseBodyToDisk(ResponseBody body, String attachmentName) {
        try {
            File futureStudioIconFile = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), attachmentName + ".jpg");

            InputStream inputStream = null;
            OutputStream outputStream = null;

            try {
                byte[] fileReader = new byte[4096];

                long fileSize = body.contentLength();
                long fileSizeDownloaded = 0;

                inputStream = body.byteStream();
                outputStream = new FileOutputStream(futureStudioIconFile);

                while (true) {
                    int read = inputStream.read(fileReader);

                    if (read == -1) {
                        break;
                    }

                    outputStream.write(fileReader, 0, read);

                    fileSizeDownloaded += read;

                }

                outputStream.flush();
                return true;
            } catch (IOException e) {
                return false;
            } finally {
                if (inputStream != null) {
                    inputStream.close();
                }

                if (outputStream != null) {
                    outputStream.close();
                }
            }
        } catch (IOException e) {
            return false;
        }
    }


}
