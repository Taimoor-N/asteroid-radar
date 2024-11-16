package com.udacity.asteroidradar.main

import android.os.Bundle
import android.view.*
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.udacity.asteroidradar.R
import com.udacity.asteroidradar.adapters.AsteroidAdapter
import com.udacity.asteroidradar.adapters.AsteroidClickListener
import com.udacity.asteroidradar.databinding.FragmentMainBinding

class MainFragment : Fragment() {

    private lateinit var _binding: FragmentMainBinding

    private val _viewModel: MainViewModel by lazy {
        ViewModelProvider(this)[MainViewModel::class.java]
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        _binding = FragmentMainBinding.inflate(inflater)
        _binding.lifecycleOwner = this
        _binding.viewModel = _viewModel

        initializeRecyclerView()
        addObservers()
        addMenuItems()

        return _binding.root
    }

    private fun addMenuItems() {
        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(object : MenuProvider {

            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                // Add menu items here
                menuInflater.inflate(R.menu.main_overflow_menu, menu)
            }

            override fun onMenuItemSelected(item: MenuItem): Boolean {
                // Handle the menu selection
                return when (item.itemId) {
                    R.id.show_all_menu -> {
                        return true
                    }
                    R.id.show_rent_menu -> {
                        return true
                    }
                    R.id.show_buy_menu -> {
                        return true
                    }
                    else -> false
                }
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }

    private fun initializeRecyclerView() {
        val adapter = AsteroidAdapter(AsteroidClickListener {
                asteroid -> _viewModel.onAsteroidClicked(asteroid)
        })
        _binding.rvFragmentMain.adapter = adapter

        _viewModel.asteroids.observe(viewLifecycleOwner) {
            it?.let {
                adapter.submitList(it)
            }
        }
    }

    private fun addObservers() {
        _viewModel.navigateToAsteroidDetails.observe(viewLifecycleOwner) {
            it?.let {
                this.findNavController().navigate(MainFragmentDirections.actionShowDetail(it))
                _viewModel.doneNavigating()
            }
        }
    }

}
