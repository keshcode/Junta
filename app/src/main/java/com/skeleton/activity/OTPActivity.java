package com.skeleton.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.widget.FrameLayout;

import com.skeleton.R;
import com.skeleton.fragment.VerifiyOTPFragment;

/**
 * Verifies and validate OTP
 */
public class OTPActivity extends BaseActivity {
    private FrameLayout fmDisplayOTPFollow;


    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);
        VerifiyOTPFragment verifiyOTPFragment = new VerifiyOTPFragment();
        replaceFragment(verifiyOTPFragment);
        init();
    }

    /**
     * initializes all variables
     */
    public void init() {
        fmDisplayOTPFollow = (FrameLayout) findViewById(R.id.fmDisplayOTPFollow);
    }

    /**
     * Replace frame layout with fragment
     *
     * @param fragment reference to the fragment to which we have to replace
     */
    public void replaceFragment(final Fragment fragment) {
        FragmentManager fM = getSupportFragmentManager();
        FragmentTransaction ft = fM.beginTransaction();
        ft.replace(R.id.fmDisplayOTPFollow, fragment);
        ft.commit();
    }
}
