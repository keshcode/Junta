package com.skeleton.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.skeleton.R;
import com.skeleton.constant.AppConstant;
import com.skeleton.model.Response;
import com.skeleton.retrofit.APIError;
import com.skeleton.retrofit.ApiInterface;
import com.skeleton.retrofit.CommonParams;
import com.skeleton.retrofit.ResponseResolver;
import com.skeleton.retrofit.RestClient;
import com.skeleton.util.Log;
import com.skeleton.util.ValidateEditText;
import com.skeleton.util.customview.MaterialEditText;

import java.util.HashMap;

import io.paperdb.Paper;

/**
 * Created by keshav on 9/5/17.
 */

public class SignInFragment extends BaseFragment {
    private MaterialEditText etSignInEmail, etSignInPassword;
    private Button btnLogin;

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable final Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_signin, container, false);
        init(view);
        Paper.init(getContext());
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                if (validate()) {
                    verifyLogin();
                }
            }
        });
        return view;
    }

    /**
     * verifies login email and password from Server
     */
    private void verifyLogin() {
        HashMap<String, String> hashMap = new CommonParams.Builder()
                .add(AppConstant.KEY_FRAGMENT_EMAIL, etSignInEmail.getText().toString())
                .add(AppConstant.KEY_FRAGMENT_PASSWORD, etSignInPassword.getText().toString())
                .add(AppConstant.KEY_FRAGMENT_DEVICE_TYPE, AppConstant.VALUE_FRAGMENT_DEVICE_TYPE)
                .add(AppConstant.KEY_FRAGMENT_LANGUAGE, AppConstant.VALUE_FRAGMENT_LANGUAGE)
                .add(AppConstant.KEY_FRAGMENT_DEVICE_TOKEN, AppConstant.VALUE_RAGMENT_DEVICE_TOKEN)
                .add(AppConstant.KEY_FRAGMENT_FLUSH_PREVIOUS_SESSIOINS, AppConstant.VALUE_FRAGMENT_FLUSH_PREVIOUS_SESSIOINS)
                .add(AppConstant.KEY_FRAGMENT_APP_VERSION, AppConstant.VALUE_FRAGMENT_APP_VERSION).build().getMap();

        ApiInterface apiInterface = RestClient.getApiInterface();
        apiInterface.userLogin(null, hashMap).enqueue(new ResponseResolver<Response>(getContext(), false, false) {
            @Override
            public void success(final Response response) {
                if ("200".equals(response.getStatusCode())) {
                    Paper.book().write(AppConstant.KEY_ACCESS_TOKEN, response.getData().getAccessToken());
                    Log.d("debug", "accEss ALLOWED");
                }
            }

            @Override
            public void failure(final APIError error) {

            }
        });
    }

    /**
     * @return boolean returns the
     */
    private boolean validate() {
        ValidateEditText validateEditText = new ValidateEditText();
        if (validateEditText.checkEmail(etSignInEmail)
                && validateEditText.checkPassword(etSignInPassword, false)) {
            return true;
        }
        return false;
    }

    /**
     * intilizes all variable in the fragment
     *
     * @param view reference to the view of fragment
     */
    private void init(final View view) {
        etSignInEmail = (MaterialEditText) view.findViewById(R.id.etSignInEmail);
        etSignInPassword = (MaterialEditText) view.findViewById(R.id.etSignInPassword);
        btnLogin = (Button) view.findViewById(R.id.btnLogin);
    }


}
