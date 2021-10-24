package com.zalocoders.dvtweatherapp.ui.favourites.map

import com.zalocoders.dvtweatherapp.data.Favourite
import com.zalocoders.dvtweatherapp.db.FavouriteLocationDao
import javax.inject.Inject

class FavouriteLocationRepository @Inject constructor(
		private val favouriteLocationDao: FavouriteLocationDao
) {
	
	fun getAllFavouriteLocations() = favouriteLocationDao.getAllFavouriteLocation()
	
	suspend fun deleteFavouriteLocation(favourite: Favourite) =
			favouriteLocationDao.deleteFavouriteLocation(favourite)
	
}