package com.zalocoders.dvtweatherapp.ui.home

import android.annotation.SuppressLint
import android.location.Location
import android.os.Looper
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.zalocoders.dvtweatherapp.data.CurrentWeather
import com.zalocoders.dvtweatherapp.data.Favourite
import com.zalocoders.dvtweatherapp.data.ForeCast
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
		private val homeRepository: HomeRepository,
		private val client: FusedLocationProviderClient
) : ViewModel() {
	
	private var _isLoading: MutableLiveData<Boolean> = MutableLiveData()
	val isLoading: LiveData<Boolean> = _isLoading
	
	fun getCurrentWeather(location: Location) = flow {
		_isLoading.value = true
		emit(homeRepository.getCurrentWeather(location))
		_isLoading.value = false
	}
	
	fun getForecast(location: Location) = flow {
		_isLoading.value = true
		emit(homeRepository.getForecast(location))
		_isLoading.value = false
	}
	
	fun insertCurrentLocation(currentWeather: CurrentWeather) =
			viewModelScope.launch(Dispatchers.IO) {
				homeRepository.insertCurrentLocationWeather(currentWeather)
			}
	
	fun insertForeCast(foreCast: ForeCast) =
			viewModelScope.launch(Dispatchers.IO) {
				homeRepository.insertForeCast(foreCast)
			}
	
	fun insertFavouriteWeatherLocation(favourite: Favourite) =
			viewModelScope.launch(Dispatchers.IO) {
				homeRepository.insertFavouriteWeatherLocation(favourite)
			}
	
	fun deleteFavouriteLocation(favourite: Favourite) =
			viewModelScope.launch(Dispatchers.IO) {
				homeRepository.deleteFavouriteLocation(favourite)
			}
	
	fun getAllForeCasts() = homeRepository.getAllForeCasts()
	fun getCurrentWeather() = homeRepository.getCurrentWeather()
	
	@ExperimentalCoroutinesApi
	@SuppressLint("MissingPermission")
	fun getCurrentLocation(): Flow<Location> = callbackFlow {
		val locationRequest = LocationRequest.create().apply {
			UPDATE_INTERVAL
			FASTEST_UPDATE_INTERVAL
			LocationRequest.PRIORITY_HIGH_ACCURACY
		}
		val callBack = object : LocationCallback() {
			override fun onLocationResult(locationResult: LocationResult) {
				super.onLocationResult(locationResult)
				val location = locationResult.lastLocation
				Timber.e("Current Location $location")
				offer(location)
			}
		}
		client.requestLocationUpdates(locationRequest, callBack, Looper.getMainLooper())
		awaitClose { client.removeLocationUpdates(callBack) }
	}
	
	companion object {
		private const val UPDATE_INTERVAL = 1000L
		private const val FASTEST_UPDATE_INTERVAL = UPDATE_INTERVAL
	}
}
