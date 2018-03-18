package com.android.artsytartsy;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.android.artsytartsy.data.data.model.Ingredient;
import com.android.artsytartsy.data.data.model.Recipe;
import com.android.artsytartsy.data.data.remote.RecipeService;
import com.android.artsytartsy.ui.Constants;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by harminder on 18/03/18.
 */


public class GridWidgetService extends RemoteViewsService {
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new GridRemoteViewsFactory(this.getApplicationContext());
    }

    public static class GridRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {

        Context mContext;
        private static List<Recipe> recipeList;

        public GridRemoteViewsFactory(Context applicationContext) {
            mContext = applicationContext;
        }

        @Override
        public void onCreate() {

        }

        @Override
        public void onDataSetChanged() {

        }

        @Override
        public void onDestroy() {

        }

        @Override
        public int getCount() {
            if (recipeList == null) return 0;
            return recipeList.size();
        }

        @Override
        public RemoteViews getViewAt(int position) {

            RemoteViews views = new RemoteViews(mContext.getPackageName(), R.layout.widget_item);
            Recipe recipe = recipeList.get(position);
            views.setTextViewText(R.id.widget_recipe_name, String.valueOf(recipe.getName()));
            if (recipe.getName().equals("Nutella Pie")) {
                views.setImageViewResource(R.id.widget_recipe_iv, R.drawable.nutellapie);
            }
            if (recipe.getName().equals("Brownies")) {
                views.setImageViewResource(R.id.widget_recipe_iv, R.drawable.brownie);
            }
            if (recipe.getName().equals("Yellow Cake")) {
                views.setImageViewResource(R.id.widget_recipe_iv, R.drawable.yellowcake);
            }
            if (recipe.getName().equals("Cheesecake")) {
                views.setImageViewResource(R.id.widget_recipe_iv, R.drawable.cheesecake);
            }

            Bundle extras = new Bundle();
            extras.putSerializable(Constants.IntentConstants.INTENT_KEY_INGREDIENTS, (ArrayList<Ingredient>)recipeList.get(position).getIngredients());
            Intent fillInIntent = new Intent();
            fillInIntent.putExtras(extras);
            views.setOnClickFillInIntent(R.id.widget_recipe_iv, fillInIntent);

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

        public void setData(List<Recipe> recipeData) {
            recipeList = recipeData;
        }

    }

}
