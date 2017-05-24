package com.skeleton.fragment;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.kbeanie.multipicker.api.entity.ChosenImage;
import com.skeleton.R;
import com.skeleton.activity.SignInSignUp;
import com.skeleton.constant.AppConstant;
import com.skeleton.database.CommonData;
import com.skeleton.retrofit.APIError;
import com.skeleton.retrofit.ApiInterface;
import com.skeleton.retrofit.MultipartParams;
import com.skeleton.retrofit.ResponseResolver;
import com.skeleton.retrofit.RestClient;
import com.skeleton.util.Log;
import com.skeleton.util.ValidateEditText;
import com.skeleton.util.customview.MaterialEditText;
import com.skeleton.util.dialog.DatePickerFragment;
import com.skeleton.util.imagepicker.ImageChooser;

import java.io.File;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import io.paperdb.Paper;
import okhttp3.RequestBody;


/**
 * Created by keshav on 9/5/17.
 */

public class SignUpFragment extends BaseFragment {
    private static final String TAG = "debug";
    private ImageView ivProfile;
    private MaterialEditText etName, etPhoneNo, etDOB, etEmailAddr, etPassword, etConfirmPassword;
    private RadioGroup rgGender;
    private RadioButton rbgender;
    private int checkedId;
    private File mProfilePic;
    private CheckBox cbTermsAndCondi;
    private Button btnSignup;
    private String mOrientation = "Straight", mCountryCode = "+91";
    private int mGender;
    private ImageChooser mChoose;
    private Date date = new Date();

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable final Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_signup, container, false);
        init(view);
        Paper.init(getContext());
        ivProfile.setOnClickListener(this);
        btnSignup.setOnClickListener(this);
        etDOB.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(final View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.ivProfile:
                mChoose = new ImageChooser(new ImageChooser.Builder(SignUpFragment.this));
                mChoose.selectImage(new ImageChooser.OnImageSelectListener() {
                    @Override
                    public void loadImage(final List<ChosenImage> list) {
                        mProfilePic = new File(list.get(0).getOriginalPath());
                        Glide.with(SignUpFragment.this)
                                .load(list.get(0).getQueryUri())
                                .into(ivProfile);
                    }

                    @Override
                    public void croppedImage(final File mCroppedImage) {

                    }
                });
                break;
            case R.id.btnSignUp:
                checkedId = rgGender.getCheckedRadioButtonId();
                checkGender();
                if (validate()) {
                    Log.d("debug", "Validated information");
                    postData();
                }
                Log.d("debug", "conre");
                break;
            case R.id.etDOB:
                DatePickerFragment datePickerFragment = DatePickerFragment.newInstance(new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(final DatePicker view, final int year, final int month, final int dayOfMonth) {
                        DecimalFormat df = new DecimalFormat("00");
                        etDOB.setText(new StringBuilder()
                                .append(year).append("-")
                                .append(df.format((long) (month + 1))).append("-")
                                .append(df.format((long) dayOfMonth)));
                    }
                });
                datePickerFragment.show(getChildFragmentManager(), TAG_DATEPICKER);
                break;
            default:
                break;

        }
    }

    /**
     * finds which option is selected in radio group
     */
    private void checkGender() {
        if (checkedId == -1) {
            Log.d("debug", "nothing is selected");
        } else if (checkedId == R.id.rbMale) {
            mGender = 0;
        } else {
            mGender = 1;
        }

    }


    @Override
    public void onActivityResult(final int requestCode, final int resultCode, final Intent data) {
        mChoose.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onRequestPermissionsResult(final int requestCode, @NonNull final String[] permissions, @NonNull final int[] grantResults) {
        mChoose.onRequestPermissionsResult(requestCode, permissions, grantResults);
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    /**
     * intilize other variable
     *
     * @param view refernece to view
     */
    private void init(final View view) {
        ivProfile = (ImageView) view.findViewById(R.id.ivProfile);
        etName = (MaterialEditText) view.findViewById(R.id.etName);
        etPhoneNo = (MaterialEditText) view.findViewById(R.id.etPhoneNo);
        etDOB = (MaterialEditText) view.findViewById(R.id.etDOB);
        etEmailAddr = (MaterialEditText) view.findViewById(R.id.etEmailAddr);
        etPassword = (MaterialEditText) view.findViewById(R.id.etPassword);
        etConfirmPassword = (MaterialEditText) view.findViewById(R.id.etConfirmPassword);
        btnSignup = (Button) view.findViewById(R.id.btnSignUp);
        rgGender = (RadioGroup) view.findViewById(R.id.rgGender);
        cbTermsAndCondi = (CheckBox) view.findViewById(R.id.cbTermsAndCondi);
        enableFoatingEditText(etConfirmPassword, etDOB, etEmailAddr, etName, etPhoneNo, etPassword);
    }

    /**
     * @return boolean true if all the values are not empty and valide
     */
    private boolean validate() {
        ValidateEditText validateEditText = new ValidateEditText();
        if (ValidateEditText.checkEmail(etEmailAddr)
                && (ValidateEditText.checkName(etName, true))
                && (ValidateEditText.checkPhoneNumber(etPhoneNo))
                && (ValidateEditText.checkPassword(etPassword, false))
                && (ValidateEditText.checkPassword(etConfirmPassword, true))
                && (ValidateEditText.comparePassword(etPassword, etConfirmPassword))
                && (validateEditText.genericEmpty(etDOB, getString(R.string.error_DOB_empty)) && (checkDOB(etDOB)))
                && (isAgreedToTerms())) {
            return true;
        }
        return false;
    }

    /**
     * @return if agreed to terms and consitions
     */
    private boolean isAgreedToTerms() {
        if (cbTermsAndCondi.isChecked()) {
            return true;
        } else {
            Toast.makeText(getActivity(), getString(R.string.msg_not_agreed_to_terms), Toast.LENGTH_SHORT).show();
        }
        return false;
    }


    /**
     * Checks if date is in valid format
     *
     * @param editText : editTextDOB containing date
     * @return : true if valid, else returns false
     */
    private boolean checkDOB(final EditText editText) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String s = editText.getText().toString();
        try {
            date = df.parse(s);
            Log.d("debug", "valide");
            return true;

        } catch (ParseException e) {
            editText.setError(getString(R.string.error_invalid_data));
            Log.d("debug", "invalide");
            return false;
        }
    }

    /**
     * Enable floating label for {@link MaterialEditText}
     *
     * @param editTexts :list of editText
     */
    public static void enableFoatingEditText(final MaterialEditText... editTexts) {
        for (MaterialEditText editText : editTexts) {
            editText.setFloatingLabel(MaterialEditText.FLOATING_LABEL_HIGHLIGHT);
        }
    }

    /**
     * sends data to server
     */
    public void postData() {
        HashMap<String, RequestBody> multipartParams = new MultipartParams.Builder()
                .add(AppConstant.KEY_FRAGMENT_FNAME, etName.getText().toString())
                .add(AppConstant.KEY_FRAGMENT_LNAME, etName.getText().toString())
                .add(AppConstant.KEY_FRAGMENT_DOB, etDOB.getText().toString())
                .add(AppConstant.KEY_FRAGMENT_COUNTRY_CODE, mCountryCode)
                .add(AppConstant.KEY_FRAGMENT_PHONE, etPhoneNo.getText().toString())
                .add(AppConstant.KEY_FRAGMENT_EMAIL, etEmailAddr.getText().toString())
                .add(AppConstant.KEY_FRAGMENT_PASSWORD, etPassword.getText().toString())
                .add(AppConstant.KEY_FRAGMENT_LANGUAGE, AppConstant.VALUE_FRAGMENT_LANGUAGE)
                .add(AppConstant.KEY_FRAGMENT_DEVICE_TYPE, AppConstant.VALUE_FRAGMENT_DEVICE_TYPE)
                .add(AppConstant.KEY_FRAGMENT_DEVICE_TOKEN, AppConstant.VALUE_RAGMENT_DEVICE_TOKEN)
                .add(AppConstant.KEY_FRAGMENT_APP_VERSION, AppConstant.VALUE_FRAGMENT_APP_VERSION)
                .add(AppConstant.KEY_FRAGMENT_GENDER, mGender)
                .add(AppConstant.KEY_FRAGMENT_ORIENTATION, mOrientation)
                .add(AppConstant.KEY_FRAGMENT_PROFILE_PIC, mProfilePic).build().getMap();

        ApiInterface apiInterface = RestClient.getApiInterface();
        apiInterface.userRegister(multipartParams).enqueue(new ResponseResolver<com.skeleton.model.Response>(getActivity(), true, true) {
            @Override
            public void success(final com.skeleton.model.Response response) {
                Log.d(TAG, "success: " + response.getStatusCode());
                Log.d(TAG, "success: " + response.getStatusCode());
                Toast.makeText(getContext(), response.getMessage(), Toast.LENGTH_SHORT).show();
                ((SignInSignUp) getActivity()).clearEditText(etName, etDOB, etConfirmPassword,
                        etEmailAddr, etPassword, etPhoneNo);
                CommonData.saveAccessToken(response.getData().getAccessToken());
                CommonData.setUserData(response.getData().getUserDetails());
                ((SignInSignUp) getActivity()).setResult(Activity.RESULT_OK, new Intent());
                ((SignInSignUp) getActivity()).finish();


            }

            @Override
            public void failure(final APIError error) {
                Log.d(TAG, "failure: Status " + error.getStatusCode());
                Log.d(TAG, "failure: Message" + error.getMessage());
            }
        });
    }


}