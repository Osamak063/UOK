package com.example.osamakhalid.schoolsystem.Activites;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.osamakhalid.schoolsystem.Adapters.alert_adapter;
import com.example.osamakhalid.schoolsystem.Adapters.exam_adapter;
import com.example.osamakhalid.schoolsystem.Model.Alert_Model;
import com.example.osamakhalid.schoolsystem.Model.ExamResult_Model;
import com.example.osamakhalid.schoolsystem.R;

import java.util.ArrayList;
import java.util.List;

public class Fees extends AppCompatActivity {

    public RecyclerView recyclerView_dues,recyclerView_history;
    public RecyclerView.Adapter adapter;
    public List<Alert_Model> listItems;
    public List<ExamResult_Model>ItemsList;
    Alert_Model alert;
    ExamResult_Model history_alert;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fees);

        //due fees
        dueFees();

        //Fees history
        paymentHistory();


    }

    //setting up dues fees view
    public void dueFees(){
        listItems = new ArrayList<>();

        //Dummy Data
            alert = new Alert_Model();
            alert.setNotification("Your Fees PKR 5000 is due on 4 December, 2017 ");
            listItems.add(alert);

        //setting up recyclerview
        recyclerView_dues =  findViewById(R.id.duefees);
        recyclerView_dues.setHasFixedSize(true);
        recyclerView_dues.setLayoutManager(new LinearLayoutManager(this));

        adapter = new alert_adapter(listItems,getApplicationContext());
        recyclerView_dues.setAdapter(adapter);
    }

    //setting up Payment Hisotry view
    public void paymentHistory(){

        ItemsList = new ArrayList<>();

        //for Dummy Data
        for (int i=0; i<10 ; i++){

            history_alert = new ExamResult_Model();
            history_alert.setSubject("Receipt #: 100"+i);
            history_alert.setMarks("Amount : 5000 PKR ");
            history_alert.setDate(" Date : 5/"+(i+1)+"/17");
            ItemsList.add(history_alert);

        }

        //setting up recyclerview
        recyclerView_history =  findViewById(R.id.feeshistory);
        recyclerView_history.setHasFixedSize(true);
        recyclerView_history.setLayoutManager(new LinearLayoutManager(this));

        adapter = new exam_adapter(ItemsList,getApplicationContext());
        recyclerView_history.setAdapter(adapter);

    }
}
