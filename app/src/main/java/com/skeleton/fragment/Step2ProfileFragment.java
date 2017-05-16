package com.skeleton.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.skeleton.R;
import com.skeleton.adapter.RecyclerAdaptor;
import com.skeleton.database.CommonData;
import com.skeleton.model.interestCatergories.Categories;
import com.skeleton.model.interestCatergories.Response;
import com.skeleton.retrofit.APIError;
import com.skeleton.retrofit.ApiInterface;
import com.skeleton.retrofit.ResponseResolver;
import com.skeleton.retrofit.RestClient;

import java.util.List;

/**
 * Created by keshav on 16/5/17.
 */

public class Step2ProfileFragment extends BaseFragment {
    private RecyclerView rvInterest;
    private List<Categories> categoriesList;
    private TextView tvSelector1, tvSelector2, tvSelector3, tvSelector4, tvSelector5;

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable final Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_step_2_profile, container, false);
        init(view);
        getcatergorylist();

        return view;
    }

    /**
     * initialize all variables
     *
     * @param view reference to view
     */
    private void init(final View view) {
        rvInterest = (RecyclerView) view.findViewById(R.id.rvInterests);
        tvSelector1 = (TextView) view.findViewById(R.id.tvSelector1);
        tvSelector2 = (TextView) view.findViewById(R.id.tvSelector2);
        tvSelector3 = (TextView) view.findViewById(R.id.tvSelector3);
        tvSelector4 = (TextView) view.findViewById(R.id.tvSelector4);
        tvSelector5 = (TextView) view.findViewById(R.id.tvSelector5);
    }

    /**
     * @param limit size
     */
    public void wrapperSetSelectTag(final int limit) {
        setSelectedTag(limit, tvSelector1, tvSelector2, tvSelector3, tvSelector4, tvSelector5);
    }

    /**
     * @param limit      size
     * @param tvSelector all the selectors
     */
    public void setSelectedTag(final int limit, final TextView... tvSelector) {
        for (int i = 0; i < tvSelector.length; i++) {
            if (i < limit) {
                tvSelector[i].setBackgroundResource(R.drawable.curved_soild_shape_selected);
            } else {
                tvSelector[i].setBackgroundResource(R.drawable.curved_solid_shape);
            }
        }
    }

    /**
     * gets Catergory list form server
     *
     * @return category list
     */
    public List<Categories> getcatergorylist() {
        ApiInterface apiInterface = RestClient.getApiInterface();
        apiInterface.getCategoryList("bearer " + CommonData.getAccessToken(), "INTEREST")
                .enqueue(new ResponseResolver<Response>(getActivity(), true, true) {
                    @Override
                    public void success(final Response response) {
                        Log.d("debug", String.valueOf(response.getStatusCode()));
                        if ("200".equals(String.valueOf(response.getStatusCode()))) {
                            categoriesList = response.getData().getCategories();
                            RecyclerAdaptor recyclerAdaptor = new RecyclerAdaptor(categoriesList, getContext(), Step2ProfileFragment.this);
                            rvInterest.setLayoutManager(new GridLayoutManager(getActivity(), 3));
                            rvInterest.setAdapter(recyclerAdaptor);
                        }
                    }

                    @Override
                    public void failure(final APIError error) {
                        Log.d("debug", error.getMessage());
                    }
                });
        return categoriesList;
    }


}
