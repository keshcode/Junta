package com.skeleton.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.skeleton.R;
import com.skeleton.retrofit.APIError;
import com.skeleton.retrofit.ApiInterface;
import com.skeleton.retrofit.CommonResponse;
import com.skeleton.retrofit.ResponseResolver;
import com.skeleton.retrofit.RestClient;
import com.skeleton.util.customview.MaterialEditText;

/**
 * Created by keshav on 14/5/17.
 */

public class EditNumberFragment extends BaseFragment {
    private String mAccessToken, mNewNumber;
    private MaterialEditText etNewNumber;
    private Bundle mBundle;

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable final Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_edit_number, container, false);
        mBundle = getArguments();
        mAccessToken = mBundle.getString(KEY_FRAGMENT_NEW_PHONE_NUMBER);
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
     * @param view
     */
    public void init(View view){
        etNewNumber = (MaterialEditText) view.findViewById(R.id.etNewNumber);
    }

    /**
     * updates Number on Server and provides new OTP
     */
    public void updateNumber() {
        ApiInterface apiInterface = RestClient.getApiInterface();
        apiInterface.editPhoneNumber("bearer " + mAccessToken, mNewNumber).enqueue(new ResponseResolver<CommonResponse>(getActivity(), false, false) {
            @Override
            public void success(final CommonResponse commonResponse) {
                if("200".equals(commonResponse.getStatusCode())){
                    Log.d("debug","new number updated");
                }
            }

            @Override
            public void failure(final APIError error) {
                Log.e("debug",error.getMessage());
            }
        });
    }



}
