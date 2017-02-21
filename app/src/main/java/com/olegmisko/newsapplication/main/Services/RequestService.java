package com.olegmisko.newsapplication.main.Services;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.olegmisko.newsapplication.main.Config.AppConfig;
import com.olegmisko.newsapplication.main.Models.NewsList;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface RequestService {

    public static final Gson GSON = new GsonBuilder()
            .excludeFieldsWithoutExposeAnnotation()
            .create();

    public static final Retrofit RETROFIT = new Retrofit.Builder()
            .baseUrl(AppConfig.BASE_REQUEST_URL)
            .addConverterFactory(GsonConverterFactory.create(GSON))
            .build();


    @GET("articles?")
    Call<NewsList> GETLatesNewsList(@Query("source") String newsSource, @Query("sortby") String sortBy, @Query("apikey") String apiKey);


}
