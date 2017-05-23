package com.skeleton.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.widget.EditText;

import com.skeleton.R;
import com.skeleton.fragment.SignInFragment;
import com.skeleton.fragment.SignUpFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by keshav on 11/5/17.
 */

public class SignInSignUp extends BaseActivity {
    private TabLayout tlSignInSignUp;
    private ViewPager vpSignInSignUp;
    private List<Fragment> fragments;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in_sign_up);
        init();
        PagerAdapter pagerAdapter = new com.skeleton.adapter.PagerAdapter(getSupportFragmentManager(), fragments);
        vpSignInSignUp.setAdapter(pagerAdapter);
        tlSignInSignUp.setupWithViewPager(vpSignInSignUp);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishAffinity();
    }

    /**
     * initialize all variables
     */
    private void init() {
        tlSignInSignUp = (TabLayout) findViewById(R.id.tlSignInSignUp);
        vpSignInSignUp = (ViewPager) findViewById(R.id.vpSignInSignUp);
        fragments = new ArrayList<>();
        fragments.add(new SignUpFragment());
        fragments.add(new SignInFragment());
    }

    /**
     * Clear the string in the editext
     *
     * @param editText : multiple edittexts to be cleared
     */

    public void clearEditText(final EditText... editText) {
        for (EditText editText1 : editText) {
            editText1.setText("");
        }

    }
}
