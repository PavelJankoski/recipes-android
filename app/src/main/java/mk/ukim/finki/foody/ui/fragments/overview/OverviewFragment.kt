package mk.ukim.finki.foody.ui.fragments.overview

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import coil.load
import mk.ukim.finki.foody.R
import mk.ukim.finki.foody.bindingadapters.RecipesRowBinding
import mk.ukim.finki.foody.databinding.FragmentOverviewBinding
import mk.ukim.finki.foody.databinding.FragmentRecipesBinding
import mk.ukim.finki.foody.models.Result
import mk.ukim.finki.foody.util.Constants.Companion.RECIPE_RESULT_KEY
import org.jsoup.Jsoup


class OverviewFragment : Fragment() {
    private var _binding: FragmentOverviewBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentOverviewBinding.inflate(inflater, container, false)
        val args = arguments
        val myBundle: Result? = args?.getParcelable(RECIPE_RESULT_KEY)
        binding.mainImageView.load(myBundle?.image)
        binding.titleTextView.text = myBundle?.title
        binding.likesTextView.text = myBundle?.aggregateLikes.toString()
        binding.timeTextView.text = myBundle?.readyInMinutes.toString()
        RecipesRowBinding.parseHtml(binding.summaryTextView, myBundle?.summary)
        updateColors(myBundle!!.vegetarian, binding.vegeterianTextView, binding.vegeterianImageView)
        updateColors(myBundle.vegan, binding.veganTextView, binding.veganImageView)
        updateColors(myBundle.cheap, binding.cheapTextView, binding.cheapImageView)
        updateColors(myBundle.dairyFree, binding.diaryFreeTextView, binding.dairyFreeImageView)
        updateColors(myBundle.glutenFree, binding.glutenFreeTextView, binding.glutenFreeImageView)
        updateColors(myBundle.veryHealthy, binding.healthyTextView, binding.healthyImageView)

        return binding.root
    }

    private fun updateColors(stateIsOn: Boolean, tv: TextView, iv: ImageView) {
        if(stateIsOn) {
            iv.setColorFilter(ContextCompat.getColor(requireContext(), R.color.green))
            tv.setTextColor(ContextCompat.getColor(requireContext(), R.color.green))
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}