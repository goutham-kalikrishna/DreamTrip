package com.me.travelapp;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.Window;

import com.me.travelapp.Fragments.BlogFragment;
import com.me.travelapp.Fragments.HomeFragment;
import com.me.travelapp.Fragments.ProfileFragment;
import com.me.travelapp.Fragments.SupportFragment;
import com.me.travelapp.Fragments.TripsFragment;


public class MainActivity extends AppCompatActivity {

    private ActionBar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

       // toolbar = getSupportActionBar();

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        //toolbar.setTitle("Home");
        loadFragment(new HomeFragment());
        navigation.setItemBackgroundResource(R.color.global_color_green_primary);

    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment fragment;
            BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);

            switch (item.getItemId()) {
                case R.id.navigation_home:
          //          toolbar.setTitle("Home");
                    fragment=new HomeFragment();
                    loadFragment(fragment);
                    navigation.setItemBackgroundResource(R.color.global_color_green_primary);
                    return true;
                case R.id.navigation_trips:
            //        toolbar.setTitle("My Trips");
                    fragment=new TripsFragment();
                    loadFragment(fragment);
                    navigation.setItemBackgroundResource(R.color.bgBottomNavigation);
                    return true;
                case R.id.navigation_blog:
              //      toolbar.setTitle("Blog");
                    fragment=new BlogFragment();
                    loadFragment(fragment);
                    navigation.setItemBackgroundResource(R.color.global_color_green_accent);
                    return true;
                case R.id.navigation_profile:
                //    toolbar.setTitle("Profile");
                    fragment=new ProfileFragment();
                    loadFragment(fragment);
                    navigation.setItemBackgroundResource(R.color.colorPrimary);
                    return true;
                case R.id.navigation_support:
                 //   toolbar.setTitle("Support");
                    fragment=new SupportFragment();
                    loadFragment(fragment);
                    navigation.setItemBackgroundResource(R.color.global_color_green_primary_dark);
                    return true;

            }
            return false;
        }
    };

    private void loadFragment(Fragment fragment) {
        // load fragment
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}
