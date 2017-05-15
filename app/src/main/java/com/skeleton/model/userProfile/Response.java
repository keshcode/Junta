package com.skeleton.model.userProfile;

import com.google.gson.annotations.SerializedName;

/**
 * Created by keshav on 15/5/17.
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
     * @param statusCode status code
     */
    public void setStatusCode(final int statusCode) {
        this.statusCode = statusCode;
    }

    /**
     * @return message from server
     */
    public String getMessage() {
        return message;
    }

    /**
     * @param message message from server
     */
    public void setMessage(final String message) {
        this.message = message;
    }

    /**
     * @return data object from server
     */
    public Data getData() {
        return data;
    }

    /**
     * @param data user profile input detail option
     */
    public void setData(final Data data) {
        this.data = data;
    }
}
