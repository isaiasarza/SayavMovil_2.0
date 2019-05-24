package com.sayav.desarrollo.sayav20.vinculacion;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface CentralAPI {
    @Headers({
            "Accept: application/json"})
    @POST("/notification")
    Call<String> vincularCentral(@Body CentralData data);
}
