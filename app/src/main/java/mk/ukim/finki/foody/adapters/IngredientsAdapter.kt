package mk.ukim.finki.foody.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import mk.ukim.finki.foody.R
import mk.ukim.finki.foody.models.ExtendedIngredient
import mk.ukim.finki.foody.util.Constants.Companion.BASE_INGREDIENT_IMAGE_URL
import mk.ukim.finki.foody.util.RecipesDiffUtil
import java.util.*

class IngredientsAdapter : RecyclerView.Adapter<IngredientsAdapter.MyViewHolder>() {
    private var ingredientsList = emptyList<ExtendedIngredient>()

    class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.ingredients_row_layout,parent, false))
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.itemView.findViewById<ImageView>(R.id.ingredient_imageView).load(BASE_INGREDIENT_IMAGE_URL + ingredientsList[position].image) {
            crossfade(600)
            error(R.drawable.ic_error)
        }
        holder.itemView.findViewById<TextView>(R.id.ingredientName_textView).text = ingredientsList[position].name?.capitalize(Locale.ROOT)
        holder.itemView.findViewById<TextView>(R.id.ingredientAmount_textView).text = ingredientsList[position].amount.toString()
        holder.itemView.findViewById<TextView>(R.id.ingredientUnit_textView).text = ingredientsList[position].unit
        holder.itemView.findViewById<TextView>(R.id.ingredientConsistency_textView).text = ingredientsList[position].consitency
        holder.itemView.findViewById<TextView>(R.id.ingredientOriginal_textView).text = ingredientsList[position].original
    }

    override fun getItemCount(): Int {
        return ingredientsList.size
    }

    fun setData(newIngredients: List<ExtendedIngredient>) {
        val ingredientsDiffUtil = RecipesDiffUtil(ingredientsList, newIngredients)
        val diffUtilResult = DiffUtil.calculateDiff(ingredientsDiffUtil)
        ingredientsList = newIngredients
        diffUtilResult.dispatchUpdatesTo(this)
    }
}