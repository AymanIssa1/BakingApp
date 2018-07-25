package com.backingapp.ayman.backingapp.UI;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.backingapp.ayman.backingapp.Constants;
import com.backingapp.ayman.backingapp.Models.Recipe;
import com.backingapp.ayman.backingapp.Models.Step;
import com.backingapp.ayman.backingapp.R;
import com.backingapp.ayman.backingapp.Widget.WidgetIntentService;

import java.util.ArrayList;

public class StepsActivity extends AppCompatActivity implements StepsActivityFragment.OnStepItemClickListener {

    private boolean mTwoPane = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_steps);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        toolbar.setNavigationOnClickListener(v -> finish());

        Recipe recipe = getIntent().getParcelableExtra(Constants.RECIPE_EXTRA);

        StepsActivityFragment stepsActivityFragment = new StepsActivityFragment();
        stepsActivityFragment.setSelectedRecipe(recipe);

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .add(R.id.container, stepsActivityFragment)
                .commit();

        if (getResources().getBoolean(R.bool.isTablet)) {
            mTwoPane = true;

            StepDetailsActivityFragment stepsDetailsActivityFragment = new StepDetailsActivityFragment();
//            stepsDetailsActivityFragment.setSelectedRecipe(recipe);
            Bundle bundle = new Bundle();
            bundle.putParcelableArrayList(Constants.STEPS_LIST_EXTRA, recipe.getSteps());
            bundle.putInt(Constants.STEP_POSITION_EXTRA, 0);

            stepsDetailsActivityFragment.setArguments(bundle);

            fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .add(R.id.detailsContainer, stepsDetailsActivityFragment)
                    .commit();

        } else {
            mTwoPane = false;
        }


        WidgetIntentService.startWidgetService(this, recipe);
    }

    @Override
    public void onStepSelected(ArrayList<Step> steps, int position) {
        Intent intent = new Intent(this, StepDetailsActivity.class);
        intent.putExtra(Constants.STEPS_LIST_EXTRA, steps);
        intent.putExtra(Constants.STEP_POSITION_EXTRA, position);
        startActivity(intent);
    }
}
