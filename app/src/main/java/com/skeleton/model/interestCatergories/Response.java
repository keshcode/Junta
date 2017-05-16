package com.skeleton.model.interestCatergories;

import com.google.gson.annotations.SerializedName;

/**
 * response for Interest categories
 */

public class Response {
    @SerializedName("statusCode")
    private int statusCode;
    @SerializedName("message")
    private String message;
    @SerializedName("data")
    private Data data;

    /**
     * @return status code
     */
    public int getStatusCode() {
        return statusCode;
    }

    /**
     * @return Messsage with status code
     */
    public String getMessage() {
        return message;
    }

    /**
     * @return main data from server
     */
    public Data getData() {
        return data;
    }
}
