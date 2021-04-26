package mk.ukim.finki.foody.ui.fragments.favourites

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
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
    private val mAdapter: FavouriteRecipesAdapter by lazy { FavouriteRecipesAdapter() }
    private val mainViewModel: MainViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentFavouriteRecipesBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        binding.mainViewModel = mainViewModel
        binding.mAdapter = mAdapter
        setupRecyclerView()
        return binding.root
    }

    private fun setupRecyclerView() {
        binding.favouriteRecipesRecyclerView.adapter = mAdapter
        binding.favouriteRecipesRecyclerView.layoutManager = LinearLayoutManager(requireContext())
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}