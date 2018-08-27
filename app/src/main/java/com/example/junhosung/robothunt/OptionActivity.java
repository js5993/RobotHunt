package com.example.junhosung.robothunt;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class OptionActivity extends FragmentActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_option);

        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragment = fragmentManager.findFragmentById(R.id.optionActivityFragmentContainer);

        if (fragment == null) {
            fragment = new OptionActivityFragment();
            fragmentManager.beginTransaction().add(R.id.optionActivityFragmentContainer,fragment).commit();

        }

    }
}
