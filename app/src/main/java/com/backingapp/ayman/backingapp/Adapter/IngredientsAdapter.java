package com.backingapp.ayman.backingapp.Adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.backingapp.ayman.backingapp.Models.Ingredient;
import com.backingapp.ayman.backingapp.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class IngredientsAdapter extends RecyclerView.Adapter<IngredientsAdapter.IngredientViewHolder> {

    private List<Ingredient> ingredientList;

    public IngredientsAdapter(List<Ingredient> ingredientList) {
        this.ingredientList = ingredientList;
    }

    @NonNull
    @Override
    public IngredientViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.ingredient_item, null);
        return new IngredientsAdapter.IngredientViewHolder(layoutView);
    }

    @Override
    public void onBindViewHolder(@NonNull IngredientViewHolder viewHolder, int position) {
        Ingredient ingredient = ingredientList.get(position);
        viewHolder.ingredientTextView.setText(ingredient.getQuantity() + " " + ingredient.getMeasure() + " of " + ingredient.getIngredient());
    }

    @Override
    public int getItemCount() {
        return ingredientList.size();
    }

    class IngredientViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.ingredientTextView) TextView ingredientTextView;

        public IngredientViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
