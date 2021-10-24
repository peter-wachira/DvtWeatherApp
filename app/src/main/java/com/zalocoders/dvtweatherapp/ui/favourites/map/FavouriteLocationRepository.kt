package com.zalocoders.dvtweatherapp.ui.favourites.map

import com.zalocoders.dvtweatherapp.db.FavouriteLocationDao
import com.zalocoders.dvtweatherapp.db.WeatherForecastDao
import javax.inject.Inject

class FavouriteLocationRepository @Inject constructor(
		private val favouriteLocationDao: FavouriteLocationDao,
		private val weatherForecastDao: WeatherForecastDao
) {
	
	fun getAllFavouriteLocations() = favouriteLocationDao.getAllFavouriteLocation()
	
	fun getMostCurrentCurrentWeather() = weatherForecastDao.getCurrentWeather()
	
}