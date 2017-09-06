package com.example.adity.invoicemaker.rest;

import com.example.adity.invoicemaker.model.VendorCreateResponse;
import com.example.adity.invoicemaker.model.VendorListResponse;

import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

/**
 * Created by chandnigarg on 02/09/17.
 */

public interface VendorApiInterface {

    @GET("v1/getAllVendors")
    Call<VendorListResponse> getAllVendors(@Header("Authorization") String apiKey);

    @FormUrlEncoded
    @POST("v1/addVendor")
    Call<VendorCreateResponse> addVendor(@Header("Authorization") String apiKey, @FieldMap Map<String,String> requestBody) ;
}
