package com.zalocoders.dvtweatherapp.ui.favourites.map

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FavouriteLocationViewModel @Inject constructor(
		private val favouriteRepository: FavouriteLocationRepository
) : ViewModel() {
	
	fun getAllFavourites() = favouriteRepository.getAllFavouriteLocations()
	
	fun getMostCurrentCurrentWeather() = favouriteRepository.getMostCurrentCurrentWeather()
}