package com.example.junhosung.robothunt;

import android.os.PersistableBundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

public class GameActivity extends FragmentActivity {

    Fragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragment = fragmentManager.findFragmentById(R.id.gameActivityFragmentContainer);

        if (fragment == null) {
            fragment = new GameActivityFragment();
            fragmentManager.beginTransaction().add(R.id.gameActivityFragmentContainer,fragment).commit();
        }

    }

}
