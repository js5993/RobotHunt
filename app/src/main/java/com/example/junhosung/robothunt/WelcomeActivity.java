package com.example.junhosung.robothunt;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class WelcomeActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragment = fragmentManager.findFragmentById(R.id.welcomeActivityFragmentContainer);

        if (fragment == null) {
            fragment = new WelcomeActivityFragment();
            fragmentManager.beginTransaction().add(R.id.welcomeActivityFragmentContainer,fragment).commit();
        }

    }
}
