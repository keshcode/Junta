package com.skeleton.model.interestCatergories;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Data object from server
 */
public class Data {
    @SerializedName("categories")
    private List<Categories> categories;

    /**
     * @return categories of interests
     */
    public List<Categories> getCategories() {
        return categories;
    }
}
