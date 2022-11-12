package com.example.api;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface RetrofitAPI {
    @POST("Zakazs")
    Call<Model> createPost(@Body Model model);
}