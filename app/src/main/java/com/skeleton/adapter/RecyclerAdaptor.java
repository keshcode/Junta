package com.skeleton.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.skeleton.R;
import com.skeleton.fragment.Step2ProfileFragment;
import com.skeleton.model.interestCatergories.Categories;

import java.util.HashMap;
import java.util.List;

/**
 * Created by keshav on 16/5/17.
 */

public class RecyclerAdaptor extends RecyclerView.Adapter<RecyclerAdaptor.ViewHolder> {
    private List<Categories> categoriesList;
    private HashMap<String, String> map;
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
        map = new HashMap<String, String>();
    }

    @Override
    public RecyclerAdaptor.ViewHolder onCreateViewHolder(final ViewGroup parent, final int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.itemview_categories, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final Categories categories = categoriesList.get(position);
        holder.tvInterestName.setText(categories.getName());
        //Picasso.with(context).load(categories.getPicURL().getThumbnail()).into(holder.ivInterest);
        holder.ivInterest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                if (holder.ivCheck.getVisibility() == View.INVISIBLE && (map.size() < 5)) {
                    holder.ivCheck.setVisibility(View.VISIBLE);
                    map.put(categories.getName(), categories.getName());
                    holder.ivOnSelectedShade.setVisibility(View.VISIBLE);
                } else {
                    holder.ivCheck.setVisibility(View.INVISIBLE);
                    map.remove(categories.getName());
                    holder.ivOnSelectedShade.setVisibility(View.INVISIBLE);
                }
                ((Step2ProfileFragment) fragment).wrapperSetSelectTag(map.size());
            }
        });
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
