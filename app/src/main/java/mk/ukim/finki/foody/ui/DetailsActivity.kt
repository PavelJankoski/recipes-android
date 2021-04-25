package mk.ukim.finki.foody.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.navArgs
import com.google.android.material.tabs.TabLayoutMediator
import mk.ukim.finki.foody.R
import mk.ukim.finki.foody.adapters.PagerAdapter
import mk.ukim.finki.foody.databinding.ActivityDetailsBinding
import mk.ukim.finki.foody.ui.fragments.ingredients.IngredientsFragment
import mk.ukim.finki.foody.ui.fragments.instructions.InstructionsFragment
import mk.ukim.finki.foody.ui.fragments.overview.OverviewFragment

class DetailsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailsBinding
    private val args by navArgs<DetailsActivityArgs>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        binding.toolbar.setTitleTextColor(ContextCompat.getColor(this, R.color.white))
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        val resultBundle = Bundle()
        resultBundle.putParcelable("recipeBundle", args.result)
        val fragments = arrayListOf<Fragment>(OverviewFragment(), IngredientsFragment(), InstructionsFragment())
        fragments.forEach { f ->
            f.arguments = resultBundle
        }
        val tabLayoutTitles = arrayListOf<String>("Overview", "Ingredients", "Instructins")
        val adapter = PagerAdapter(fragments, supportFragmentManager, lifecycle)
        binding.viewPager.adapter = adapter

        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            tab.text = tabLayoutTitles[position]
        }.attach()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == android.R.id.home) {
            finish()
        }
        return super.onOptionsItemSelected(item)
    }
}