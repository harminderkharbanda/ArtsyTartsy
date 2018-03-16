package com.android.artsytartsy.ui;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.artsytartsy.R;
import com.android.artsytartsy.RecipeStepsAdapter;
import com.android.artsytartsy.data.data.model.Ingredient;
import com.android.artsytartsy.data.data.model.Recipe;
import com.android.artsytartsy.data.data.model.Step;

import java.util.ArrayList;
import java.util.List;

///**
// * A simple {@link Fragment} subclass.
// * Activities that contain this fragment must implement the
// * {@link RecipeDetailsFragment.OnFragmentInteractionListener} interface
// * to handle interaction events.
// * Use the {@link RecipeDetailsFragment#newInstance} factory method to
// * create an instance of this fragment.
// */
public class RecipeDetailsFragment extends Fragment implements RecipeStepsAdapter.RecipeStepClickListener{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
//    private static final String ARG_PARAM1 = "param1";
//    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
//    private String mParam1;
//    private String mParam2;

    private OnFragmentInteractionListener mListener;
    private RecyclerView mRecyclerView;
    private List<Step> stepList;
    private ArrayList<Ingredient> ingredientsList;
    private CardView ingredientsCard;

    public RecipeDetailsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
//     * @param param1 Parameter 1.
//     * @param param2 Parameter 2.
     * @return A new instance of fragment RecipeDetailsFragment.
     */
    // TODO: Rename and change types and number of parameters
//    public static RecipeDetailsFragment newInstance(String param1, String param2) {
//        RecipeDetailsFragment fragment = new RecipeDetailsFragment();
//        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
//        fragment.setArguments(args);
//        return fragment;
//    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        if (getArguments() != null) {
//            mParam1 = getArguments().getString(ARG_PARAM1);
//            mParam2 = getArguments().getString(ARG_PARAM2);
//        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_recipe_details, container, false);
        mRecyclerView = rootView.findViewById(R.id.steps_recyclerview);
        ingredientsCard = rootView.findViewById(R.id.ingredients_card);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));

        RecipeStepsAdapter recipeStepsAdapter = new RecipeStepsAdapter(this);
        recipeStepsAdapter.setStepsData(stepList);
        mRecyclerView.setAdapter(recipeStepsAdapter);
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

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onIngredientsClicked(ArrayList<Ingredient> ingredientsList);
        void onRecipeStepClicked(Step step);
    }

    public void setStepsList(List<Step> stepsList) {
        this.stepList = stepsList;
    }

    public void setIngredientsList(ArrayList<Ingredient> ingredientsList) {
        this.ingredientsList = ingredientsList;
    }
}
