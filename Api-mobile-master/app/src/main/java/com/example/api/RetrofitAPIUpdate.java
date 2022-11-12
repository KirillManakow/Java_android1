package com.example.api;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.PUT;
import retrofit2.http.Query;

public interface RetrofitAPIUpdate {
    @PUT("Zakazs/")
    Call<Model> updateData(@Query("Id")int id, @Body Model model);

}
