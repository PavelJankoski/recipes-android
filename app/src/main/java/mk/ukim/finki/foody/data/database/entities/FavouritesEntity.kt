package mk.ukim.finki.foody.data.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import mk.ukim.finki.foody.models.Result
import mk.ukim.finki.foody.util.Constants.Companion.FAVOURITE_RECIPES_TABLE

@Entity(tableName = FAVOURITE_RECIPES_TABLE)
class FavouritesEntity(
    @PrimaryKey(autoGenerate = true)
    var id: Int,
    var result: Result
)