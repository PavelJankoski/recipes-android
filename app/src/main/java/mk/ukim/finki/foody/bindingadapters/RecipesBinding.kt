package mk.ukim.finki.foody.bindingadapters

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.databinding.BindingAdapter
import mk.ukim.finki.foody.data.database.entities.RecipesEntity
import mk.ukim.finki.foody.models.FoodRecipe
import mk.ukim.finki.foody.util.NetworkResult
import org.w3c.dom.Text
import java.util.*

class RecipesBinding {
    companion object {

        @BindingAdapter("readApiResponse", "readDatabase", requireAll = true)
        @JvmStatic
        fun handleReadDataErrors(
            view: View,
            apiResponse: NetworkResult<FoodRecipe>?,
            database: List<RecipesEntity>?
        ) {
            when(view) {
                is ImageView -> {
                    view.isVisible = apiResponse is NetworkResult.Error && database.isNullOrEmpty()
                }
                is TextView -> {
                    view.isVisible = apiResponse is NetworkResult.Error && database.isNullOrEmpty()
                    view.text = apiResponse?.message.toString()
                }
            }
        }

        @BindingAdapter("capitalizeFirstLetter")
        @JvmStatic
        fun capitalizeFirstLetter(textView: TextView, text: String) {
            textView.text = text.capitalize(Locale.ROOT)
        }

        @BindingAdapter("doubleToString")
        @JvmStatic
        fun doubleToString(textView: TextView, value: Double) {
            textView.text = value.toString()
        }



    }
}