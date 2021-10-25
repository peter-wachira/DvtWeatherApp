package com.zalocoders.dvtweatherapp.ui.home

import android.location.Location
import com.zalocoders.dvtweatherapp.data.CurrentWeather
import com.zalocoders.dvtweatherapp.data.Favourite
import com.zalocoders.dvtweatherapp.data.ForeCast
import com.zalocoders.dvtweatherapp.db.FavouriteLocationDao
import com.zalocoders.dvtweatherapp.db.WeatherForecastDao
import com.zalocoders.dvtweatherapp.di.NetworkModule
import com.zalocoders.dvtweatherapp.network.ApiService
import com.zalocoders.dvtweatherapp.network.utils.safeApiCall
import javax.inject.Inject

class HomeRepository @Inject constructor(
		private val apiService: ApiService,
		private val favouriteLocationDao: FavouriteLocationDao,
		private val weatherForecastDao: WeatherForecastDao,
		@NetworkModule.ApiKey private val apiKey: String,
) {
	
	suspend fun getCurrentWeather(location: Location) = safeApiCall {
		apiService.getCurrentWeatherByLocation(
				location.latitude.toString(),
				location.longitude.toString(),
				apiKey = apiKey
		)
	}
	
	suspend fun getForecast(location: Location) = safeApiCall {
		apiService.getCurrentForecastByLocation(
				location.latitude.toString(),
				location.longitude.toString(),
				apiKey = apiKey
		)
	}
	
	suspend fun insertCurrentLocationWeather(currentWeather: CurrentWeather) =
			weatherForecastDao.insertCurrentLocationWeather(currentWeather)
	
	suspend fun insertForeCast(foreCast: ForeCast) =
			weatherForecastDao.insertForeCast(foreCast)
	
	suspend fun insertFavouriteWeatherLocation(favourite: Favourite) =
			favouriteLocationDao.insertFavouriteLocation(favourite)
	
	fun getAllForeCasts() = weatherForecastDao.getAllForeCasts()
	
	fun getLocalCurrentWeather() = weatherForecastDao.getCurrentWeather()
	
	suspend fun deleteFavouriteLocation(favourite: Favourite) =
			favouriteLocationDao.deleteFavouriteLocation(favourite)
}
