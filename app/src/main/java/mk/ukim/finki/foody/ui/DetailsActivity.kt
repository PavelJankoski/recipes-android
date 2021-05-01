package mk.ukim.finki.foody.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.navArgs
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint
import mk.ukim.finki.foody.R
import mk.ukim.finki.foody.adapters.PagerAdapter
import mk.ukim.finki.foody.data.database.entities.FavouritesEntity
import mk.ukim.finki.foody.databinding.ActivityDetailsBinding
import mk.ukim.finki.foody.ui.fragments.ingredients.IngredientsFragment
import mk.ukim.finki.foody.ui.fragments.instructions.InstructionsFragment
import mk.ukim.finki.foody.ui.fragments.overview.OverviewFragment
import mk.ukim.finki.foody.util.Constants.Companion.RECIPE_RESULT_KEY
import mk.ukim.finki.foody.viewmodels.MainViewModel
import java.lang.Exception

@AndroidEntryPoint
class DetailsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailsBinding
    private val args by navArgs<DetailsActivityArgs>()
    private val mainViewModel: MainViewModel by viewModels()
    private var recipeSaved = false
    private var savedRecipeId = 0

    private lateinit var menuItem: MenuItem

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        binding.toolbar.setTitleTextColor(ContextCompat.getColor(this, R.color.white))
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        val resultBundle = Bundle()
        resultBundle.putParcelable(RECIPE_RESULT_KEY, args.result)
        val fragments = arrayListOf<Fragment>(OverviewFragment(), IngredientsFragment(), InstructionsFragment())
        val tabLayoutTitles = arrayListOf<String>("Overview", "Ingredients", "Instructins")
        val pagerAdapter = PagerAdapter(resultBundle, fragments, this)
        binding.viewPager.apply {
            adapter = pagerAdapter
        }

        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            tab.text = tabLayoutTitles[position]
        }.attach()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.details_menu, menu)
        menuItem = menu!!.findItem(R.id.save_to_favourites_menu)
        checkSavedRecipes(menuItem)
        return true
    }

    private fun checkSavedRecipes(menuItem: MenuItem) {
        mainViewModel.readFavouriteRecipes.observe(this, {favouritesEntity ->
            try {
                for(savedRecipe in favouritesEntity) {
                    if(savedRecipe.result.id == args.result.id) {
                        changeMenuItemColor(menuItem, R.color.yellow)
                        savedRecipeId = savedRecipe.id
                        recipeSaved = true
                    }
                }
            } catch (e: Exception) {
                Log.d("DetailsActivity", e.message.toString())
            }
        })
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == android.R.id.home) {
            finish()
        }
        else if(item.itemId == R.id.save_to_favourites_menu && !recipeSaved) {
            saveToFavourites(item)
        } else if (item.itemId == R.id.save_to_favourites_menu && recipeSaved) {
            removeFromFavourites(item)
        }
        return super.onOptionsItemSelected(item)
    }

    private fun saveToFavourites(item: MenuItem) {
        val favouritesEntity = FavouritesEntity (0, args.result)
        mainViewModel.insertFavouriteRecipe(favouritesEntity)
        changeMenuItemColor(item, R.color.yellow)
        showSnackBar("Recipe saved.")
        recipeSaved = true
    }

    private fun removeFromFavourites(item: MenuItem) {
        val favouritesEntity = FavouritesEntity(
            savedRecipeId,
            args.result
        )
        mainViewModel.deleteFavouriteRecipe(favouritesEntity)
        changeMenuItemColor(item, R.color.white)
        showSnackBar("Removed from Favourites.")
        recipeSaved = false
    }

    private fun showSnackBar(message: String) {
        Snackbar.make(
            binding.detailsLayout,
            message,
            Snackbar.LENGTH_SHORT
        ).setAction("Okay") {}.show()
    }

    private fun changeMenuItemColor(item: MenuItem, color: Int) {
        item.icon.setTint(ContextCompat.getColor(this, color))
    }

    override fun onDestroy() {
        super.onDestroy()
        changeMenuItemColor(menuItem, R.color.white)
    }
}