package com.example.osamakhalid.schoolsystem.Activites;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.Spinner;

import com.example.osamakhalid.schoolsystem.Fragments.LeavesListFragment;
import com.example.osamakhalid.schoolsystem.Fragments.SubmitLeaveFragment;
import com.example.osamakhalid.schoolsystem.Model.ParentModels.ParentStudentData;
import com.example.osamakhalid.schoolsystem.Model.TeacherData_Model;
import com.example.osamakhalid.schoolsystem.R;

import java.util.ArrayList;
import java.util.List;

public class Leaves extends AppCompatActivity {
    Fragment fragment;
    public static List<TeacherData_Model> teacherData_models;
    public static ArrayList<String> teachersUsername;
    private ProgressDialog progressDilougue;
    String username;
    private List<ParentStudentData> parentStudentDataList;
    private ArrayList<String> childrenUsernames;
    Spinner leave_spinner;
    private static final int CONTENT_VIEW_ID = 10101010;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
      //  setContentView(R.layout.activity_leaves);
        teacherData_models = new ArrayList<>();
        teachersUsername = new ArrayList<>();
        FrameLayout frame = new FrameLayout(this);
        frame.setId(CONTENT_VIEW_ID);
        setContentView(frame, new FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT));

        if (savedInstanceState == null) {
            Fragment newFragment = new LeavesListFragment();
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.add(CONTENT_VIEW_ID,newFragment);
            ft.commit();
        }
//        leave_spinner = findViewById(R.id.leave_spinner);
//        parentStudentDataList = CommonCalls.getChildrenOfParentList(Leaves.this);
//        childrenUsernames = new ArrayList<>();
//        for (ParentStudentData data : parentStudentDataList) {
//            childrenUsernames.add(data.getName());
//        }
//        if (CommonCalls.getUserType(this).equals(Values.TYPE_PARENT)) {
//
//            leave_spinner.setVisibility(View.VISIBLE);
//            ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter
//                    (Leaves.this, android.R.layout.simple_spinner_item,childrenUsernames);
//            spinnerArrayAdapter.setDropDownViewResource(android.R.layout
//                    .simple_spinner_dropdown_item);
//            leave_spinner.setAdapter(spinnerArrayAdapter);
//            leave_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//                @Override
//                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//                    username = parentStudentDataList.get(i).getUsername();
//                    if ((username!= null)){
//                        getTeacherData();
//                    }
//                }
//
//                @Override
//                public void onNothingSelected(AdapterView<?> adapterView) {
//
//                }
//            });
//
//        }
//        else if(CommonCalls.getUserType(Leaves.this).equals(Values.TYPE_STUDENT)){
//            getTeacherData();
//        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.leaves_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }



    public void getTeachersUsername(List<TeacherData_Model> list) {
        if (teachersUsername != null) {
            teachersUsername.clear();
        }
        for (TeacherData_Model data : list) {
            teachersUsername.add(data.getTeacherUsername());
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.leaves_list) {

            fragment = new LeavesListFragment();
            FragmentTransaction ff = getSupportFragmentManager().beginTransaction();
            ff.replace(CONTENT_VIEW_ID, new LeavesListFragment());
            ff.commit();

        } else if (id == R.id.submit_leave_menu) {

            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.add(CONTENT_VIEW_ID, new SubmitLeaveFragment());
            ft.commit();
      }

        return true;
    }
}