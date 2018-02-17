package com.example.osamakhalid.schoolsystem.Activites;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.osamakhalid.schoolsystem.Adapters.Chat_Adapter;
import com.example.osamakhalid.schoolsystem.Adapters.Teachers_Adapter;
import com.example.osamakhalid.schoolsystem.Model.Chat_Model;
import com.example.osamakhalid.schoolsystem.Model.Teacher_Model;
import com.example.osamakhalid.schoolsystem.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class ParentTeacherChat extends AppCompatActivity {
    private String name;
    Chat_Adapter adapter;
    List<Chat_Model> chatModelList;
    RecyclerView recyclerView;
    EditText inputText;
    Button sendButton;
    SimpleDateFormat dateFormat;
    Calendar c;
    String formattedDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parent_teacher_chat);
        Intent i = getIntent();
        name = i.getStringExtra("name");
        getSupportActionBar().setTitle(name);
        chatModelList = new ArrayList<>();
        recyclerView = (RecyclerView) findViewById(R.id.chat_recycler_view);
        inputText = (EditText) findViewById(R.id.chat_input_msg);
        sendButton = (Button) findViewById(R.id.chat_send_msg);
        c = Calendar.getInstance();
        dateFormat = new SimpleDateFormat("hh:mm:ss aa");
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new Chat_Adapter(chatModelList, getApplicationContext());
        recyclerView.setAdapter(adapter);
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!TextUtils.isEmpty(inputText.getText().toString())) {
                    formattedDate = dateFormat.format(c.getTime());
                    chatModelList.add(new Chat_Model(inputText.getText().toString(), formattedDate));
                    int newPosition= chatModelList.size()-1;
                    adapter.notifyItemInserted(newPosition);
                    recyclerView.scrollToPosition(newPosition);
                    inputText.setText("");
                }
            }
        });
    }
}
