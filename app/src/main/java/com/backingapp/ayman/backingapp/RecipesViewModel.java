package com.backingapp.ayman.backingapp;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.backingapp.ayman.backingapp.Models.Recipe;

import java.util.List;

public class RecipesViewModel extends ViewModel {

    private static final String TAG = RecipesViewModel.class.getSimpleName();

    private MutableLiveData<List<Recipe>> recipesLiveData = new MutableLiveData<>();

    public MutableLiveData<List<Recipe>> getRecipesLiveData() {
        return recipesLiveData;
    }

    public void setRecipes(List<Recipe> recipes) {
        recipesLiveData.setValue(recipes);
    }
}
