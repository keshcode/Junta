package com.skeleton.model.interestCatergories;

import com.google.gson.annotations.SerializedName;

/**
 * Categories of Intersts
 */
public class Categories {
    @SerializedName("_id")
    private String _id;
    @SerializedName("categoryType")
    private String categoryType;
    @SerializedName("updatedAt")
    private String updatedAt;
    @SerializedName("createdAt")
    private String createdAt;
    @SerializedName("isDeleted")
    private boolean isDeleted;
    @SerializedName("picURL")
    private PicURL picURL;
    @SerializedName("name")
    private String name;
    private Boolean isChecked;

    /**
     * @return if category is checked or not
     */
    public Boolean getChecked() {
        return isChecked;
    }

    /**
     * @param checked sets if Category is checked or not
     */
    public void setChecked(final Boolean checked) {
        isChecked = checked;
    }

    /**
     * @return id to the category
     */
    public String get_id() {
        return _id;
    }

    /**
     * @return which type of Category
     */
    public String getCategoryType() {
        return categoryType;
    }

    /**
     * @return UpdateAt
     */
    public String getUpdatedAt() {
        return updatedAt;
    }

    /**
     * @return Created at
     */
    public String getCreatedAt() {
        return createdAt;
    }

    /**
     * @return is Deleted
     */
    public boolean getIsDeleted() {
        return isDeleted;
    }

    /**
     * @return url to Pictures
     */
    public PicURL getPicURL() {
        return picURL;
    }

    /**
     * @return name of the Category
     */
    public String getName() {
        return name;
    }
}
