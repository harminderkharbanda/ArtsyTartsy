package com.android.artsytartsy.ui;

import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.widget.TextView;

import com.android.artsytartsy.R;
import com.android.artsytartsy.data.data.model.Ingredient;
import com.android.artsytartsy.data.data.model.Recipe;
import com.android.artsytartsy.data.data.model.Step;

import java.util.ArrayList;
import java.util.List;

public class RecipeDetailsActivity extends AppCompatActivity implements RecipeDetailsFragment.OnFragmentInteractionListener{

    private Recipe mRecipe;
    private CardView ingredientsCardView;
    private TextView ingredientName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_details);

        ingredientsCardView = findViewById(R.id.ingredients_card);
        ingredientName = findViewById(R.id.ingredients_tv);

        RecipeDetailsFragment recipeDetailsFragment = new RecipeDetailsFragment();

        FragmentManager fragmentManager = getSupportFragmentManager();

        fragmentManager.beginTransaction()
                .add(R.id.recipedetails, recipeDetailsFragment)
                .commit();

        Intent intent = getIntent();

        if (intent.getSerializableExtra(Constants.IntentConstants.INTENT_KEY_RECIPE) != null) {
            mRecipe = (Recipe) intent.getSerializableExtra(Constants.IntentConstants.INTENT_KEY_RECIPE);
            recipeDetailsFragment.setStepsList(mRecipe.getSteps());
            recipeDetailsFragment.setIngredientsList((ArrayList<Ingredient>) mRecipe.getIngredients());
        }
    }

    @Override
    public void onIngredientsClicked(ArrayList<Ingredient> ingredientsList) {
        Intent intent = new Intent(this, IngredientAndStepDetailsActivity.class);
        intent.putExtra(Constants.IntentConstants.INTENT_KEY_INGREDIENTS, ingredientsList);
        startActivity(intent);
    }

    @Override
    public void onRecipeStepClicked(Step step) {
        Intent intent = new Intent(this, IngredientAndStepDetailsActivity.class);
        intent.putExtra(Constants.IntentConstants.INTENT_KEY_STEP, step);
        startActivity(intent);
    }
}
