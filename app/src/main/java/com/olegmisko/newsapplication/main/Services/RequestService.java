package com.olegmisko.newsapplication.main.Services;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.olegmisko.newsapplication.main.Config.AppConstants;
import com.olegmisko.newsapplication.main.Models.NewsList;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface RequestService {

    Gson GSON = new GsonBuilder()
            .excludeFieldsWithoutExposeAnnotation()
            .create();

    Retrofit RETROFIT = new Retrofit.Builder()
            .baseUrl(AppConstants.BASE_REQUEST_URL)
            .addConverterFactory(GsonConverterFactory.create(GSON))
            .build();


    @GET("articles?")
    Call<NewsList> GETLatesNewsList(@Query("source") String newsSource, @Query("sortby") String sortBy, @Query("apikey") String apiKey);


}
