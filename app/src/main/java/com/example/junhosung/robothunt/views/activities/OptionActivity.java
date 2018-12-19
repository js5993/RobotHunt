package com.example.junhosung.robothunt.views.activities;

import android.support.v4.app.Fragment;

import com.example.junhosung.robothunt.fragment_template.SingleFragmentActivity;
import com.example.junhosung.robothunt.views.fragments.OptionFragment;

public class OptionActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment() {
        return new OptionFragment();
    }
}
