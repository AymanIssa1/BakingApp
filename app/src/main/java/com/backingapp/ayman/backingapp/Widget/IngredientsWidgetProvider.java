package com.backingapp.ayman.backingapp.Widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.RemoteViews;

import com.backingapp.ayman.backingapp.Constants;
import com.backingapp.ayman.backingapp.Models.Ingredient;
import com.backingapp.ayman.backingapp.Models.Recipe;
import com.backingapp.ayman.backingapp.R;
import com.backingapp.ayman.backingapp.UI.StepsActivity;

import java.util.List;

/**
 * Implementation of App Widget functionality.
 */
public class IngredientsWidgetProvider extends AppWidgetProvider {

    static void updateAppWidget(Context context, Recipe recipe, AppWidgetManager appWidgetManager, int appWidgetId) {

        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.ingredients_widget);

        Intent intent = new Intent(context, StepsActivity.class);

        Bundle bundle = new Bundle();
        bundle.putParcelable(Constants.RECIPE_EXTRA, recipe);

        intent.putExtras(bundle);

        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);

        views.setTextViewText(R.id.recipeTitleTextView, recipe.getName());


        views.setTextViewText(R.id.ingredientsListViewWidget, getRecipeIngredients(recipe.getIngredients()));

        views.setOnClickPendingIntent(R.id.widgetLayout, pendingIntent);

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    public static void updateWidgetRecipe(Context context, Recipe recipe, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, recipe, appWidgetManager, appWidgetId);
        }
    }

    static String getRecipeIngredients(List<Ingredient> ingredients) {
        StringBuilder ingredientsText = new StringBuilder();

        for (Ingredient ingredient : ingredients) {
            ingredientsText
                    .append("-")
                    .append(ingredient.getQuantity())
                    .append(" ")
                    .append(ingredient.getMeasure())
                    .append(" of ")
                    .append(ingredient.getIngredient())
                    .append("\n");
        }
        return ingredientsText.toString();
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {

    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}

