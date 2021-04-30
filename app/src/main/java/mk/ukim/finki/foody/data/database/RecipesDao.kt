package mk.ukim.finki.foody.data.database

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import mk.ukim.finki.foody.data.database.entities.FavouritesEntity
import mk.ukim.finki.foody.data.database.entities.FoodJokeEntity
import mk.ukim.finki.foody.data.database.entities.RecipesEntity
import mk.ukim.finki.foody.models.FoodJoke

@Dao
interface RecipesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRecipes(recipesEntity: RecipesEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavouriteRecipe(favouritesEntity: FavouritesEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFoodJoke(foodJokeEntity: FoodJokeEntity)

    @Query("select * from recipes_table order by id asc")
    fun readRecipes(): Flow<List<RecipesEntity>>

    @Query("select * from favourite_recipes_table order by id asc")
    fun readFavouriteRecipes(): Flow<List<FavouritesEntity>>

    @Query("select * from food_joke_table order by id asc")
    fun readFoodJoke(): Flow<List<FoodJokeEntity>>

    @Delete
    suspend fun deleteFavouriteRecipe(favouritesEntity: FavouritesEntity)

    @Query("delete from favourite_recipes_table")
    suspend fun deleteAllFavouriteRecipes()
}