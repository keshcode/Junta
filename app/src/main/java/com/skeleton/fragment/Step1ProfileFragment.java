package com.skeleton.fragment;

import android.app.Activity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.skeleton.R;
import com.skeleton.activity.SetProfileActivity;
import com.skeleton.database.CommonData;
import com.skeleton.model.UserDetails;
import com.skeleton.model.userProfile.Response;
import com.skeleton.retrofit.APIError;
import com.skeleton.retrofit.ApiInterface;
import com.skeleton.retrofit.MultipartParams;
import com.skeleton.retrofit.ResponseResolver;
import com.skeleton.retrofit.RestClient;
import com.skeleton.util.Log;
import com.skeleton.util.customview.MaterialEditText;

import java.util.HashMap;
import java.util.List;

import okhttp3.RequestBody;

/**
 * Created by keshav on 15/5/17.
 */

public class Step1ProfileFragment extends BaseFragment {
    private MaterialEditText etRelationshipHistory, etEthnicity, etReligion, etHeight, etBodyType, etSmoking;
    private MaterialEditText etDrinking, etOrientation;
    private TextView tvSelector1, tvSelector2, tvSelector3, tvSelector4, tvSelector5, tvSelector6, tvSelector7, tvSelector8;
    private Response profileConstants;
    private Button btnNext, btnSkip;
    private ImageView ivToolbarBtn;

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable final Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_step1_profile, container, false);
        init(view);
        getProfileItems();
        ivToolbarBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                CommonData.clearData();
                ((SetProfileActivity) getActivity()).setResult(Activity.RESULT_OK);
                ((SetProfileActivity) getActivity()).finish();
            }
        });
        btnSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                ((SetProfileActivity) getActivity()).skipStep(1);
            }
        });


        enableFoatingEditText(etRelationshipHistory, etEthnicity, etReligion, etHeight, etBodyType
                , etSmoking, etDrinking, etOrientation);
        return view;
    }

    /**
     * initializes all variables
     *
     * @param view reference to the view
     */
    public void init(final View view) {
        etRelationshipHistory = (MaterialEditText) view.findViewById(R.id.etRelationshipHistory);
        etEthnicity = (MaterialEditText) view.findViewById(R.id.etEthnicity);
        etReligion = (MaterialEditText) view.findViewById(R.id.etReligion);
        etHeight = (MaterialEditText) view.findViewById(R.id.etHeight);
        etBodyType = (MaterialEditText) view.findViewById(R.id.etBodyType);
        etSmoking = (MaterialEditText) view.findViewById(R.id.etSmoking);
        etDrinking = (MaterialEditText) view.findViewById(R.id.etDrinking);
        etOrientation = (MaterialEditText) view.findViewById(R.id.etOrientation);
        tvSelector1 = (TextView) view.findViewById(R.id.tvSelector1);
        tvSelector2 = (TextView) view.findViewById(R.id.tvSelector2);
        tvSelector3 = (TextView) view.findViewById(R.id.tvSelector3);
        tvSelector4 = (TextView) view.findViewById(R.id.tvSelector4);
        tvSelector5 = (TextView) view.findViewById(R.id.tvSelector5);
        tvSelector6 = (TextView) view.findViewById(R.id.tvSelector6);
        tvSelector7 = (TextView) view.findViewById(R.id.tvSelector7);
        tvSelector8 = (TextView) view.findViewById(R.id.tvSelector8);
        btnNext = (Button) view.findViewById(R.id.btnNext);
        ivToolbarBtn = ((SetProfileActivity) getActivity()).getIvToolbarBtn();
        btnSkip = ((SetProfileActivity) getActivity()).getBtnSki();
    }

    @Override
    public void onClick(final View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.etRelationshipHistory:
                alertDropBox("Relationship History", profileConstants.getData().getRelationshipHistory(), etRelationshipHistory, tvSelector1);
                break;
            case R.id.etEthnicity:
                alertDropBox("Ethnicity", profileConstants.getData().getEthnicity(), etEthnicity, tvSelector2);
                break;
            case R.id.etReligion:
                alertDropBox("Religion", profileConstants.getData().getReligion(), etReligion, tvSelector3);
                break;
            case R.id.etHeight:
                alertDropBox("Height", profileConstants.getData().getHeight(), etHeight, tvSelector4);
                break;
            case R.id.etBodyType:
                alertDropBox("BodyType", profileConstants.getData().getBodyType(), etBodyType, tvSelector5);
                break;
            case R.id.etSmoking:
                alertDropBox("Smoking", profileConstants.getData().getSmoking(), etSmoking, tvSelector6);
                break;
            case R.id.etDrinking:
                alertDropBox("Drinking", profileConstants.getData().getDrinking(), etDrinking, tvSelector7);
                break;
            case R.id.etOrientation:
                alertDropBox("Orientation", profileConstants.getData().getOrientation(), etOrientation, tvSelector8);
                break;
            case R.id.btnNext:
                Log.d("debug", "btn next");
                updateInfo();
                break;
            default:
                break;

        }
    }

    /**
     * @param mTitle     title of drop box
     * @param list       list of drop bar items
     * @param etItem     reference to editText
     * @param tvSelector reference to selector
     */
    public void alertDropBox(final String mTitle, final List<String> list, final MaterialEditText etItem
            , final TextView tvSelector) {
        final CharSequence[] cs = list.toArray(new CharSequence[list.size()]);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(mTitle);
        builder.setItems(cs, new DialogInterface.OnClickListener() {
            public void onClick(final DialogInterface dialog, final int item) {
                etItem.setText(cs[item]);
                tvSelector.setBackgroundResource(R.drawable.curved_soild_shape_selected);
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
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
     * gets profile items from server
     *
     * @return profile content
     */
    public Response getProfileItems() {
        ApiInterface apiInterface = RestClient.getApiInterface();
        apiInterface.getUserProfileConstants().enqueue(new ResponseResolver<Response>(getActivity(), true, true) {
            @Override
            public void success(final Response response) {
                if ("200".equals(String.valueOf(response.getStatusCode()))) {
                    profileConstants = response;
                    etRelationshipHistory.setOnClickListener(Step1ProfileFragment.this);
                    etEthnicity.setOnClickListener(Step1ProfileFragment.this);
                    etReligion.setOnClickListener(Step1ProfileFragment.this);
                    etHeight.setOnClickListener(Step1ProfileFragment.this);
                    etBodyType.setOnClickListener(Step1ProfileFragment.this);
                    etSmoking.setOnClickListener(Step1ProfileFragment.this);
                    etDrinking.setOnClickListener(Step1ProfileFragment.this);
                    etOrientation.setOnClickListener(Step1ProfileFragment.this);
                    btnNext.setOnClickListener(Step1ProfileFragment.this);
                }
            }

            @Override
            public void failure(final APIError error) {
                Log.d("debug", error.getMessage());
            }
        });
        return profileConstants;
    }

    @Override
    public void onResume() {
        super.onResume();
        ((SetProfileActivity) getActivity()).setTitle(getString(R.string.title_update_profile));
        UserDetails userDetails = CommonData.getUserData();
        setAllFields(userDetails);
    }

    /**
     * Sets all fields in profile  if not empty
     *
     * @param userDetails userdetails form paper DP
     */
    private void setAllFields(final UserDetails userDetails) {
        etRelationshipHistory.setText((CharSequence) userDetails.getRelationshipHistory());
        etEthnicity.setText((CharSequence) userDetails.getEthnicity());
        etReligion.setText((CharSequence) userDetails.getReligion());
        etBodyType.setText((CharSequence) userDetails.getBodyType());
        etDrinking.setText((CharSequence) userDetails.getDrinking());
        etSmoking.setText((CharSequence) userDetails.getSmoking());
        etHeight.setText(userDetails.getHeight());
        etOrientation.setText(userDetails.getOrientation());
    }

    /**
     * update profile to server
     */
    public void updateInfo() {
        Log.d("debug", "updating info");

        HashMap<String, RequestBody> multipartParams = new MultipartParams.Builder()
                .add(KEY_USER_RELATIONSHIP_HISTORY, etRelationshipHistory.getText())
                .add(KEY_USER_ETHNICITY, etEthnicity.getText())
                .add(KEY_USER_RELIGION, etReligion.getText())
                .add(KEY_USER_HEIGHT, etHeight.getText())
                .add(KEY_USER_BODY_TYPE, etBodyType.getText())
                .add(KEY_USER_SMOKING, etSmoking.getText())
                .add(KEY_USER_DRINKING, etDrinking.getText())
                .add(KEY_STEP1COMPLETEORSKIPED, true)
                .add(KEY_USER_ORIENTATION, etOrientation.getText()).build().getMap();

        ApiInterface apiInterface = RestClient.getApiInterface();
        apiInterface.updateProfile("bearer " + CommonData.getAccessToken(), multipartParams)
                .enqueue(new ResponseResolver<com.skeleton.model.Response>(getActivity(), false, false) {
                    @Override
                    public void success(final com.skeleton.model.Response response) {
                        android.util.Log.d("debug", response.getStatusCode().toString());
                        if ("200".equals(response.getStatusCode().toString())) {
                            CommonData.setUserData(response.getData().getUserDetails());
                            ((SetProfileActivity) getActivity()).replaceFragment(new Step2ProfileFragment());
                        }
                    }

                    @Override
                    public void failure(final APIError error) {
                        android.util.Log.e("debug", error.getMessage());
                    }
                });
    }
}
