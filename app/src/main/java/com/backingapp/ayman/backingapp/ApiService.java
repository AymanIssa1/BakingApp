package com.backingapp.ayman.backingapp;

import com.backingapp.ayman.backingapp.Models.Recipe;

import java.util.ArrayList;

import io.reactivex.Single;
import retrofit2.http.GET;

public interface ApiService {

    @GET("/topher/2017/May/59121517_baking/baking.json")
    Single<ArrayList<Recipe>> getRecipes();

}
