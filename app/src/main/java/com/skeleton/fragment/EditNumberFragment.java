package com.skeleton.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.skeleton.R;
import com.skeleton.activity.OTPActivity;
import com.skeleton.constant.AppConstant;
import com.skeleton.database.CommonData;
import com.skeleton.model.Response;
import com.skeleton.retrofit.APIError;
import com.skeleton.retrofit.ApiInterface;
import com.skeleton.retrofit.MultipartParams;
import com.skeleton.retrofit.ResponseResolver;
import com.skeleton.retrofit.RestClient;
import com.skeleton.util.customview.MaterialEditText;

import java.util.HashMap;

import okhttp3.RequestBody;

/**
 * Created by keshav on 14/5/17.
 */

public class EditNumberFragment extends BaseFragment {
    private String mAccessToken, mNewNumber;
    private MaterialEditText etNewNumber;
    private Button btnSubmit;

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable final Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_edit_number, container, false);
        init(view);
        mAccessToken = CommonData.getAccessToken();
        btnSubmit.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(final View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.btnSubmit:
                mNewNumber = etNewNumber.getText().toString();
                updateNumber();
                break;
            default:
                break;
        }
    }

    /**
     * intilize all variables
     *
     * @param view reference to view to which we have to replace
     */
    public void init(final View view) {
        etNewNumber = (MaterialEditText) view.findViewById(R.id.etNewNumber);
        btnSubmit = (Button) view.findViewById(R.id.btnSubmit);
    }


    /**
     * updates Number on Server and provides new OTP
     */
    public void updateNumber() {
        HashMap<String, RequestBody> multipartParams = new MultipartParams.Builder()
                .add(AppConstant.KEY_FRAGMENT_NEW_PHONE_NUMBER, mNewNumber).build().getMap();
        ApiInterface apiInterface = RestClient.getApiInterface();
        apiInterface.editPhoneNumber("bearer " + mAccessToken, multipartParams).enqueue(new ResponseResolver<Response>(getActivity(), false, false) {
            @Override
            public void success(final Response response) {
                Log.d("debug", response.getStatusCode().toString());
                if ("200".equals(response.getStatusCode().toString())) {
                    CommonData.setUserData(response.getData().getUserDetails());
                    ((OTPActivity) getActivity()).replaceFragment(new VerifiyOTPFragment());
                }
            }

            @Override
            public void failure(final APIError error) {
                Log.e("debug", error.getMessage());
            }
        });
    }


}
