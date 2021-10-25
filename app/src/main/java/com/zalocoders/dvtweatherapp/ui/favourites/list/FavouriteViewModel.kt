package com.zalocoders.dvtweatherapp.ui.favourites.list

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FavouriteViewModel @Inject constructor(
		private val favouriteRepository: FavouriteRepository
) : ViewModel() {
	
	fun getAllFavourites() = favouriteRepository.getAllFavouriteLocations()
	
}