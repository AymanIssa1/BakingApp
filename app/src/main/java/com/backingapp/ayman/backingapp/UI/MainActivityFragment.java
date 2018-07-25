package com.backingapp.ayman.backingapp.UI;

import android.app.Activity;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.backingapp.ayman.backingapp.Adapter.RecipeAdapter;
import com.backingapp.ayman.backingapp.Constants;
import com.backingapp.ayman.backingapp.Interfaces.RecipeAdapterListener;
import com.backingapp.ayman.backingapp.Interfaces.RecipesInterface;
import com.backingapp.ayman.backingapp.Models.Recipe;
import com.backingapp.ayman.backingapp.R;
import com.backingapp.ayman.backingapp.RecipesController;
import com.backingapp.ayman.backingapp.RecipesViewModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment implements RecipeAdapterListener, Observer<List<Recipe>> {

    @BindView(R.id.recipeRecyclerView) RecyclerView recipeRecyclerView;
    OnRecipeItemClickListener mCallback;
    GridLayoutManager gridLayoutManager;
    private ArrayList<Recipe> recipesList;
    private RecipeAdapter recipeAdapter;
    private Parcelable mListState;
    private RecipesViewModel recipesViewModel;

    public MainActivityFragment() {

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        // This makes sure that the host activity has implemented the callback interface
        // If not, it throws an exception
        try {
            mCallback = (OnRecipeItemClickListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement OnRecipeItemClickListener");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        ButterKnife.bind(this, rootView);

        if (savedInstanceState != null) {
            recipesList = savedInstanceState.getParcelableArrayList(Constants.RECIPES_LIST_EXTRA);
        } else {
            recipesViewModel = ViewModelProviders.of(this).get(RecipesViewModel.class);
            recipesViewModel.getRecipesLiveData().observe(this, this);
            getRecipes();
        }

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();

        if (mListState != null) {
            gridLayoutManager.onRestoreInstanceState(mListState);
        }
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);


        if (savedInstanceState != null)
            mListState = savedInstanceState.getParcelable(Constants.LIST_STATE_EXTRA);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putParcelableArrayList(Constants.RECIPES_LIST_EXTRA, recipesList);

        if (gridLayoutManager != null) {
            mListState = gridLayoutManager.onSaveInstanceState();
            outState.putParcelable(Constants.LIST_STATE_EXTRA, mListState);
        }
    }

    void getRecipes() {
        RecipesController.getRecipes(new RecipesInterface() {
            @Override
            public void onRecipesReceived(ArrayList<Recipe> recipes) {
                recipesList = recipes;
                recipesViewModel.setRecipes(recipes);
            }

            @Override
            public void onError(String errorMessage) {
                Toast.makeText(getContext(), errorMessage, Toast.LENGTH_SHORT).show();
                Log.e("getRecipes", errorMessage);
            }
        });
    }

    void initRecipeAdapter(List<Recipe> recipeList) {
        recipeAdapter = new RecipeAdapter(recipeList, this);
        if (isTablet(getContext())) {
            if (getActivity().getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
                gridLayoutManager = new GridLayoutManager(getActivity(), 2);
            } else {
                gridLayoutManager = new GridLayoutManager(getActivity(), 4);
            }
        } else {
            if (getActivity().getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
                gridLayoutManager = new GridLayoutManager(getActivity(), 1);
            } else {
                gridLayoutManager = new GridLayoutManager(getActivity(), 2);
            }
        }
        recipeRecyclerView.setLayoutManager(gridLayoutManager);
        recipeRecyclerView.setHasFixedSize(true);
        recipeRecyclerView.setAdapter(recipeAdapter);
    }

    @Override
    public void onRecipeClickListener(Recipe recipe) {
        mCallback.onRecipeSelected(recipe);
    }

    public boolean isTablet(Context context) {
        // Verifies if the Generalized Size of the device is XLARGE to be
        // considered a Tablet
        boolean xlarge = ((context.getResources().getConfiguration().screenLayout &
                Configuration.SCREENLAYOUT_SIZE_MASK) ==
                Configuration.SCREENLAYOUT_SIZE_XLARGE);

        // If XLarge, checks if the Generalized Density is at least MDPI
        // (160dpi)
        if (xlarge) {
            DisplayMetrics metrics = new DisplayMetrics();
            Activity activity = (Activity) context;
            activity.getWindowManager().getDefaultDisplay().getMetrics(metrics);

            // MDPI=160, DEFAULT=160, DENSITY_HIGH=240, DENSITY_MEDIUM=160,
            // DENSITY_TV=213, DENSITY_XHIGH=320
            if (metrics.densityDpi == DisplayMetrics.DENSITY_DEFAULT
                    || metrics.densityDpi == DisplayMetrics.DENSITY_HIGH
                    || metrics.densityDpi == DisplayMetrics.DENSITY_MEDIUM
                    || metrics.densityDpi == DisplayMetrics.DENSITY_TV
                    || metrics.densityDpi == DisplayMetrics.DENSITY_XHIGH) {

                // Yes, this is a tablet!
                return true;
            }
        }

        // No, this is not a tablet!
        return false;
    }

    @Override
    public void onChanged(@Nullable List<Recipe> recipes) {
        initRecipeAdapter(recipes);
    }

    public interface OnRecipeItemClickListener {
        void onRecipeSelected(Recipe recipe);
    }
}
