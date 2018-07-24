package com.backingapp.ayman.backingapp;

import com.backingapp.ayman.backingapp.Models.Recipe;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;

import io.reactivex.Single;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient implements ApiService {

    private static final String BASE_URL = "https://d17h27t6h515a5.cloudfront.net/";

    private static ApiClient instance;
    private ApiService apiService;

    private ApiClient() {
        Gson gson = new GsonBuilder().create();

        final Retrofit retrofit = new Retrofit.Builder().baseUrl(BASE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        apiService = retrofit.create(ApiService.class);

    }

    public static ApiClient getInstance() {
        if (instance == null) {
            instance = new ApiClient();
        }
        return instance;
    }

    @Override
    public Single<ArrayList<Recipe>> getRecipes() {
        return apiService.getRecipes();
    }
}
