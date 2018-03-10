package com.example.osamakhalid.schoolsystem.Interfaces;

import android.view.View;

import com.example.osamakhalid.schoolsystem.Model.TimeTable_Data_Model;

import java.util.List;

/**
 * Created by HAMI on 10/03/2018.
 */

public interface ItemClickListenerDay {

    void onClick(View view, List<TimeTable_Data_Model> TimeTable);

}
