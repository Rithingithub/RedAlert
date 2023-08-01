package com.example.redalert.api;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface LocationApiService {
    @POST("api/location") // The API endpoint on your backend server to receive location data
    Call<Void> sendLocationData(@Body LocationData locationData);
}
