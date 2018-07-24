package com.backingapp.ayman.backingapp.Widget;

import android.app.IntentService;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;

import com.backingapp.ayman.backingapp.Constants;
import com.backingapp.ayman.backingapp.Models.Recipe;

public class WidgetIntentService extends IntentService {

    public static final String OPEN_RECIPE_ACTION = "open-recipe-action";

    public WidgetIntentService() {
        super("RecipeWidgetService");
    }

    public static void startWidgetService(Context context, Recipe recipe) {
        Intent intent = new Intent(context, WidgetIntentService.class);
        intent.setAction(OPEN_RECIPE_ACTION);
        intent.putExtra(Constants.RECIPE_EXTRA, recipe);
        context.startService(intent);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        if (intent != null) {
            String action = intent.getAction();
            if (action.equals(OPEN_RECIPE_ACTION))
                handleOpenRecipeAction(intent);
        }
    }

    private void handleOpenRecipeAction(Intent intent) {
        Recipe recipe = intent.getParcelableExtra(Constants.RECIPE_EXTRA);

        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(this, IngredientsWidgetProvider.class));
        IngredientsWidgetProvider.updateWidgetRecipe(this, recipe, appWidgetManager, appWidgetIds);
    }

}
