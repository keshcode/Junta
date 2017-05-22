package com.skeleton.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.skeleton.R;
import com.skeleton.database.CommonData;
import com.skeleton.fragment.Step1ProfileFragment;
import com.skeleton.fragment.Step2ProfileFragment;
import com.skeleton.retrofit.APIError;
import com.skeleton.retrofit.ApiInterface;
import com.skeleton.retrofit.CommonResponse;
import com.skeleton.retrofit.ResponseResolver;
import com.skeleton.retrofit.RestClient;
import com.skeleton.util.Log;

/**
 * Sets the User Profile Data
 */
public class SetProfileActivity extends AppCompatActivity {
    private TextView tvTitle;
    private ImageView ivToolbarBtn;
    private Button btnSkip;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_profile);
        init();
        if (!CommonData.getUserData().getStep1CompleteOrSkip()) {
            replaceFragment(new Step1ProfileFragment());
        } else {
            replaceFragment(new Step2ProfileFragment());
        }
    }

    /**
     * initialize all function
     */
    private void init() {
        tvTitle = (TextView) findViewById(R.id.tvTitle);
        ivToolbarBtn = (ImageView) findViewById(R.id.ivToolbarBtn);
        btnSkip = (Button) findViewById(R.id.btnskip);
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

    /**
     * sets title to title
     *
     * @param mTitle title of fragment
     */
    public void setTitle(final String mTitle) {
        tvTitle.setText(mTitle);
    }

    /**
     * @return reeference to toolbar Button
     */
    public ImageView getIvToolbarBtn() {
        return ivToolbarBtn;
    }

    /**
     * @return reeference to skip Button
     */
    public Button getBtnSki() {
        return btnSkip;
    }

    /**
     * skip step
     *
     * @param mStepNumber Which step to skip
     */
    public void skipStep(final int mStepNumber) {

        ApiInterface apiInterface = RestClient.getApiInterface();
        apiInterface.skipStep("bearer " + CommonData.getAccessToken(), String.valueOf(mStepNumber))
                .enqueue(new ResponseResolver<CommonResponse>(this, true, true) {
                    @Override
                    public void success(final CommonResponse commonResponse) {
                        if (mStepNumber == 1) {
                            replaceFragment(new Step1ProfileFragment());
                        } else {
                            replaceFragment(new Step2ProfileFragment());
                        }
                    }

                    @Override
                    public void failure(final APIError error) {
                        Log.d("debug", error.getMessage());
                    }
                });
    }
}
