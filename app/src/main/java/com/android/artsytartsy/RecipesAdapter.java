package com.android.artsytartsy;

import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.artsytartsy.data.data.model.Recipe;

import java.util.List;
import java.util.Random;

/**
 * Created by harminder on 13/03/18.
 */

public class RecipesAdapter extends RecyclerView.Adapter<RecipesAdapter.RecipesViewHolder> {

    private final RecipeClickListener listener;
    private List<Recipe> recipeList;

    public RecipesAdapter(RecipeClickListener listener) {
        this.listener = listener;
    }

    public interface RecipeClickListener {
        void onRecipeClicked(Recipe recipe);
    }

    @Override
    public RecipesViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recipe_card, viewGroup, false);
        return new RecipesViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(RecipesViewHolder holder, int position) {
        Recipe recipe = recipeList.get(position);
        holder.recipeName.setText(recipe.getName());
        Random rnd = new Random();
        int color = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
        holder.recipeCard.setBackgroundColor(color);
    }

    @Override
    public int getItemCount() {
        if (null == recipeList) return 0;
        return recipeList.size();
    }

    public class RecipesViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView recipeName;
        private CardView recipeCard;

        public RecipesViewHolder(View itemView) {
            super(itemView);
            recipeName = itemView.findViewById(R.id.recipe_name);
            recipeCard = itemView.findViewById(R.id.recipe_card);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            listener.onRecipeClicked(recipeList.get(getAdapterPosition()));
        }
    }

    public void setRecipeData(List<Recipe> recipeData) {
        recipeList = recipeData;
    }
}
