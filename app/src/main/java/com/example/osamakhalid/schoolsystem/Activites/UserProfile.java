package com.example.osamakhalid.schoolsystem.Activites;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.example.osamakhalid.schoolsystem.Fragments.PersonalInfoFragment;
import com.example.osamakhalid.schoolsystem.R;

public class UserProfile extends AppCompatActivity {
    Fragment fragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.user_info_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.user_info) {
            fragment = new PersonalInfoFragment();
        }

        if (fragment != null) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.personal_info_fragment, fragment);
            ft.commit();
        }
        return super.onOptionsItemSelected(item);
    }

}
