package com.zalocoders.dvtweatherapp.ui.favourites.list

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.zalocoders.dvtweatherapp.databinding.FragmentFavouritesBinding
import com.zalocoders.dvtweatherapp.utils.hide
import com.zalocoders.dvtweatherapp.utils.show
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class FavouritesFragment : Fragment() {
	
	private val binding: FragmentFavouritesBinding by lazy {
		FragmentFavouritesBinding.inflate(layoutInflater)
	}
	
	private val viewModel: FavouriteViewModel by viewModels()
	
	override fun onCreateView(
			inflater: LayoutInflater, container: ViewGroup?,
			savedInstanceState: Bundle?
	): View = binding.root
	
	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		getAllFavouriteLocations()
		initViews()
	}
	
	private fun initViews() {
		binding.viewMapBtn.setOnClickListener {
			val action = FavouritesFragmentDirections.actionFavouritesFragment2ToFavouritesMapsFragment()
			findNavController().navigate(action)
		}
	}
	
	private fun getAllFavouriteLocations() {
		val favouriteLocationsAdapter = FavouriteLocationsAdapter()
		
		lifecycleScope.launchWhenStarted {
			viewModel.getAllFavourites().collect { favouriteLocations ->
				if (favouriteLocations.isNotEmpty()) {
					binding.errorLayout.hide()
				} else {
					binding.errorLayout.show()
					binding.viewMapBtn.hide()
				}
				
				with(binding.favouriteRecycler) {
					adapter = favouriteLocationsAdapter
				}
				favouriteLocationsAdapter.submitList(favouriteLocations)
			}
		}
	}
}