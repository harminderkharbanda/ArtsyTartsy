package com.android.artsytartsy.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.android.artsytartsy.R;
import com.android.artsytartsy.RecipeWidgetProvider;
import com.android.artsytartsy.data.data.model.Ingredient;
import com.android.artsytartsy.data.data.model.Recipe;
import com.android.artsytartsy.data.data.model.Step;

import java.util.ArrayList;

import static com.android.artsytartsy.ui.MainActivity.sp;

public class RecipeDetailsActivity extends AppCompatActivity implements RecipeDetailsFragment.OnFragmentInteractionListener, StepDetailsFragment.NavigationButtonsHandler{

    private Recipe mRecipe;
    private boolean mTwoPane;
    private IngredientDetailsFragment ingredientDetailsFragment;
    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_details);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle(R.string.recipe_detail_activity_title);
        }

        RecipeDetailsFragment recipeDetailsFragment = new RecipeDetailsFragment();
        ingredientDetailsFragment = new IngredientDetailsFragment();

        fragmentManager = getSupportFragmentManager();

        Intent intent = getIntent();

        if (intent.getSerializableExtra(Constants.IntentConstants.INTENT_KEY_RECIPE) != null) {
            mRecipe = (Recipe) intent.getSerializableExtra(Constants.IntentConstants.INTENT_KEY_RECIPE);
        }

        if (findViewById(R.id.ingredient_step_details_fragment_twopane) != null ) {

            mTwoPane = true;

            fragmentManager.beginTransaction()
                    .add(R.id.recipedetails_twopane, recipeDetailsFragment)
                    .commit();



            if (mRecipe != null) {
                ingredientDetailsFragment.setIngredientDetails((ArrayList<Ingredient>) mRecipe.getIngredients());
                fragmentManager.beginTransaction()
                        .add(R.id.ingredient_step_details_fragment_twopane, ingredientDetailsFragment)
                        .commit();
            }

        } else {

            fragmentManager.beginTransaction()
                    .add(R.id.recipedetails, recipeDetailsFragment)
                    .commit();
        }

        if (mRecipe != null) {
            recipeDetailsFragment.setStepsList(mRecipe.getSteps());
            recipeDetailsFragment.setIngredientsList((ArrayList<Ingredient>) mRecipe.getIngredients());
        }
    }

    @Override
    public void onIngredientsClicked(ArrayList<Ingredient> ingredientsList) {
        if (mTwoPane) {
            IngredientDetailsFragment ing = new IngredientDetailsFragment();
            ing.setIngredientDetails(ingredientsList);
            fragmentManager.beginTransaction()
                    .replace(R.id.ingredient_step_details_fragment_twopane, ing)
                    .commit();

        } else {
            Intent intent = new Intent(this, IngredientAndStepDetailsActivity.class);
            intent.putExtra(Constants.IntentConstants.INTENT_KEY_INGREDIENTS, ingredientsList);
            startActivity(intent);
        }
    }

    @Override
    public void onRecipeStepClicked(Step step) {
        if (mTwoPane) {
            StepDetailsFragment stepDetails = new StepDetailsFragment();
            stepDetails.setStep(step);
            fragmentManager.beginTransaction()
                    .replace(R.id.ingredient_step_details_fragment_twopane, stepDetails)
                    .commit();
        }
        else {
            Intent intent = new Intent(this, IngredientAndStepDetailsActivity.class);
            intent.putExtra(Constants.IntentConstants.INTENT_KEY_STEP, step);
            intent.putExtra(Constants.IntentConstants.INTENT_KEY_STEP_LIST, (ArrayList<Step>)mRecipe.getSteps());
            startActivity(intent);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();

        if (itemId == android.R.id.home) {
            this.finish();
        }
        if (itemId == R.id.favorite) {
            if (sp == null) {
                sp = PreferenceManager.getDefaultSharedPreferences(this);
            }
            SharedPreferences.Editor editor = sp.edit();
            if (item.getTitle().equals(this.getResources().getString(R.string.sort_by))) {
                editor.putInt(Constants.SP_FAVORITE, mRecipe.getId());
                RecipeWidgetProvider.updateMyWidgets(this);
                item.setTitle(this.getResources().getString(R.string.sort_by_unfavorite));
                Toast.makeText(this, R.string.added_to_favorite, Toast.LENGTH_SHORT).show();
            } else {
                editor.putInt(Constants.SP_FAVORITE, -1);
                RecipeWidgetProvider.updateMyWidgets(this);
                item.setTitle(this.getResources().getString(R.string.sort_by));
                Toast.makeText(this, this.getResources().getString(R.string.removed_from_favorite), Toast.LENGTH_SHORT).show();
            }
            editor.apply();
        }
        return true;
    }

    @Override
    public boolean onNextButtonClicked(Step step) {
        return false;
    }

    @Override
    public void onPreviousButtonClicked(Step step) {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.recipe_menu, menu);
        int id = sp.getInt(Constants.SP_FAVORITE, -1);
        if (mRecipe.getId() == id) {
            menu.findItem(R.id.favorite).setTitle(this.getResources().getString(R.string.sort_by_unfavorite));
        }
        return true;
    }

}
