package com.android.artsytartsy;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Parcelable;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.TextView;

import com.android.artsytartsy.data.data.model.Ingredient;
import com.android.artsytartsy.data.data.model.Recipe;
import com.android.artsytartsy.ui.Constants;
import com.android.artsytartsy.ui.IngredientAndStepDetailsActivity;
import com.android.artsytartsy.ui.MainActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.android.artsytartsy.ui.MainActivity.sp;

/**
 * Implementation of App Widget functionality.
 */
public class RecipeWidgetProvider extends AppWidgetProvider {

    private static List<Recipe> mRecipe;

    private static int id = -1;

    private static String ingredientsString = "";

    private static String heading = "";

    private static Map<String, String> measureMap = new HashMap<String, String>()
    {
        {
            put("CUP", "cup");
            put("TBLSP", "tablespoon");
            put("TSP", "teaspoon");
            put("K", "kilo");
            put("G", "gram");
            put("OZ", "ounce");
            put("UNIT", "unit");
            put("OZ", "ounce");
        }
    };

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {

//        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget_grid_view);
//        Intent intent = new Intent(context, GridWidgetService.class);
//        views.setRemoteAdapter(appWidgetId, R.id.widget_grid_view, intent);
//
//        Intent appIntent = new Intent(context, IngredientAndStepDetailsActivity.class);
//        PendingIntent appPendingIntent = PendingIntent.getActivity(context, 0, appIntent, PendingIntent.FLAG_UPDATE_CURRENT);
//        views.setPendingIntentTemplate(R.id.widget_grid_view, appPendingIntent);
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.recipe_widget_provider);

        if (sp != null) {
            id = sp.getInt(Constants.SP_FAVORITE, -1);
        }

        if (id == -1) {
            int i = 1;
            if (mRecipe != null) {
                List<Ingredient> ingredients = mRecipe.get(0).getIngredients();
                heading = mRecipe.get(0).getName() + " : Ingredients";
                for (Ingredient ingredient : ingredients) {
                    ingredientsString += String.valueOf(i) + ". " + String.valueOf((long) ingredient.getQuantity()) + " " + measureMap.get(ingredient.getMeasure()) + " " + ingredient.getIngredient() + "\n\n";
                    i++;
                }
                Log.d("abc", ingredientsString);
            }
        } else {
            int k = 1;
            if (mRecipe != null) {
                List<Ingredient> ingredients = mRecipe.get(id-1).getIngredients();
                heading = mRecipe.get(id - 1).getName() + " : Ingredients";
                for (Ingredient ingredient : ingredients) {
                    ingredientsString += String.valueOf(k) + ". " + String.valueOf((long) ingredient.getQuantity()) + " " + measureMap.get(ingredient.getMeasure()) + " " + ingredient.getIngredient() + "\n\n";
                    k++;
                }
                Log.d("abc", ingredientsString);
            }
        }

        views.setTextViewText(R.id.appwidget_text, ingredientsString);
        views.setTextViewText(R.id.heading, heading);

        views.setEmptyView(R.id.widget_grid_view, R.id.empty_view);
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
//        appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetIds, R.id.widget_grid_view);
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);

        }

        update(context, appWidgetManager, appWidgetIds, null);
//        appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetIds, R.id.widget_grid_view);

    }

    @Override
    public void onEnabled(Context context) {
    }

    @Override
    public void onDisabled(Context context) {
    }

    public static void setRecipeIngredient(List<Recipe> recipe) {
        mRecipe = recipe;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.hasExtra(Constants.WIDGET_IDS_KEY)) {
            int[] ids = intent.getExtras().getIntArray(Constants.WIDGET_IDS_KEY);
            this.onUpdate(context, AppWidgetManager.getInstance(context), ids);
        } else super.onReceive(context, intent);
    }

    public void update(Context context, AppWidgetManager manager, int[] ids, Object data) {

        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.recipe_widget_provider);
        for (int widgetId : ids) {
            ingredientsString = "";
            int id = sp.getInt(Constants.SP_FAVORITE, -1);
            if (id == -1) {
                int i = 1;
                if (mRecipe != null) {
                    List<Ingredient> ingredients = mRecipe.get(0).getIngredients();
                    heading = mRecipe.get(0).getName() + " : Ingredients";
                    for (Ingredient ingredient : ingredients) {
                        ingredientsString += String.valueOf(i) + ". " + String.valueOf((long) ingredient.getQuantity()) + " " + measureMap.get(ingredient.getMeasure()) + " " + ingredient.getIngredient() + "\n\n";
                        i++;
                    }
                    Log.d("abc", ingredientsString);
                }
            } else {
                int k = 1;
                if (mRecipe != null) {
                    List<Ingredient> ingredients = mRecipe.get(id-1).getIngredients();
                    heading = mRecipe.get(id - 1).getName() + " : Ingredients";
                    for (Ingredient ingredient : ingredients) {
                        ingredientsString += String.valueOf(k) + ". " + String.valueOf((long) ingredient.getQuantity()) + " " + measureMap.get(ingredient.getMeasure()) + " " + ingredient.getIngredient() + "\n\n";
                        k++;
                    }
                    Log.d("abc", ingredientsString);
                }
            }
            views.setTextViewText(R.id.appwidget_text, ingredientsString);
            views.setTextViewText(R.id.heading, heading);
            manager.updateAppWidget(widgetId, views);
        }
    }

    public static void updateMyWidgets(Context context) {
        AppWidgetManager man = AppWidgetManager.getInstance(context);
        int[] ids = man.getAppWidgetIds(
                new ComponentName(context, RecipeWidgetProvider.class));
        Intent updateIntent = new Intent();
        updateIntent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
        updateIntent.putExtra(Constants.WIDGET_IDS_KEY, ids);
        context.sendBroadcast(updateIntent);
    }

}

