package com.skeleton.fragment;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.skeleton.R;
import com.skeleton.activity.OTPActivity;
import com.skeleton.constant.AppConstant;
import com.skeleton.database.CommonData;
import com.skeleton.model.UserDetails;
import com.skeleton.retrofit.APIError;
import com.skeleton.retrofit.ApiInterface;
import com.skeleton.retrofit.CommonParams;
import com.skeleton.retrofit.CommonResponse;
import com.skeleton.retrofit.ResponseResolver;
import com.skeleton.retrofit.RestClient;
import com.skeleton.util.EditTextUtil;

import java.util.HashMap;

/**
 * Created by keshav on 15/5/17.
 */

public class VerifiyOTPFragment extends BaseFragment {
    private String mPhoneNo, mCountryCode, mOTPCode;
    private TextView tvPhoneNo;
    private TextView tvButtonResendOtp, tvButtonEditNumber, tvTitle;
    private EditText etOtpDigit1, etOtpDigit2, etOtpDigit3, etOtpDigit4;
    private Button btnVerifiyOTP, btnSkip;
    private ImageView ivToolbarbtn;

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable final Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_verifiy_otp, container, false);
        init(view);
        btnSkip.setVisibility(View.INVISIBLE);
        tvPhoneNo.setText("(" + mCountryCode + ") " + mPhoneNo);
        tvButtonResendOtp.setOnClickListener(this);
        tvTitle.setText(getString(R.string.title_otp_verfication));
        tvButtonEditNumber.setOnClickListener(this);
        ivToolbarbtn.setOnClickListener(this);
        btnVerifiyOTP.setOnClickListener(this);
        EditTextUtil.moveFrontAndBackAutomatic(1, etOtpDigit1, etOtpDigit2, etOtpDigit3, etOtpDigit4);
        return view;
    }

    /**
     * initializes all variables
     *
     * @param view refernece to view inflated
     */
    private void init(final View view) {
        tvPhoneNo = (TextView) view.findViewById(R.id.tvPhoneNo);
        etOtpDigit1 = (EditText) view.findViewById(R.id.etOtpDigit1);
        etOtpDigit2 = (EditText) view.findViewById(R.id.etOtpDigit2);
        etOtpDigit3 = (EditText) view.findViewById(R.id.etOtpDigit3);
        etOtpDigit4 = (EditText) view.findViewById(R.id.etOtpDigit4);
        tvButtonEditNumber = (TextView) view.findViewById(R.id.tvButtonEditNumber);
        tvButtonResendOtp = (TextView) view.findViewById(R.id.tvButtonResendOTP);
        tvTitle = (TextView) view.findViewById(R.id.tvTitle);
        btnVerifiyOTP = (Button) view.findViewById(R.id.btnVerifiyOTP);
        btnSkip = (Button) view.findViewById(R.id.btnskip);
        ivToolbarbtn = (ImageView) view.findViewById(R.id.ivToolbarBtn);
        mPhoneNo = CommonData.getUserData().getPhoneNo();
        mCountryCode = CommonData.getUserData().getCountryCode();
    }

    @Override
    public void onClick(final View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.tvButtonEditNumber:
                ((OTPActivity) getActivity()).replaceFragment(new EditNumberFragment());
                break;
            case R.id.tvButtonResendOTP:
                resendOTP();
                break;
            case R.id.btnVerifiyOTP:
                verifiyOTP();
                break;
            case R.id.ivToolbarBtn:
                getActivity().finish();
                break;
            default:
                Log.d("debug", "running ...  #default case!");
                break;
        }
    }

    /**
     * @return OTP
     */
    public String getOTPCode() {
        mOTPCode = etOtpDigit1.getText().toString() + etOtpDigit2.getText().toString()
                + etOtpDigit3.getText().toString() + etOtpDigit4.getText().toString();
        Log.d("debug", mOTPCode);
        return mOTPCode;
    }

    /**
     * resends OTP on request
     */
    public void resendOTP() {
        ApiInterface apiInterface = RestClient.getApiInterface();
        apiInterface.resendOtp("bearer " + CommonData.getAccessToken()).enqueue(
                new ResponseResolver<CommonResponse>(getActivity(), true, true) {
                    @Override
                    public void success(final CommonResponse commonResponse) {
                        if ("200".equals(commonResponse.getStatusCode())) {
                            Toast.makeText(getActivity(), "new Verification code has been sent", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void failure(final APIError error) {
                        Log.d("debug", error.getMessage());
                    }
                }
        );
    }

    /**
     * send request tot server to verifiy otp
     */
    public void verifiyOTP() {
        Log.d("debug", getOTPCode());
        HashMap<String, String> hashMap = new CommonParams.Builder()
                .add(AppConstant.KEY_FRAGMENT_COUNTRY_CODE, mCountryCode)
                .add(AppConstant.KEY_FRAGMENT_PHONE, mPhoneNo)
                .add(AppConstant.KEY_FRAGMENT_OTPCODE, getOTPCode()).build().getMap();

        ApiInterface apiInterface = RestClient.getApiInterface();
        apiInterface.confirmOtp("bearer " + CommonData.getAccessToken(), hashMap).enqueue(
                new ResponseResolver<CommonResponse>(getActivity(), true, true) {
                    @Override
                    public void success(final CommonResponse commonResponse) {
                        if ("200".equals(commonResponse.getStatusCode())) {
                            UserDetails userData = CommonData.getUserData();
                            userData.setPhoneVerified(true);
                            CommonData.setUserData(userData);
                            ((OTPActivity) getActivity()).setResult(Activity.RESULT_OK, new Intent());
                            ((OTPActivity) getActivity()).finish();
                        }
                    }

                    @Override
                    public void failure(final APIError error) {
                        Log.e("debug", error.getMessage());
                    }
                });
    }
}
