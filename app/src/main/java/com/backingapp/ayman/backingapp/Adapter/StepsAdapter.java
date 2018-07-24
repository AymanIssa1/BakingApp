package com.backingapp.ayman.backingapp.Adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.backingapp.ayman.backingapp.Interfaces.StepAdapterListener;
import com.backingapp.ayman.backingapp.Models.Step;
import com.backingapp.ayman.backingapp.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StepsAdapter extends RecyclerView.Adapter<StepsAdapter.StepsViewHolder> {

    private ArrayList<Step> stepList;
    private StepAdapterListener stepAdapterListener;

    public StepsAdapter(ArrayList<Step> stepList, StepAdapterListener stepAdapterListener) {
        this.stepList = stepList;
        this.stepAdapterListener = stepAdapterListener;
    }

    @NonNull
    @Override
    public StepsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.step_item, null);
        return new StepsAdapter.StepsViewHolder(layoutView);
    }

    @Override
    public void onBindViewHolder(@NonNull StepsViewHolder viewHolder, int position) {
        Step step = stepList.get(position);
        viewHolder.stepTitleTextView.setText(position + 1 + "- " + step.getShortDescription());

        viewHolder.stepCardView.setOnClickListener(view -> stepAdapterListener.onStepClickListener(stepList, position));
    }

    @Override
    public int getItemCount() {
        return stepList.size();
    }

    class StepsViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.stepCardView) CardView stepCardView;
        @BindView(R.id.stepTitleTextView) TextView stepTitleTextView;

        public StepsViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
