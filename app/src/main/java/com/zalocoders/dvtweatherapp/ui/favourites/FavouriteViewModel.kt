package com.zalocoders.dvtweatherapp.ui.favourites

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zalocoders.dvtweatherapp.data.Favourite
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class FavouriteViewModel @Inject constructor(
    private val favouriteRepository: FavouriteRepository
): ViewModel() {

     fun getAllFavourites() = favouriteRepository.getAllFavouriteLocations()

     fun deleteFavouriteLocation(favourite: Favourite) = viewModelScope.launch(
        Dispatchers.IO) {
        favouriteRepository.deleteFavouriteLocation(favourite)
    }

}