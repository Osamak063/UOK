package com.example.osamakhalid.schoolsystem.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.osamakhalid.schoolsystem.Consts.Values;
import com.example.osamakhalid.schoolsystem.GlobalCalls.CommonCalls;
import com.example.osamakhalid.schoolsystem.Model.LoginResponse;
import com.example.osamakhalid.schoolsystem.Model.ParentLoginResponse;
import com.example.osamakhalid.schoolsystem.R;

public class PersonalInfoFragment extends Fragment {
    ParentLoginResponse loginResponse;
    TextView user_name, user_roll_no, user_gender, user_email, user_address, user_religon, user_phone;
    ImageView user_profile_pic;
    LoginResponse loginResponse2;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if (container != null) {
            container.removeAllViews();
        }
        View view = inflater.inflate(R.layout.fragment_personal_info, container, false);
        user_profile_pic = view.findViewById(R.id.user_profile_pic);
        user_name = view.findViewById(R.id.name_user_profile);
        user_roll_no = view.findViewById(R.id.user_roll_number);
        user_gender = view.findViewById(R.id.user_gender);
        user_email = view.findViewById(R.id.user_email);
        user_phone = view.findViewById(R.id.user_number);
        user_address = view.findViewById(R.id.user_address);
        user_religon = view.findViewById(R.id.user_religon);
        if(CommonCalls.getUserType(getActivity()).equals(Values.TYPE_PARENT)){
            loginResponse = CommonCalls.getParentData(getActivity());
            Glide.with(this)
                    .load("http://demo.simsportal.com/uploads/images/" + loginResponse.getPhoto())
                    .into(user_profile_pic);
            user_name.setText(loginResponse.getName());
            user_roll_no.setText("Not Available");
            user_gender.setText("Not Available");
            user_religon.setText("Not Available");
            user_email.setText(loginResponse.getEmail());
            user_phone.setText(loginResponse.getPhone());
            user_address.setText(loginResponse.getAddress());
        }
        else if(CommonCalls.getUserType(getActivity()).equals(Values.TYPE_STUDENT)){
            loginResponse2 = CommonCalls.getUserData(getActivity());
            Glide.with(this)
                    .load("http://demo.simsportal.com/uploads/images/" + loginResponse2.getPhoto())
                    .into(user_profile_pic);
            user_name.setText(loginResponse2.getName());
            user_roll_no.setText(loginResponse2.getRoll());
            user_religon.setText(loginResponse2.getReligion());
            user_gender.setText(loginResponse2.getSex());
            user_email.setText(loginResponse2.getEmail());
            user_phone.setText(loginResponse2.getPhone());
            user_address.setText(loginResponse2.getAddress());
        }



        return view;
    }

}
