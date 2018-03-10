package com.example.osamakhalid.schoolsystem.Activites;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.example.osamakhalid.schoolsystem.Fragments.FavoriteMessagesFragment;
import com.example.osamakhalid.schoolsystem.Fragments.InboxFragment;
import com.example.osamakhalid.schoolsystem.Fragments.SentMessagesFragment;
import com.example.osamakhalid.schoolsystem.Fragments.TrashMessagesFragment;
import com.example.osamakhalid.schoolsystem.R;

public class AllMessages extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, InboxFragment.OnInboxFragmentInteractionListener ,SentMessagesFragment.OnSentFragmentInteractionListener
        ,TrashMessagesFragment.OnTrashFragmentInteractionListener,FavoriteMessagesFragment.OnFavoriteFragmentInteractionListener {
    private Fragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_messages);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Inbox");
        fragment = new InboxFragment();
        FragmentManager fm1 = getSupportFragmentManager();
        FragmentTransaction ft1 = fm1.beginTransaction();
        ft1.replace(R.id.content_frame, fragment);
        ft1.commit();
        getSupportActionBar().setTitle("Inbox");
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.all_messages, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        switch (id) {
            case R.id.all_messages_inbox:
                fragment = new InboxFragment();
                getSupportActionBar().setTitle("Inbox");
                break;
            case R.id.all_messages_sent:
                fragment = new SentMessagesFragment();
                getSupportActionBar().setTitle("Sent Messages");
                break;
            case R.id.all_messages_fav:
                fragment = new FavoriteMessagesFragment();
                getSupportActionBar().setTitle("Favorite Messages");
                break;
            case R.id.all_messages_trash:
                fragment = new FavoriteMessagesFragment();
                getSupportActionBar().setTitle("Trash Messages");
                break;
        }
        if (fragment != null) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.content_frame, fragment);
            ft.commit();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onFragmentInteraction(String messageId) {
        fragment = new ChatFragment();
        Bundle args = new Bundle();
        args.putString("id", messageId);
        fragment.setArguments(args);
        if (fragment != null) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.content_frame, fragment).addToBackStack("chat");
            ft.commit();
        }
    }
}
