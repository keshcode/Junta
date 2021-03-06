package com.skeleton.database;

import com.skeleton.constant.AppConstant;
import com.skeleton.constant.PaperDbConstant;
import com.skeleton.model.UserDetails;

import io.paperdb.Paper;

/**
 * Developer: Saurabh Verma
 * Dated: 19-02-2017.
 */
public final class CommonData implements PaperDbConstant {
    /**
     * Empty Constructor
     * not called
     */
    private CommonData() {
    }

    /**
     * Update fcm token.
     *
     * @param token the token
     */
//======================================== FCM token ==============================================
    public static void updateFCMToken(final String token) {
        Paper.book().write(PAPER_DEVICE_TOKEN, token);
    }

    /**
     * Gets fcm token.
     *
     * @return the fcm token
     */
    public static String getFCMToken() {
        return Paper.book().read(PAPER_DEVICE_TOKEN);
    }

    /**
     * Save access token.
     *
     * @param accessToken the access token
     */
//======================================== Access Token ============================================
    public static void saveAccessToken(final String accessToken) {
        Paper.book().write(PAPER_ACCESS_TOKEN, accessToken);
    }

    /**
     * Gets access token.
     *
     * @return the access token
     */
    public static String getAccessToken() {
        return Paper.book().read(PAPER_ACCESS_TOKEN);
    }


    //======================================== Clear Data ===============================================

    /**
     * Delete paper.
     */
    public static void clearData() {
        String deviceToken = getFCMToken();
        Paper.book().destroy();
        updateFCMToken(deviceToken);
    }
    //======================================== Set User Data===============================================

    /**
     * sets User Details
     *
     * @param mUserDetails user data object
     */
    public static void setUserData(final UserDetails mUserDetails) {
        Paper.book().write(AppConstant.KEY_FRAGMENT_USER_DETAILS, mUserDetails);
    }
    //======================================== Get User Data===============================================

    /**
     * @return user details object
     */
    public static UserDetails getUserData() {
        return Paper.book().read(AppConstant.KEY_FRAGMENT_USER_DETAILS);
    }


}

