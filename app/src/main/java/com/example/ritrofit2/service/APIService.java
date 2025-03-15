package com.example.ritrofit2.service;

import com.example.ritrofit2.model.Category;
import com.example.ritrofit2.model.Respone;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface APIService {
    @GET("category")
    Call<Respone<List<Category>>> getCategory();
}
