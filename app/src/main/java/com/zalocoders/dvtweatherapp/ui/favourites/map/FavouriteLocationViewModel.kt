package com.zalocoders.dvtweatherapp.ui.favourites.map

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zalocoders.dvtweatherapp.data.Favourite
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavouriteLocationViewModel @Inject constructor(
		private val favouriteRepository: FavouriteLocationRepository
) : ViewModel() {
	
	fun getAllFavourites() = favouriteRepository.getAllFavouriteLocations()
	
}