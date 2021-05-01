package mk.ukim.finki.foody.adapters

import android.view.*
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.card.MaterialCardView
import com.google.android.material.snackbar.Snackbar
import mk.ukim.finki.foody.R
import mk.ukim.finki.foody.data.database.entities.FavouritesEntity
import mk.ukim.finki.foody.databinding.FavouriteRecipesRowLayoutBinding
import mk.ukim.finki.foody.ui.fragments.favourites.FavouriteRecipesFragmentDirections
import mk.ukim.finki.foody.util.RecipesDiffUtil
import mk.ukim.finki.foody.viewmodels.MainViewModel

class FavouriteRecipesAdapter(
    private val requireActivity: FragmentActivity,
    private val mainViewModel: MainViewModel
): RecyclerView.Adapter<FavouriteRecipesAdapter.MyViewHolder>(), ActionMode.Callback {
    private var favouriteRecipes = emptyList<FavouritesEntity>()
    private var myViewHolders = arrayListOf<MyViewHolder>()
    private var multiSelection = false
    private var selectedRecipes = arrayListOf<FavouritesEntity>()

    private lateinit var rootView: View
    private lateinit var mActionMode: ActionMode

    class MyViewHolder(private val binding: FavouriteRecipesRowLayoutBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(favouritesEntity: FavouritesEntity) {
            binding.favouritesEntity = favouritesEntity
            binding.executePendingBindings()
        }
        companion object {
            fun from(parent: ViewGroup): MyViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = FavouriteRecipesRowLayoutBinding.inflate(layoutInflater, parent, false)
                return MyViewHolder(binding)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        myViewHolders.add(holder)
        rootView = holder.itemView.rootView
        val currentRecipe = favouriteRecipes[position]
        holder.bind(currentRecipe)
        val favouriteRecipesRow = holder.itemView.findViewById<ConstraintLayout>(R.id.favouriteRecipesRowLayout)
        saveItemStateOnScroll(currentRecipe, holder)

        //Single click listener
        favouriteRecipesRow.setOnClickListener {
            if(multiSelection) {
                applySelection(holder, currentRecipe)
            }else {
                val action = FavouriteRecipesFragmentDirections.actionFavouriteRecipesFragmentToDetailsActivity(currentRecipe.result)
                holder.itemView.findNavController().navigate(action)
            }

        }

        //Long click listener
        favouriteRecipesRow.setOnLongClickListener {
            if(!multiSelection){
                multiSelection = true
                requireActivity.startActionMode(this)
                applySelection(holder, currentRecipe)
                true
            } else {
                applySelection(holder, currentRecipe)
                true
            }

        }
    }

    private fun saveItemStateOnScroll(currentRecipe: FavouritesEntity, holder: MyViewHolder) {
        if(selectedRecipes.contains(currentRecipe)) {
            changeRecipeStyle(holder, R.color.cardBackgroundLightColor, R.color.colorPrimary)
        }else {
            changeRecipeStyle(holder, R.color.cardBackgroundColor, R.color.strokeColor)
        }
    }

    private fun applySelection(holder: MyViewHolder, currentRecipe: FavouritesEntity) {
        if(selectedRecipes.contains(currentRecipe)) {
            selectedRecipes.remove(currentRecipe)
            changeRecipeStyle(holder, R.color.cardBackgroundColor, R.color.strokeColor)
            applyActionModeTitle()
        }else {
            selectedRecipes.add(currentRecipe)
            changeRecipeStyle(holder, R.color.cardBackgroundLightColor, R.color.colorPrimary)
            applyActionModeTitle()
        }
    }

    private fun changeRecipeStyle(holder: MyViewHolder, backgroundColor: Int, strokeColor: Int) {
        val insideCardConstraintLayout = holder.itemView.findViewById<ConstraintLayout>(R.id.insideCard_constraintLayout)
        val favouriteRowCard = holder.itemView.findViewById<MaterialCardView>(R.id.favouriteRow_cardView)
        insideCardConstraintLayout.setBackgroundColor(ContextCompat.getColor(requireActivity, backgroundColor))
        favouriteRowCard.strokeColor = ContextCompat.getColor(requireActivity, strokeColor)
    }

    override fun getItemCount(): Int {
        return favouriteRecipes.size
    }


    private fun applyActionModeTitle() {
        when(selectedRecipes.size) {
            0 -> {
                mActionMode.finish()
                multiSelection = false
            }
            1 -> {
                mActionMode.title = "${selectedRecipes.size} item selected"
            }
            else -> {
                mActionMode.title = "${selectedRecipes.size} items selected"

            }
        }
    }


    override fun onCreateActionMode(mode: ActionMode?, menu: Menu?): Boolean {
        mode?.menuInflater?.inflate(R.menu.favourites_contextual_menu, menu)
        mActionMode = mode!!
        applyStatusBarColor(R.color.contextualStatusBarColor)
        return true
    }

    override fun onPrepareActionMode(mode: ActionMode?, menu: Menu?): Boolean {
        return true
    }

    override fun onActionItemClicked(mode: ActionMode?, item: MenuItem?): Boolean {
        if(item?.itemId == R.id.deleteFavouriteRecipeMenu) {
            selectedRecipes.forEach{
                mainViewModel.deleteFavouriteRecipe(it)

            }
            showSnackBar("${selectedRecipes.size} Recipe/s removed.")
            multiSelection = false
            selectedRecipes.clear()
            mode?.finish()
        }
        return true
    }

    override fun onDestroyActionMode(mode: ActionMode?) {
        myViewHolders.forEach{holder ->
            changeRecipeStyle(holder, R.color.cardBackgroundColor, R.color.strokeColor)
        }
        multiSelection = false
        selectedRecipes.clear()
        applyStatusBarColor(R.color.statusBarColor)
    }

    private fun applyStatusBarColor(color: Int) {
        requireActivity.window.statusBarColor = ContextCompat.getColor(requireActivity, color)
    }

    fun setData(newFavouriteRecipe: List<FavouritesEntity>) {
        val favouritesDiffUtil = RecipesDiffUtil(favouriteRecipes, newFavouriteRecipe)
        val diffUtilResult = DiffUtil.calculateDiff(favouritesDiffUtil)
        favouriteRecipes = newFavouriteRecipe
        diffUtilResult.dispatchUpdatesTo(this)
    }

    private fun showSnackBar(message: String) {
        Snackbar.make(rootView, message, Snackbar.LENGTH_SHORT).setAction("Okay"){}.show()
    }

    fun clearContextualActionMode() {
        if(this::mActionMode.isInitialized) {
            mActionMode.finish()
        }
    }
}