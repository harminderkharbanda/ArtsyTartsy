package com.android.artsytartsy.ui;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.android.artsytartsy.R;
import com.android.artsytartsy.data.data.model.Ingredient;
import com.android.artsytartsy.data.data.model.Step;

import java.util.ArrayList;

public class IngredientAndStepDetailsActivity extends AppCompatActivity implements StepDetailsFragment.NavigationButtonsHandler {

    private ArrayList<Ingredient> ingredientArrayList;
    private static ArrayList<Step> stepList;
    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingredient_and_step_details);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        Intent intent = getIntent();

        if (savedInstanceState == null) {

            fragmentManager = getSupportFragmentManager();

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

            if (intent.getSerializableExtra(Constants.IntentConstants.INTENT_KEY_STEP_LIST) != null) {
                stepList = (ArrayList<Step>) intent.getSerializableExtra(Constants.IntentConstants.INTENT_KEY_STEP_LIST);
            }
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();

        if (itemId == android.R.id.home) {
            this.finish();
        }
        return true;
    }

    @Override
    public boolean onNextButtonClicked(Step step) {
        if (fragmentManager == null) {
            fragmentManager = getSupportFragmentManager();
        }
        int id = step.getId();
        StepDetailsFragment stepDetailsFragment = new StepDetailsFragment();
        fragmentManager.beginTransaction()
                .replace(R.id.ingredient_step_details_fragment, stepDetailsFragment)
                .commit();

        Step currentStep = stepList.get(id + 1);
        stepDetailsFragment.setStep(currentStep);
        if (id + 2 == stepList.size()) {
            Toast.makeText(this, R.string.last_step, Toast.LENGTH_SHORT).show();
            return true;
        }
        return false;
    }

    @Override
    public void onPreviousButtonClicked(Step step) {
        int id = step.getId();
        StepDetailsFragment stepDetailsFragment = new StepDetailsFragment();
        fragmentManager.beginTransaction()
                .replace(R.id.ingredient_step_details_fragment, stepDetailsFragment)
                .commit();

        Step currentStep = stepList.get(id - 1);
        stepDetailsFragment.setStep(currentStep);
        if (id - 1 == 0) {
            Toast.makeText(this, R.string.first_step, Toast.LENGTH_SHORT).show();
        }
    }

}
