package com.android.artsytartsy.ui;

import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.artsytartsy.R;
import com.android.artsytartsy.RecipeStepsAdapter;
import com.android.artsytartsy.data.data.model.Ingredient;
import com.android.artsytartsy.data.data.model.Step;

import java.util.ArrayList;
import java.util.List;

import static com.android.artsytartsy.ui.Constants.SAVED_POSITION;

public class RecipeDetailsFragment extends Fragment implements RecipeStepsAdapter.RecipeStepClickListener{

    private OnFragmentInteractionListener mListener;
    private RecyclerView mRecyclerView;
    private List<Step> stepList;
    private ArrayList<Ingredient> ingredientsList;
    private CardView ingredientsCard;
    private static Parcelable layoutManagerSavedState;

    public RecipeDetailsFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            if (savedInstanceState != null) {
                layoutManagerSavedState = savedInstanceState.getParcelable(SAVED_POSITION);
            }
        }
        View rootView = inflater.inflate(R.layout.fragment_recipe_details, container, false);
        mRecyclerView = rootView.findViewById(R.id.steps_recyclerview);
        ingredientsCard = rootView.findViewById(R.id.ingredients_card);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));

        RecipeStepsAdapter recipeStepsAdapter = new RecipeStepsAdapter(this);
        recipeStepsAdapter.setStepsData(stepList);
        mRecyclerView.setAdapter(recipeStepsAdapter);
        if (layoutManagerSavedState != null) {
            restoreLayoutManagerPosition();
        }
        mRecyclerView.setHasFixedSize(true);
        ingredientsCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onIngredientsClicked(ingredientsList);
            }
        });
        return rootView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onRecipeStepClicked(Step step) {
        mListener.onRecipeStepClicked(step);
    }

    public interface OnFragmentInteractionListener {
        void onIngredientsClicked(ArrayList<Ingredient> ingredientsList);
        void onRecipeStepClicked(Step step);
    }

    public void setStepsList(List<Step> stepsList) {
        this.stepList = stepsList;
    }

    public void setIngredientsList(ArrayList<Ingredient> ingredientsList) {
        this.ingredientsList = ingredientsList;
    }

    @Override
    public void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        bundle.putParcelable(SAVED_POSITION, mRecyclerView.getLayoutManager().onSaveInstanceState());
    }

    private void restoreLayoutManagerPosition() {
        if (layoutManagerSavedState != null) {
            mRecyclerView.getLayoutManager().onRestoreInstanceState(layoutManagerSavedState);
        }
    }

}
