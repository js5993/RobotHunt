package com.example.junhosung.robothunt;

import android.os.Parcel;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class ScoreboardActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scoreboard);

        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragment = fragmentManager.findFragmentById(R.id.scoreboardActivityFragmentContainer);

        if (fragment == null) {
            fragment = new ScoreboardActivityFragment();
            fragmentManager.beginTransaction().add(R.id.scoreboardActivityFragmentContainer,fragment).commit();
        }
    }
}
