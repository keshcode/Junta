package com.skeleton.retrofit;


import com.skeleton.model.Response;

import java.util.HashMap;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.PartMap;
import retrofit2.http.Query;

import static com.skeleton.constant.ApiKeyConstant.AUTHORIZATION;

/**
 * Developer: Saurabh Verma
 * Dated: 27-09-2016.
 */
public interface ApiInterface {
    String UPDATE_LOCATION = "api/v1/user/updateLocation";
    String USER_SIGNUP = "api/user/register";
    String USER_LOGIN = "api/user/login";
    String USER_PROFILE = "api/user/getProfile";
    String USER_OTP = "api/user/resendOTP";
    String USER_VERFIY_OTP = "api/user/verifyOTP";
    String USER_UPDATE_PROFILE = "api/user/updateProfile";
    String USER_PROFILE_CONSTANTS = "api/profile/constants";
    String CATEGORY_LIST = "api/category/list";
    String UPDATE_USER_CATEGORY_INTERESTS = "api/user/selectCategory";
    String USER_SKIP_STEP = "api/user/skipStep";

    /**
     * Api Call for user signup
     *
     * @param map : {@link HashMap} of user data
     * @return : response of server {@link Call<CommonResponse>}
     */
    @Multipart
    @POST(USER_SIGNUP)
    Call<com.skeleton.model.Response> userRegister(@PartMap HashMap<String, RequestBody> map);

    /**
     * Api call for user Login
     *
     * @param authorization : header for api call
     * @param map           : login data of user
     * @return : response of server {@link CommonResponse}
     */
    @POST(USER_LOGIN)
    Call<Response> userLogin(@Header(AUTHORIZATION) String authorization,
                             @Body HashMap<String, String> map);

    /**
     * gets user profile
     *
     * @param mAccessToken to the user account
     * @return complete profile status of user
     */
    @GET(USER_PROFILE)
    Call<Response> userProfile(@Header("authorization") String mAccessToken);

    /**
     * @param mAccessToken Access to the User Account
     * @return OTP
     */
    @PUT(USER_OTP)
    Call<CommonResponse> resendOtp(@Header("authorization") String mAccessToken);

    /**
     * @param mAccessToken Access to the User Account
     * @param map          HashMap tp user details
     * @return null
     */
    @PUT(USER_VERFIY_OTP)
    Call<CommonResponse> confirmOtp(@Header("authorization") String mAccessToken, @Body HashMap<String, String> map);

    /**
     * @param mAccessToken Access to the User Account
     * @param map          items that are to be updated
     * @return Response Model Class
     */
    @Multipart
    @PUT(USER_UPDATE_PROFILE)
    Call<Response> updateProfile(@Header("authorization") String mAccessToken, @PartMap HashMap<String, RequestBody> map);

    /**
     * gets user profile constants
     *
     * @return response model of user profile constant
     */
    @GET(USER_PROFILE_CONSTANTS)
    Call<com.skeleton.model.userProfile.Response> getUserProfileConstants();

    /**
     * @param mAccessToken Access to the user account
     * @param mInterest    prefernces
     * @return catergory list for step 2 profile
     */
    @GET(CATEGORY_LIST)
    Call<com.skeleton.model.interestCatergories.Response> getCategoryList(@Header("authorization") String mAccessToken,
                                                                          @Query("requestType") String mInterest);

    /**
     * @param mAccessToken access to user account
     * @param map          hash map
     * @return commonRespone
     */
    @Multipart
    @PUT(UPDATE_USER_CATEGORY_INTERESTS)
    Call<CommonResponse> putCategoryInterest(@Header("authorization") String mAccessToken,
                                             @PartMap HashMap<String, RequestBody> map);

    /**
     * @param mAccessToken acces to user account
     * @param mNumber      skip which step
     * @return common response
     */
    @FormUrlEncoded
    @PUT(USER_SKIP_STEP)
    Call<CommonResponse> skipStep(@Header("authorization") String mAccessToken, @Field("stepNumber") String mNumber);

//    /**
//     * @param map
//     * @return
//     */
//    @Multipart
//    @POST("api/v1/user/createUser")
//    Call<LoginResponse> register(@PartMap HashMap<String, RequestBody> map);
//
//
//    /**
//     * @param map
//     * @return
//     */
//    @FormUrlEncoded
//    @PUT("api/v1/user/socialLogin")
//    Call<LoginResponse> socialLogin(@FieldMap HashMap<String, String> map);
//
//    /**
//     * @param authorization
//     * @param map
//     * @return
//     */
//    @FormUrlEncoded
//    @PUT("api/v1/user/loginToken")
//    Call<LoginResponse> accessTokenLogin(@Header(AUTHORIZATION) String authorization,
//                                         @FieldMap HashMap<String, String> map);
//
//
//    /**
//     * @param email
//     * @return
//     */
//    @FormUrlEncoded
//    @POST("api/v1/user/forgotpassword")
//    Call<CommonResponse> forgotPassword(@Field("email") String email);
//
//    /**
//     * @param map
//     * @return
//     */
//    @FormUrlEncoded
//    @PUT("api/v1/user/loginCredential")
//    Call<LoginResponse> login(@FieldMap HashMap<String, String> map);


    /**
     * Update location call.
     *
     * @param authorization the authorization
     * @param map           the map
     * @return the call
     */
    @FormUrlEncoded
    @POST(UPDATE_LOCATION)
    Call<CommonParams> updateLocation(@Header(AUTHORIZATION) String authorization,
                                      @FieldMap HashMap<String, String> map);

}

