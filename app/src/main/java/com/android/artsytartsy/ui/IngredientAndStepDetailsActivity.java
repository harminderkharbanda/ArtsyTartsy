package com.android.artsytartsy.ui;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.android.artsytartsy.R;
import com.android.artsytartsy.data.data.model.Ingredient;
import com.android.artsytartsy.data.data.model.Step;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class IngredientAndStepDetailsActivity extends AppCompatActivity {

    private ArrayList<Ingredient> ingredientArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingredient_and_step_details);

        Intent intent = getIntent();

        if (savedInstanceState == null) {
            FragmentManager fragmentManager = getSupportFragmentManager();


            if (intent.getSerializableExtra(Constants.IntentConstants.INTENT_KEY_INGREDIENTS) != null) {
                IngredientDetailsFragment ingredientDetailsFragment = new IngredientDetailsFragment();
                fragmentManager.beginTransaction()
                        .add(R.id.ingredient_step_details_fragment, ingredientDetailsFragment)
                        .commit();

                ingredientArrayList = (ArrayList<Ingredient>) intent.getSerializableExtra(Constants.IntentConstants.INTENT_KEY_INGREDIENTS);

                ingredientDetailsFragment.setIngredientDetails(ingredientArrayList);

            }

            if (intent.getSerializableExtra(Constants.IntentConstants.INTENT_KEY_STEP) != null) {
                StepDetailsFragment stepDetailsFragment = new StepDetailsFragment();
                fragmentManager.beginTransaction()
                        .add(R.id.ingredient_step_details_fragment, stepDetailsFragment)
                        .commit();
                Step currentStep = (Step) intent.getSerializableExtra(Constants.IntentConstants.INTENT_KEY_STEP);
                stepDetailsFragment.setStep(currentStep);
            }
        }
    }
}
