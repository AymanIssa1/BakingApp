package com.backingapp.ayman.backingapp.Interfaces;

import com.backingapp.ayman.backingapp.Models.Recipe;

import java.util.ArrayList;

public interface RecipesInterface {

    void onRecipesReceived(ArrayList<Recipe> recipes);

    void onError(String errorMessage);

}
