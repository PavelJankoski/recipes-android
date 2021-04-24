package mk.ukim.finki.foody.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import mk.ukim.finki.foody.util.Constants
import mk.ukim.finki.foody.util.Constants.Companion.API_KEY
import mk.ukim.finki.foody.util.Constants.Companion.QUERY_ADD_RECIPE_INFORMATION
import mk.ukim.finki.foody.util.Constants.Companion.QUERY_API_KEY
import mk.ukim.finki.foody.util.Constants.Companion.QUERY_DIET
import mk.ukim.finki.foody.util.Constants.Companion.QUERY_FILL_INGREDIENTS
import mk.ukim.finki.foody.util.Constants.Companion.QUERY_NUMBER
import mk.ukim.finki.foody.util.Constants.Companion.QUERY_TYPE

class RecipesViewModel(application: Application): AndroidViewModel(application) {
    fun applyQueries(): HashMap<String, String> {
        val queries: HashMap<String, String> = HashMap()
        queries[QUERY_NUMBER] = "50"
        queries[QUERY_API_KEY] = API_KEY
        queries[QUERY_TYPE] = "snack"
        queries[QUERY_ADD_RECIPE_INFORMATION] = "true"
        queries[QUERY_FILL_INGREDIENTS] = "true"

        return queries
    }
}