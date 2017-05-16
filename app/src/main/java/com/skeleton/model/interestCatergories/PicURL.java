package com.skeleton.model.interestCatergories;

import com.google.gson.annotations.SerializedName;

/**
 * Url of the Picture of Interest catergories
 */
public class PicURL {
    @SerializedName("thumbnail")
    private String thumbnail;
    @SerializedName("original")
    private String original;

    /**
     * @return URL  to thumbnail of image
     */
    public String getThumbnail() {
        return thumbnail;
    }

    /**
     * @return URL to original image
     */
    public String getOriginal() {
        return original;
    }
}
