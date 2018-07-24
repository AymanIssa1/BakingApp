package com.backingapp.ayman.backingapp.UI;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.backingapp.ayman.backingapp.Adapter.IngredientsAdapter;
import com.backingapp.ayman.backingapp.Adapter.StepsAdapter;
import com.backingapp.ayman.backingapp.Constants;
import com.backingapp.ayman.backingapp.Interfaces.StepAdapterListener;
import com.backingapp.ayman.backingapp.Models.Recipe;
import com.backingapp.ayman.backingapp.Models.Step;
import com.backingapp.ayman.backingapp.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class StepsActivityFragment extends Fragment implements StepAdapterListener {

    @BindView(R.id.ingredientsRecyclerView) RecyclerView ingredientsRecyclerView;
    @BindView(R.id.stepsRecyclerView) RecyclerView stepsRecyclerView;
    OnStepItemClickListener mCallback;
    private Recipe selectedRecipe;

    public StepsActivityFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        // This makes sure that the host activity has implemented the callback interface
        // If not, it throws an exception
        try {
            mCallback = (OnStepItemClickListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement OnRecipeItemClickListener");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_steps_activity, container, false);
        ButterKnife.bind(this, rootView);

        if (savedInstanceState != null)
            selectedRecipe = savedInstanceState.getParcelable(Constants.RECIPE_EXTRA);

        initIngredientsAdapter();
        initStepsAdapter();


        return rootView;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putParcelable(Constants.RECIPE_EXTRA, selectedRecipe);
    }

    public void setSelectedRecipe(Recipe selectedRecipe) {
        this.selectedRecipe = selectedRecipe;
    }

    private void initIngredientsAdapter() {
        IngredientsAdapter ingredientsAdapter = new IngredientsAdapter(selectedRecipe.getIngredients());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        ingredientsRecyclerView.setLayoutManager(linearLayoutManager);
        ingredientsRecyclerView.setHasFixedSize(true);
        ingredientsRecyclerView.setAdapter(ingredientsAdapter);
    }

    private void initStepsAdapter() {
        StepsAdapter stepsAdapter = new StepsAdapter(selectedRecipe.getSteps(), this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        stepsRecyclerView.setLayoutManager(linearLayoutManager);
        stepsRecyclerView.setHasFixedSize(true);
        stepsRecyclerView.setAdapter(stepsAdapter);
    }

    @Override
    public void onStepClickListener(ArrayList<Step> steps, int position) {
        mCallback.onStepSelected(steps, position);
    }

    public interface OnStepItemClickListener {
        void onStepSelected(ArrayList<Step> steps, int position);
    }
}
