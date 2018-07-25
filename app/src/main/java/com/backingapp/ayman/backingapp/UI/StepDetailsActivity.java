package com.backingapp.ayman.backingapp.UI;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.backingapp.ayman.backingapp.Constants;
import com.backingapp.ayman.backingapp.Models.Step;
import com.backingapp.ayman.backingapp.R;

import java.util.ArrayList;

public class StepDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        toolbar.setNavigationOnClickListener(v -> finish());

        if (savedInstanceState == null) {
            ArrayList<Step> steps = getIntent().getParcelableArrayListExtra(Constants.STEPS_LIST_EXTRA);
            int position = getIntent().getIntExtra(Constants.STEP_POSITION_EXTRA, 0);

            StepDetailsActivityFragment stepDetailsActivityFragment = new StepDetailsActivityFragment();
            Bundle bundle = new Bundle();
            bundle.putParcelableArrayList(Constants.STEPS_LIST_EXTRA, steps);
            bundle.putInt(Constants.STEP_POSITION_EXTRA, position);

            stepDetailsActivityFragment.setArguments(bundle);

//            stepDetailsActivityFragment.setPosition(position);
//            stepDetailsActivityFragment.setStepList(steps);

            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .add(R.id.container, stepDetailsActivityFragment)
                    .commit();
        }
    }



}
