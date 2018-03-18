package com.android.artsytartsy.ui;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;
import android.support.test.espresso.IdlingResource;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.artsytartsy.GridWidgetService;
import com.android.artsytartsy.IdlingResource.SimpleIdlingResource;
import com.android.artsytartsy.R;
import com.android.artsytartsy.RecipeWidgetProvider;
import com.android.artsytartsy.RecipesAdapter;
import com.android.artsytartsy.data.data.model.Recipe;
import com.android.artsytartsy.data.data.remote.ApiUtils;
import com.android.artsytartsy.data.data.remote.RecipeService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements RecipesAdapter.RecipeClickListener {


    private RecipeService mService;
    private RecyclerView mRecyclerView;
    private RecipesAdapter mRecipesAdapter;
    @Nullable
    private SimpleIdlingResource mIdlingResource;
    private GridWidgetService.GridRemoteViewsFactory mRemoteViewsFactory;
    private ProgressBar mProgressBar;
    private TextView mErrorTextView;

    @VisibleForTesting
    @NonNull
    public IdlingResource getIdlingResource() {
        if (mIdlingResource == null) {
            mIdlingResource = new SimpleIdlingResource();
        }
        return mIdlingResource;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mProgressBar = findViewById(R.id.pb_loading_indicator);
        mProgressBar.setVisibility(View.VISIBLE);
        mErrorTextView = findViewById(R.id.tv_error_message);
        mRecyclerView = findViewById(R.id.recipes_recyclerview);
        mRecipesAdapter = new RecipesAdapter(this);
        mRemoteViewsFactory = new GridWidgetService.GridRemoteViewsFactory(getApplicationContext());


        if (findViewById(R.id.recipes_framelayout) != null) {
            mRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        } else {
            mRecyclerView.setLayoutManager(new GridLayoutManager(this, 1));
        }
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setAdapter(mRecipesAdapter);

        mService = ApiUtils.getRecipeService();

        getIdlingResource();

        loadRecipes(mIdlingResource);
    }

    private void loadRecipes( @Nullable final SimpleIdlingResource idlingResource) {
        if (idlingResource != null) {
            idlingResource.setIdleState(false);
        }
        mService.getRecipes().enqueue(new Callback<List<Recipe>>() {
            @Override
            public void onResponse(Call<List<Recipe>> call, Response<List<Recipe>> response) {
                if (response.isSuccessful()) {
                    mProgressBar.setVisibility(View.INVISIBLE);
                    mErrorTextView.setVisibility(View.INVISIBLE);
                    AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(getApplicationContext());
                    int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(getApplicationContext(), RecipeWidgetProvider.class));
                    mRecipesAdapter.setRecipeData(response.body());
                    mRemoteViewsFactory.setData(response.body());
                    appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetIds, R.id.widget_grid_view);
                    mRecipesAdapter.notifyDataSetChanged();
                    if (idlingResource != null) {
                        idlingResource.setIdleState(true);
                    }
                    Log.d("MainActivity", String.valueOf(response.body().get(0)));
                } else {
                    int statusCode  = response.code();
                    Log.d("MainActivity", String.valueOf(statusCode));
                }
            }

            @Override
            public void onFailure(Call<List<Recipe>> call, Throwable t) {
                mProgressBar.setVisibility(View.INVISIBLE);
                mErrorTextView.setVisibility(View.VISIBLE);
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
