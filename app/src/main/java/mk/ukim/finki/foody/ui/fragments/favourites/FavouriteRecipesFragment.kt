package mk.ukim.finki.foody.ui.fragments.favourites

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import mk.ukim.finki.foody.R
import mk.ukim.finki.foody.adapters.FavouriteRecipesAdapter
import mk.ukim.finki.foody.databinding.FragmentFavouriteRecipesBinding
import mk.ukim.finki.foody.databinding.FragmentIngredientsBinding
import mk.ukim.finki.foody.viewmodels.MainViewModel

@AndroidEntryPoint
class FavouriteRecipesFragment : Fragment() {
    private var _binding: FragmentFavouriteRecipesBinding? = null
    private val binding get() = _binding!!
    private val mainViewModel: MainViewModel by viewModels()
    private val mAdapter: FavouriteRecipesAdapter by lazy { FavouriteRecipesAdapter(requireActivity(), mainViewModel) }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentFavouriteRecipesBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        binding.mainViewModel = mainViewModel
        binding.mAdapter = mAdapter
        setHasOptionsMenu(true)
        setupRecyclerView()
        return binding.root
    }

    private fun setupRecyclerView() {
        binding.favouriteRecipesRecyclerView.adapter = mAdapter
        binding.favouriteRecipesRecyclerView.layoutManager = LinearLayoutManager(requireContext())
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.favourite_recipes_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.delete_all_favourite_recipes_menu) {
            mainViewModel.deleteAllFavouriteRecipes()
            showSnackbar()
        }
        return super.onOptionsItemSelected(item)

    }

    private fun showSnackbar() {
        Snackbar.make(binding.root, "All recipes removed.", Snackbar.LENGTH_SHORT).setAction("Okay"){}.show()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
        mAdapter.clearContextualActionMode()
    }
}