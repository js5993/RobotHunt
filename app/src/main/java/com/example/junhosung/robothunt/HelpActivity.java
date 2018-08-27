package com.example.junhosung.robothunt;

import android.os.Bundle;
import android.app.Activity;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;

public class HelpActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);

        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragment = fragmentManager.findFragmentById(R.id.helpActivityFragmentContainer);

        if (fragment == null) {
            fragment = new HelpActivityFragment();
            fragmentManager.beginTransaction().add(R.id.helpActivityFragmentContainer,fragment).commit();
        }

    }

}
