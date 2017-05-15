package com.skeleton.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.skeleton.R;
import com.skeleton.constant.AppConstant;
import com.skeleton.retrofit.APIError;
import com.skeleton.retrofit.ApiInterface;
import com.skeleton.retrofit.CommonParams;
import com.skeleton.retrofit.CommonResponse;
import com.skeleton.retrofit.ResponseResolver;
import com.skeleton.retrofit.RestClient;
import com.skeleton.util.EditTextUtil;

import java.util.HashMap;

import io.paperdb.Paper;


/**
 * Verifies and validate OTP
 */
public class OTPActivity extends BaseActivity {
    private String mPhoneNo, mCountryCode, mOTPCode;
    private TextView tvPhoneNo;
    private TextView tvButtonResendOtp, tvButtonEditNumber;
    private EditText etOtpDigit1, etOtpDigit2, etOtpDigit3, etOtpDigit4;
    private Button btnVerifiyOTP;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);
        init();
        Paper.init(this);
        tvPhoneNo.setText(mCountryCode + " " + mPhoneNo);
        tvButtonResendOtp.setOnClickListener(this);
        tvButtonEditNumber.setOnClickListener(this);
        btnVerifiyOTP.setOnClickListener(this);
        EditTextUtil.moveFrontAndBackAutomatic(1, etOtpDigit1, etOtpDigit2, etOtpDigit3, etOtpDigit4);

    }

    /**
     * initializes all variables
     */
    private void init() {
        tvPhoneNo = (TextView) findViewById(R.id.tvPhoneNo);
        etOtpDigit1 = (EditText) findViewById(R.id.etOtpDigit1);
        etOtpDigit2 = (EditText) findViewById(R.id.etOtpDigit2);
        etOtpDigit3 = (EditText) findViewById(R.id.etOtpDigit3);
        etOtpDigit4 = (EditText) findViewById(R.id.etOtpDigit4);
        tvButtonEditNumber = (TextView) findViewById(R.id.tvButtonEditNumber);
        tvButtonResendOtp = (TextView) findViewById(R.id.tvButtonResendOTP);
        btnVerifiyOTP = (Button) findViewById(R.id.btnVerifiyOTP);
        mPhoneNo = getIntent().getStringExtra(KEY_FRAGMENT_PHONE);
        mCountryCode = getIntent().getStringExtra(KEY_FRAGMENT_COUNTRY_CODE);
    }

    @Override
    public void onClick(final View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.tvButtonEditNumber:
                Log.d("debug", "tv Button Edit Number");
                break;
            case R.id.tvButtonResendOTP:
                resendOTP();
                break;
            case R.id.btnVerifiyOTP:
                verifiyOTP();
                break;
            default:
                Log.d("debug", "end my world");
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

    public void resendOTP() {
        ApiInterface apiInterface = RestClient.getApiInterface();
        apiInterface.resendOtp("bearer " + Paper.book().read(KEY_ACCESS_TOKEN).toString()).enqueue(
                new ResponseResolver<CommonResponse>(this, true, true) {
                    @Override
                    public void success(final CommonResponse commonResponse) {
                        if ("200".equals(commonResponse.getStatusCode())) {
                            Toast.makeText(OTPActivity.this, "new Verification code has been sent", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void failure(final APIError error) {
                        Log.d("debud","failure to resend otp");
                    }
                }
        );
    }

    /**
     * send request tot server to verifiy otp
     */
    public void verifiyOTP() {
        HashMap<String, String> hashMap = new CommonParams.Builder()
                .add(AppConstant.KEY_FRAGMENT_COUNTRY_CODE, mCountryCode)
                .add(AppConstant.KEY_FRAGMENT_PHONE, mPhoneNo)
                .add(AppConstant.KEY_FRAGMENT_OTPCODE, getOTPCode()).build().getMap();

        ApiInterface apiInterface = RestClient.getApiInterface();
        apiInterface.confirmOtp("bearer " + Paper.book().read(KEY_ACCESS_TOKEN).toString(), hashMap).enqueue(
                new ResponseResolver<CommonResponse>(this, true, true) {
                    @Override
                    public void success(final CommonResponse commonResponse) {
                        if ("200".equals(commonResponse.getStatusCode())) {
                            Log.d("debug", "verified otp");
                        }
                    }

                    @Override
                    public void failure(final APIError error) {
                        Log.e("debug", error.getMessage());
                    }
                });
    }
}
