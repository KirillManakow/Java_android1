package com.example.api;

import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.Query;

public interface RetrofitAPIDelete {
    @DELETE("Zakazs/")
    Call<Model> deleteData(@Query("Id")int id);
}
