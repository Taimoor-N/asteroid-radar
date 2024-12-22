package com.udacity.asteroidradar.detail


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.udacity.asteroidradar.R
import com.udacity.asteroidradar.databinding.FragmentDetailBinding
import com.udacity.asteroidradar.model.Asteroid

class DetailFragment : Fragment() {

    private lateinit var _binding: FragmentDetailBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        _binding = FragmentDetailBinding.inflate(inflater)
        _binding.lifecycleOwner = this

        val asteroid = DetailFragmentArgs.fromBundle(requireArguments()).selectedAsteroid

        _binding.asteroid = asteroid

        setDynamicLayoutContent(asteroid)

        _binding.ivFragmentDetailHelpButton.setOnClickListener {
            displayAstronomicalUnitExplanationDialog()
        }

        return _binding.root
    }

    private fun setDynamicLayoutContent(asteroid: Asteroid) {
        if (asteroid.isPotentiallyHazardous) {
            _binding.ivFragmentDetailImage.contentDescription = getString(R.string.potentially_hazardous_asteroid_image)
        } else {
            _binding.ivFragmentDetailImage.contentDescription = getString(R.string.not_hazardous_asteroid_image)
        }
    }

    private fun displayAstronomicalUnitExplanationDialog() {
        val builder = AlertDialog.Builder(requireActivity())
            .setMessage(getString(R.string.astronomical_unit_explanation))
            .setPositiveButton(android.R.string.ok, null)
        builder.create().show()
    }
}
