package com.skeleton.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.skeleton.R;
import com.skeleton.fragment.Step1ProfileFragment;

/**
 * Sets the User Profile Data
 */
public class SetProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_profile);
        replaceFragment(new Step1ProfileFragment());
    }

    /**
     * Replace frame layout with fragment
     *
     * @param fragment reference to the fragment to which we have to replace
     */
    public void replaceFragment(final Fragment fragment) {
        FragmentManager fM = getSupportFragmentManager();
        FragmentTransaction ft = fM.beginTransaction();
        ft.replace(R.id.fmDisplayProfile, fragment);
        ft.commit();
    }
}
