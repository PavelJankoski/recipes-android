package mk.ukim.finki.foody.ui.fragments.ingredients

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import mk.ukim.finki.foody.R
import mk.ukim.finki.foody.adapters.IngredientsAdapter
import mk.ukim.finki.foody.databinding.FragmentIngredientsBinding
import mk.ukim.finki.foody.databinding.FragmentOverviewBinding
import mk.ukim.finki.foody.models.Result
import mk.ukim.finki.foody.util.Constants.Companion.RECIPE_RESULT_KEY

class IngredientsFragment : Fragment() {
    private var _binding: FragmentIngredientsBinding? = null
    private val binding get() = _binding!!
    private val mAdapter: IngredientsAdapter by lazy { IngredientsAdapter() }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentIngredientsBinding.inflate(inflater, container, false)
        val args = arguments
        val myBundle: Result? = args?.getParcelable(RECIPE_RESULT_KEY)
        setupRecyclerView()
        myBundle?.extendedIngredients?.let { mAdapter.setData(it) }
        return binding.root
    }

    private fun setupRecyclerView() {
        binding.ingredientsRecyclerView.adapter = mAdapter
        binding.ingredientsRecyclerView.layoutManager = LinearLayoutManager(requireContext())
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}