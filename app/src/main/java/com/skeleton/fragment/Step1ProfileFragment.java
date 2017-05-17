package com.skeleton.fragment;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.skeleton.R;
import com.skeleton.model.userProfile.Response;
import com.skeleton.retrofit.APIError;
import com.skeleton.retrofit.ApiInterface;
import com.skeleton.retrofit.ResponseResolver;
import com.skeleton.retrofit.RestClient;
import com.skeleton.util.Log;
import com.skeleton.util.customview.MaterialEditText;

import java.util.List;

/**
 * Created by keshav on 15/5/17.
 */

public class Step1ProfileFragment extends BaseFragment {
    private MaterialEditText etRelationshipHistory, etEthnicity, etReligion, etHeight, etBodyType, etSmoking;
    private MaterialEditText etDrinking, etOrientation;
    private Response profileConstants;

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable final Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_step1_profile, container, false);
        init(view);
        getProfileItems();
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
    }

    @Override
    public void onClick(final View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.etRelationshipHistory:
                alertDropBox("Relationship History", profileConstants.getData().getRelationshipHistory(), etRelationshipHistory);
                break;
            case R.id.etEthnicity:
                alertDropBox("Ethnicity", profileConstants.getData().getEthnicity(), etEthnicity);
                break;
            case R.id.etReligion:
                alertDropBox("Religion", profileConstants.getData().getReligion(), etReligion);
                break;
            case R.id.etHeight:
                alertDropBox("Height", profileConstants.getData().getReligion(), etHeight);
                break;
            case R.id.etBodyType:
                alertDropBox("BodyType", profileConstants.getData().getReligion(), etBodyType);
                break;
            case R.id.etSmoking:
                alertDropBox("Smoking", profileConstants.getData().getSmoking(), etSmoking);
                break;
            case R.id.etDrinking:
                alertDropBox("Drinking", profileConstants.getData().getDrinking(), etDrinking);
                break;
            case R.id.etOrientation:
                alertDropBox("Orientation", profileConstants.getData().getOrientation(), etOrientation);
                break;
            default:
                break;

        }
    }

    /**
     * @param mTitle title of drop box
     * @param list   list of drop bar items
     * @param etItem reference to editText
     */
    public void alertDropBox(final String mTitle, final List<String> list, final MaterialEditText etItem) {
        final CharSequence[] cs = list.toArray(new CharSequence[list.size()]);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(mTitle);
        builder.setItems(cs, new DialogInterface.OnClickListener() {
            public void onClick(final DialogInterface dialog, final int item) {
                etItem.setText(cs[item]);
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
                }
            }

            @Override
            public void failure(final APIError error) {
                Log.d("debug", error.getMessage());
            }
        });
        return profileConstants;
    }
}
