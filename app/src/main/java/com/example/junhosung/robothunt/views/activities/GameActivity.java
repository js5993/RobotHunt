package com.example.junhosung.robothunt.views.activities;

import android.support.v4.app.Fragment;

import com.example.junhosung.robothunt.fragment_template.SingleFragmentActivity;
import com.example.junhosung.robothunt.views.fragments.GameFragment;

public class GameActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment() {
        return new GameFragment();
    }
}
