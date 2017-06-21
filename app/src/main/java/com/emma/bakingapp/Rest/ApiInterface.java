package com.emma.bakingapp.Rest;

import com.emma.bakingapp.Models.BaseResponse;
import com.emma.bakingapp.Models.RecipeModels;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;


public interface ApiInterface {

    @GET("android-baking-app-json")
    Call<List<RecipeModels>> getRecipeModel();

}
