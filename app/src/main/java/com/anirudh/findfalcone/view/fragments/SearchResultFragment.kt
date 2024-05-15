package com.anirudh.findfalcone.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.anirudh.findfalcone.R
import com.anirudh.findfalcone.databinding.FragmentSearchBinding
import com.anirudh.findfalcone.domain.util.Constants

class SearchResultFragment : Fragment() {
    companion object {
        @JvmStatic
        fun newInstance(status: Boolean, planetName: String, timeTaken: String) =
            SearchResultFragment().apply {
                arguments = Bundle().apply {
                    putBoolean(Constants.STATUS, status)
                    putString(Constants.PLANET_NAME, planetName)
                    putString(Constants.TIME_TAKEN, timeTaken)
                }
            }
    }


    private lateinit var binding: FragmentSearchBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSearchBinding.inflate(layoutInflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val status = arguments?.getBoolean(Constants.STATUS)
        binding.ivPlanet.setImageResource(R.drawable.airplane)
        if (status == true) {
            val planetName = arguments?.getString(Constants.PLANET_NAME)
            binding.tvTimeTaken.text = resources.getString(
                R.string.totalTimeTaken,
                arguments?.getString(Constants.TIME_TAKEN)
            )
            binding.tvResult.text = resources.getString(R.string.search_success, planetName)
        } else {
            binding.tvTimeTaken.text = ""
            binding.tvTimeTaken.text = resources.getString(
                R.string.totalTimeTaken,
                "0"
            )
            binding.tvResult.text = resources.getString(R.string.search_fail)
        }
    }

}
