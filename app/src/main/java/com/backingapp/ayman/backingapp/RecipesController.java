package com.backingapp.ayman.backingapp;

import com.backingapp.ayman.backingapp.Interfaces.RecipesInterface;
import com.backingapp.ayman.backingapp.Models.Recipe;

import java.util.ArrayList;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class RecipesController {

    public static void getRecipes(final RecipesInterface recipesInterface) {
        ApiClient.getInstance()
                .getRecipes()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<ArrayList<Recipe>>() {
                    Disposable disposable;

                    @Override
                    public void onSubscribe(Disposable d) {
                        disposable = d;
                    }

                    @Override
                    public void onSuccess(ArrayList<Recipe> recipes) {
                        recipesInterface.onRecipesReceived(recipes);
                        disposable.dispose();
                    }

                    @Override
                    public void onError(Throwable e) {
                        recipesInterface.onError(e.getMessage());
                        disposable.dispose();
                    }
                });
    }

}
