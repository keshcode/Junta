package com.skeleton.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
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
//                new AlertDialog.Builder(getActivity())
//                        .setItems(profileConstants.getData().getRelationshipHistory(), new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(final DialogInterface dialog, final int which) {
//                                  etRelationshipHistory.setText(profileConstants.getData().getRelationshipHistory().get(which));
//                            }
//                        });

                break;
            case R.id.etEthnicity:
                break;
            case R.id.etReligion:
                break;
            case R.id.etHeight:
                break;
            case R.id.etBodyType:
                break;
            case R.id.etSmoking:
                break;
            case R.id.etDrinking:
                break;
            case R.id.etOrientation:
                break;
            default:
                break;

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
     * gets profile items from server
     */
    public void getProfileItems() {
        ApiInterface apiInterface = RestClient.getApiInterface();
        apiInterface.getUserProfileConstants().enqueue(new ResponseResolver<Response>(getActivity(), true, true) {
            @Override
            public void success(final Response response) {
                if ("200".equals(response.getStatusCode())) {
                    profileConstants = response;
                }
            }

            @Override
            public void failure(final APIError error) {
                Log.d("debug", error.getMessage());
            }
        });
    }

}
