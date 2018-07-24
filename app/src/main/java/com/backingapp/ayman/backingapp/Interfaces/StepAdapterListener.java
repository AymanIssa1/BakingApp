package com.backingapp.ayman.backingapp.Interfaces;

import com.backingapp.ayman.backingapp.Models.Step;

import java.util.ArrayList;

public interface StepAdapterListener {

    void onStepClickListener(ArrayList<Step> steps, int position);

}
