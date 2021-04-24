package mk.ukim.finki.foody.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import mk.ukim.finki.foody.models.FoodRecipe
import mk.ukim.finki.foody.util.Constants.Companion.RECIPES_TABLE

@Entity(tableName = RECIPES_TABLE)
class RecipesEntity(
    var foodRecipe: FoodRecipe
) {
    @PrimaryKey(autoGenerate = false)
    var id: Int = 0
}