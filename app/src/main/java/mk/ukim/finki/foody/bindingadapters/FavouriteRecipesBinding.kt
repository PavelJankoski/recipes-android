package mk.ukim.finki.foody.bindingadapters

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import mk.ukim.finki.foody.adapters.FavouriteRecipesAdapter
import mk.ukim.finki.foody.data.database.entities.FavouritesEntity

class FavouriteRecipesBinding {
    companion object {

        @BindingAdapter("setVisibility", "setData", requireAll = false)
        @JvmStatic
        fun setVisibility(view: View, favouritesEntity: List<FavouritesEntity>?, mAdapter: FavouriteRecipesAdapter?) {
            when (view) {
                is RecyclerView -> {
                    val dataCheck = favouritesEntity.isNullOrEmpty()
                    view.isInvisible = dataCheck
                    if(!dataCheck) {
                        favouritesEntity?.let {
                            mAdapter?.setData(it)
                        }
                    }
                }
                else -> view.isVisible = favouritesEntity.isNullOrEmpty()
            }
        }

    }
}