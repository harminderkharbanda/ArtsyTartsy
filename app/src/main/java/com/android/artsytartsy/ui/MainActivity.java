package com.android.artsytartsy.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.android.artsytartsy.R;
import com.android.artsytartsy.RecipesAdapter;
import com.android.artsytartsy.data.data.model.Recipe;
import com.android.artsytartsy.data.data.remote.ApiUtils;
import com.android.artsytartsy.data.data.remote.RecipeService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import java.io.Serializable;

public class MainActivity extends AppCompatActivity implements RecipesAdapter.RecipeClickListener {


    private RecipeService mService;
    private RecyclerView mRecyclerView;
    private RecipesAdapter mRecipesAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRecyclerView = findViewById(R.id.recipes_recyclerview);
        mRecipesAdapter = new RecipesAdapter(this);

        if (findViewById(R.id.recipes_framelayout) != null) {
            mRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        } else {
            mRecyclerView.setLayoutManager(new GridLayoutManager(this, 1));
        }
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setAdapter(mRecipesAdapter);

        mService = ApiUtils.getRecipeService();

        loadRecipes();
    }

    private void loadRecipes() {
        mService.getRecipes().enqueue(new Callback<List<Recipe>>() {
            @Override
            public void onResponse(Call<List<Recipe>> call, Response<List<Recipe>> response) {
                if (response.isSuccessful()) {
                    mRecipesAdapter.setRecipeData(response.body());
                    mRecipesAdapter.notifyDataSetChanged();
                    Log.d("MainActivity", String.valueOf(response.body().get(0)));
                } else {
                    int statusCode  = response.code();
                    Log.d("MainActivity", String.valueOf(statusCode));
                }
            }

            @Override
            public void onFailure(Call<List<Recipe>> call, Throwable t) {
                Log.d("MainActivity", "error loading from API");
            }
        });
    }

    @Override
    public void onRecipeClicked(Recipe recipe) {
        Intent intent = new Intent(this, RecipeDetailsActivity.class);
        intent.putExtra(Constants.IntentConstants.INTENT_KEY_RECIPE, recipe);
        startActivity(intent);
    }


}
