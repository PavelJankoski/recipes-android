package mk.ukim.finki.foody.ui.fragments.instructions

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import mk.ukim.finki.foody.R
import mk.ukim.finki.foody.databinding.FragmentIngredientsBinding
import mk.ukim.finki.foody.databinding.FragmentInstructionsBinding
import mk.ukim.finki.foody.models.Result
import mk.ukim.finki.foody.util.Constants


class InstructionsFragment : Fragment() {
    private var _binding: FragmentInstructionsBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentInstructionsBinding.inflate(inflater, container, false)
        val args = arguments
        val myBundle: Result? = args?.getParcelable(Constants.RECIPE_RESULT_KEY)

        binding.instructionsWebView.webViewClient = object : WebViewClient() {}
        val websiteUrl: String? = myBundle!!.sourceUrl
        if (websiteUrl != null) {
            binding.instructionsWebView.loadUrl(websiteUrl)
        }

        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}