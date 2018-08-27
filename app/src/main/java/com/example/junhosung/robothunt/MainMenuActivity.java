package com.example.junhosung.robothunt;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainMenuActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragment = fragmentManager.findFragmentById(R.id.mainMenuActivityFragmentContainer);

        if (fragment == null) {
            fragment = new MainMenuActivityFragment();
            fragmentManager.beginTransaction().add(R.id.mainMenuActivityFragmentContainer,fragment).commit();
        }

    }
}
