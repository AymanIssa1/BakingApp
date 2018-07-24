package com.backingapp.ayman.backingapp.Adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.backingapp.ayman.backingapp.Interfaces.RecipeAdapterListener;
import com.backingapp.ayman.backingapp.Models.Recipe;
import com.backingapp.ayman.backingapp.R;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder> {

    private List<Recipe> recipeList;
    private RecipeAdapterListener recipeAdapterListener;

    public RecipeAdapter(List<Recipe> recipeList, RecipeAdapterListener recipeAdapterListener) {
        this.recipeList = recipeList;
        this.recipeAdapterListener = recipeAdapterListener;
    }

    @NonNull
    @Override
    public RecipeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.recipe_item, null);
        return new RecipeAdapter.RecipeViewHolder(layoutView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeViewHolder recipeViewHolder, int position) {
        recipeViewHolder.recipeNameTextView.setText(recipeList.get(position).getName());

        Picasso.with(recipeViewHolder.itemView.getContext())
                .load("http://image.tmdb.org/t/p/w185/" + recipeList.get(position).getImage())
                .placeholder(R.drawable.recipe)
                .into(recipeViewHolder.recipeImageView);

        recipeViewHolder.recipeCardView.setOnClickListener(view -> recipeAdapterListener.onRecipeClickListener(recipeList.get(position)));
    }

    @Override
    public int getItemCount() {
        return recipeList.size();
    }

    public class RecipeViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.recipeCardView) CardView recipeCardView;
        @BindView(R.id.recipeImageView) ImageView recipeImageView;
        @BindView(R.id.recipeNameTextView) TextView recipeNameTextView;

        public RecipeViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

    }

}
