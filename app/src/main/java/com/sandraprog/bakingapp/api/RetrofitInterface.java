package com.sandraprog.bakingapp.api;

import com.sandraprog.bakingapp.model.Recipe;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by sandrapog on 17.06.2018.
 */

public interface RetrofitInterface {
     @GET("baking.json")
    Call<List<Recipe>> getRecipeList();
}