package com.shidduckbook.RetrofitClasses;

import com.google.gson.JsonObject;
import com.shidduckbook.Util.AppConstant;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by MAX on 25-Jan-17.
 */

public interface MyApiEndpointInterface {

    @POST(AppConstant.ENDPOINT.REGISTRATION)
    Call<JsonObject> register_logs_api(@Body JsonObject jsonObject);

    @POST(AppConstant.ENDPOINT.GENERALAPI)
    Call<JsonObject> general_api(@Body JsonObject jsonObject);

    @POST(AppConstant.ENDPOINT.MY_MATCHED_API)
    Call<JsonObject> my_matched_API(@Body JsonObject jsonObject);

    @POST(AppConstant.ENDPOINT.USER_NOTES_API)
    Call<JsonObject> user_notes_Api(@Body JsonObject jsonObject);

}
