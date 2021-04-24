package mk.ukim.finki.foody.viewmodels

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import mk.ukim.finki.foody.data.DataStoreRepository
import mk.ukim.finki.foody.util.Constants
import mk.ukim.finki.foody.util.Constants.Companion.API_KEY
import mk.ukim.finki.foody.util.Constants.Companion.DEFAULT_DIET_TYPE
import mk.ukim.finki.foody.util.Constants.Companion.DEFAULT_MEAL_TYPE
import mk.ukim.finki.foody.util.Constants.Companion.DEFAULT_RECIPES_NUMBER
import mk.ukim.finki.foody.util.Constants.Companion.QUERY_ADD_RECIPE_INFORMATION
import mk.ukim.finki.foody.util.Constants.Companion.QUERY_API_KEY
import mk.ukim.finki.foody.util.Constants.Companion.QUERY_DIET
import mk.ukim.finki.foody.util.Constants.Companion.QUERY_FILL_INGREDIENTS
import mk.ukim.finki.foody.util.Constants.Companion.QUERY_NUMBER
import mk.ukim.finki.foody.util.Constants.Companion.QUERY_TYPE
import javax.inject.Inject

@HiltViewModel
class RecipesViewModel @Inject constructor(
    private val dataStoreRepository: DataStoreRepository,
    application: Application
): AndroidViewModel(application) {
    private var mealType = DEFAULT_MEAL_TYPE
    private var dietType = DEFAULT_DIET_TYPE

    var networkStatus = false
    var backOnline = false

    val readMealAndDietType = dataStoreRepository.readMealAndDietType
    val readBackOnline = dataStoreRepository.readBackOnline.asLiveData()


    fun saveMealAndDietType(mealType: String, mealTypeId: Int, dietType: String, dietTypeId: Int) = viewModelScope.launch(Dispatchers.IO){
        dataStoreRepository.saveMealAndDietType(mealType, mealTypeId, dietType, dietTypeId)
    }

    fun saveBackOnline(backOnline: Boolean) = viewModelScope.launch(Dispatchers.IO) {
        dataStoreRepository.saveBackOnlone(backOnline)
    }

    fun applyQueries(): HashMap<String, String> {
        val queries: HashMap<String, String> = HashMap()
        viewModelScope.launch {
            readMealAndDietType.collect { value ->
                mealType = value.selectedMealType
                dietType = value.selectedDietType
            }
        }
        queries[QUERY_NUMBER] = DEFAULT_RECIPES_NUMBER
        queries[QUERY_API_KEY] = API_KEY
        queries[QUERY_TYPE] = mealType
        queries[QUERY_DIET] = dietType
        queries[QUERY_ADD_RECIPE_INFORMATION] = "true"
        queries[QUERY_FILL_INGREDIENTS] = "true"

        return queries
    }

    fun showNetworkStatus() {
        if(!networkStatus) {
            Toast.makeText(getApplication(), "No Internet Connection.", Toast.LENGTH_SHORT).show()
            saveBackOnline(true)
        } else {
            if(backOnline) {
                Toast.makeText(getApplication(), "We're back online!", Toast.LENGTH_SHORT).show()
                saveBackOnline(false)
            }
        }
    }

}