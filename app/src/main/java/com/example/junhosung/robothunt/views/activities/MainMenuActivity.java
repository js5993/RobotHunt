package com.example.junhosung.robothunt.views.activities;

import android.support.v4.app.Fragment;

import com.example.junhosung.robothunt.fragment_template.SingleFragmentActivity;
import com.example.junhosung.robothunt.views.fragments.MainMenuFragment;

public class MainMenuActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment() {
        return new MainMenuFragment();
    }



}
