package com.skeleton.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.skeleton.R;
import com.skeleton.fragment.Step2ProfileFragment;
import com.skeleton.model.interestCatergories.Categories;

import java.util.List;

/**
 * Created by keshav on 16/5/17.
 */

public class RecyclerAdaptor extends RecyclerView.Adapter<RecyclerAdaptor.ViewHolder> {
    private List<Categories> categoriesList;
    private Context context;
    private Fragment fragment;

    /**
     * @param categories list of categories
     * @param context    reference to the activty form where it has been called
     * @param fragment   reference to fragment
     */
    public RecyclerAdaptor(final List<Categories> categories, final Context context, final Fragment fragment) {
        this.categoriesList = categories;
        this.context = context;
        this.fragment = fragment;
    }

    @Override
    public RecyclerAdaptor.ViewHolder onCreateViewHolder(final ViewGroup parent, final int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.itemview_categories, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final Categories categories = categoriesList.get(position);
        if (categories.getChecked() == null) {
            categories.setChecked(false);
        }
        holder.tvInterestName.setText(categories.getName());
        //Picasso.with(context).load(categories.getPicURL().getThumbnail()).into(holder.ivInterest);

        holder.ivInterest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                int limit = ((Step2ProfileFragment) fragment).getCategorylistsize();
                Log.d("debug", String.valueOf(limit));
                notifyDataSetChanged();
                if (holder.ivCheck.getVisibility() == View.INVISIBLE && (limit < 5)) {
                    categories.setChecked(true);
                    Log.d("debug", "added to list");
                    holder.ivCheck.setVisibility(View.VISIBLE);
                    ((Step2ProfileFragment) fragment).addElementInCategoryList(categories.get_id());
                    holder.ivOnSelectedShade.setVisibility(View.VISIBLE);
                } else {
                    categories.setChecked(false);
                    Log.d("debug", "removed from list");
                    holder.ivCheck.setVisibility(View.INVISIBLE);
                    ((Step2ProfileFragment) fragment).removeElementFromCategoryLIst(categories.get_id());
                    holder.ivOnSelectedShade.setVisibility(View.INVISIBLE);
                }
                limit = ((Step2ProfileFragment) fragment).getCategorylistsize();
                ((Step2ProfileFragment) fragment).wrapperSetSelectTag(limit);
            }
        });
        if (!categories.getChecked()) {
            holder.ivCheck.setVisibility(View.INVISIBLE);
            holder.ivOnSelectedShade.setVisibility(View.INVISIBLE);
        } else {
            holder.ivCheck.setVisibility(View.VISIBLE);
            holder.ivOnSelectedShade.setVisibility(View.VISIBLE);
        }
    }


    @Override
    public int getItemCount() {
        return categoriesList.size();
    }

    /**
     * viewHolder gets elements of inflated elements
     */
    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView ivInterest, ivOnSelectedShade, ivCheck;
        private TextView tvInterestName;

        /**
         * @param itemView reference to the view inflated
         */
        public ViewHolder(final View itemView) {
            super(itemView);
            ivInterest = (ImageView) itemView.findViewById(R.id.ivInterest);
            ivOnSelectedShade = (ImageView) itemView.findViewById(R.id.ivOnSelectedShade);
            ivCheck = (ImageView) itemView.findViewById(R.id.ivCheck);
            tvInterestName = (TextView) itemView.findViewById(R.id.tvInterestName);
            ivCheck.setVisibility(View.INVISIBLE);
            ivOnSelectedShade.setVisibility(View.INVISIBLE);
        }

    }
}
