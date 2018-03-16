package com.android.artsytartsy.data.data.remote;

import com.android.artsytartsy.data.data.model.Recipe;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by harminder on 13/03/18.
 */

public interface RecipeService {

    @GET("/topher/2017/May/59121517_baking/baking.json")
    Call<List<Recipe>> getRecipes();
}
