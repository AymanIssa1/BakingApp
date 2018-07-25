package com.backingapp.ayman.backingapp.Widget;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.backingapp.ayman.backingapp.Constants;
import com.backingapp.ayman.backingapp.Models.Ingredient;
import com.backingapp.ayman.backingapp.Models.Recipe;
import com.backingapp.ayman.backingapp.R;
import com.google.gson.Gson;

import java.util.List;

public class ListViewWidgetService extends RemoteViewsService {

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
//        Log.e("ListViewWidgetService","Fired");
//        Recipe recipe = intent.getParcelableExtra(Constants.RECIPE_EXTRA);
//        Log.e("ListViewWidgetService",new Gson().toJson(recipe));
//        List<Ingredient> ingredientList = recipe.getIngredients();
        return new ListViewRemoteViewsFactory(this.getApplicationContext());
    }

}

class ListViewRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {

    Context mContext;
    List<Ingredient> ingredientList;

    public ListViewRemoteViewsFactory(Context mContext, List<Ingredient> ingredientList) {
//        Log.e("ListViewRemoteViewsFactory","Fired");
        this.mContext = mContext;
        this.ingredientList = ingredientList;
    }

    public ListViewRemoteViewsFactory(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onDataSetChanged() {
        ingredientList = IngredientsWidgetProvider.ingredientList;
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        if (ingredientList == null)
            return 0;
        return ingredientList.size();
    }

    @Override
    public RemoteViews getViewAt(int position) {
        RemoteViews views = new RemoteViews(mContext.getPackageName(), R.layout.ingredients_list_widget_item);

        Ingredient ingredient = ingredientList.get(position);

        views.setTextViewText(R.id.ingredientTextView, ingredient.getQuantity() + " " + ingredient.getMeasure() + " of " +ingredient.getIngredient());

        return views;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }
}
