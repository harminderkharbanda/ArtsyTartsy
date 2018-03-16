package com.android.artsytartsy.ui;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.artsytartsy.R;
import com.android.artsytartsy.data.data.model.Ingredient;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class IngredientDetailsFragment extends Fragment {

    private ArrayList<Ingredient> ingredients;
    String ingredientsString = "";
    private TextView ingredientsDetails;

    Map<String, String> measureMap = new HashMap<String, String>()
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

    public IngredientDetailsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if(savedInstanceState != null) {
            ingredients = (ArrayList<Ingredient>) savedInstanceState.getSerializable(Constants.KEY_SAVE_INGREDIENT);
        }

        View rootView = inflater.inflate(R.layout.fragment_ingredient_details, container, false);
        ingredientsDetails = rootView.findViewById(R.id.ingredients_details_tv);
        int i = 1;
        for(Ingredient ingredient : ingredients) {
            ingredientsString += String.valueOf(i) + ". " + String.valueOf((int)ingredient.getQuantity()) + " " + measureMap.get(ingredient.getMeasure()) + " " + ingredient.getIngredient() + "\n\n";
            i++;
        }
        ingredientsDetails.setText(ingredientsString);

        return rootView;
    }

    public void setIngredientDetails(ArrayList<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putSerializable(Constants.KEY_SAVE_INGREDIENT, ingredients);
    }
}
