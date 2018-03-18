package com.android.artsytartsy;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.artsytartsy.data.data.model.Recipe;
import com.squareup.picasso.Picasso;

import java.util.List;

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
        if (recipe.getName().equals("Nutella Pie")) {
            Picasso.get().load(R.drawable.nutellapie).into(holder.recipeImage);
        }
        if (recipe.getName().equals("Brownies")) {
            Picasso.get().load(R.drawable.brownie).into(holder.recipeImage);
        }
        if (recipe.getName().equals("Yellow Cake")) {
            Picasso.get().load(R.drawable.yellowcake).into(holder.recipeImage);
        }
        if (recipe.getName().equals("Cheesecake")) {
            Picasso.get().load(R.drawable.cheesecake).into(holder.recipeImage);
        }

    }

    @Override
    public int getItemCount() {
        if (null == recipeList) return 0;
        return recipeList.size();
    }

    public class RecipesViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView recipeName;
        private ImageView recipeImage;

        public RecipesViewHolder(View itemView) {
            super(itemView);
            recipeName = itemView.findViewById(R.id.recipe_name);
            recipeImage = itemView.findViewById(R.id.recipe_iv);
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
