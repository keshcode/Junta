package com.skeleton.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;

import com.skeleton.R;
import com.skeleton.database.CommonData;
import com.skeleton.fragment.SignInFragment;
import com.skeleton.fragment.SignUpFragment;
import com.skeleton.model.Response;
import com.skeleton.retrofit.APIError;
import com.skeleton.retrofit.ApiInterface;
import com.skeleton.retrofit.ResponseResolver;
import com.skeleton.retrofit.RestClient;
import com.skeleton.util.Log;

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
        directToActivty(CommonData.getAccessToken());
        PagerAdapter pagerAdapter = new com.skeleton.adapter.PagerAdapter(getSupportFragmentManager(), fragments);
        vpSignInSignUp.setAdapter(pagerAdapter);
        tlSignInSignUp.setupWithViewPager(vpSignInSignUp);
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
     * direct to Activty as per the profile completed
     *
     * @param mAccessToken token access user details
     */
    public void directToActivty(final String mAccessToken) {
        if (mAccessToken != null) {
            ApiInterface apiInterface = RestClient.getApiInterface();
            Log.d("debug", mAccessToken);
            apiInterface.userProfile("bearer " + mAccessToken).enqueue(new ResponseResolver<Response>(this, true, true) {
                @Override
                public void success(final Response response) {
                    if (!response.getData().getUserDetails().getPhoneVerified()) {
                        CommonData.setUserData(response.getData().getUserDetails());
                        Intent intent = new Intent(SignInSignUp.this, OTPActivity.class);
                        startActivityForResult(intent, REQ_CODE_SCREEN_OTP);
                    } else {
                        if (response.getData().getUserDetails().getStep1CompleteOrSkip()
                                && response.getData().getUserDetails().getStep2CompleteOrSkip()) {
                            startActivityForResult(new Intent(SignInSignUp.this, HomeActivty.class)
                                    , REQ_CODE_SCREEN_PROFILE_COMPELTE);
                        } else {
                            startActivityForResult(new Intent(SignInSignUp.this, SetProfileActivity.class)
                                    , REQ_CODE_SCREEN_HOME);
                        }
                    }
                }

                @Override
                public void failure(final APIError error) {
                    android.util.Log.e("debug", error.getMessage());
                }
            });
        }
    }

    @Override
    protected void onActivityResult(final int requestCode, final int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RESULT_OK) {
            directToActivty(CommonData.getAccessToken());
        }
    }
}
