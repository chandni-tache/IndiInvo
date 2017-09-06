package com.example.adity.invoicemaker.rest;

import com.example.adity.invoicemaker.model.LoginResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

/**
 * Created by chandnigarg on 30/08/17.
 */

public interface UserApiInterface {

    @FormUrlEncoded
    @POST("v1/login")
    Call<LoginResponse> checkLogin(@Field("email") String email, @Field("password") String password);

    @GET("v1/getUserProfile")
    Call<LoginResponse> getUserProfile(@Header("Authorization") String apiKey);
}
