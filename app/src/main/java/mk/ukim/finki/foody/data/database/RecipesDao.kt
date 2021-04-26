package mk.ukim.finki.foody.data.database

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import mk.ukim.finki.foody.data.database.entities.FavouritesEntity
import mk.ukim.finki.foody.data.database.entities.RecipesEntity

@Dao
interface RecipesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRecipes(recipesEntity: RecipesEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavouriteRecipe(favouritesEntity: FavouritesEntity)

    @Query("select * from recipes_table order by id asc")
    fun readRecipes(): Flow<List<RecipesEntity>>

    @Query("select * from favourite_recipes_table order by id asc")
    fun readFavouriteRecipes(): Flow<List<FavouritesEntity>>

    @Delete
    suspend fun deleteFavouriteRecipe(favouritesEntity: FavouritesEntity)

    @Query("delete from favourite_recipes_table")
    suspend fun deleteAllFavouriteRecipes()
}