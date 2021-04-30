package mk.ukim.finki.foody.data

import mk.ukim.finki.foody.data.network.FoodRecipesApi
import mk.ukim.finki.foody.models.FoodJoke
import mk.ukim.finki.foody.models.FoodRecipe
import retrofit2.Response
import javax.inject.Inject

class RemoteDataSource @Inject constructor(
    private val foodRecipesApi: FoodRecipesApi
) {
    suspend fun getRecipes(queries: Map<String, String>): Response<FoodRecipe> {
        return foodRecipesApi.getRecipes(queries)
    }

    suspend fun searchRecipes(searchQuery: Map<String, String>): Response<FoodRecipe> {
        return foodRecipesApi.searchRecipes(searchQuery)
    }

    suspend fun getFoodJoke(apiKey: String) : Response<FoodJoke> {
        return foodRecipesApi.getFoodJoke(apiKey)
    }
}