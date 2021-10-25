package com.zalocoders.dvtweatherapp.ui.home

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.location.Location
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.view.Window
import android.view.WindowManager
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import androidx.palette.graphics.Palette
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.karumi.dexter.Dexter
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.single.PermissionListener
import com.zalocoders.dvtweatherapp.BuildConfig
import com.zalocoders.dvtweatherapp.R
import com.zalocoders.dvtweatherapp.data.CurrentWeather
import com.zalocoders.dvtweatherapp.data.ForeCast
import com.zalocoders.dvtweatherapp.databinding.ActivityMainBinding
import com.zalocoders.dvtweatherapp.db.mappers.toCurrentWeatherEntity
import com.zalocoders.dvtweatherapp.db.mappers.toForeCastEntity
import com.zalocoders.dvtweatherapp.network.utils.WeatherResult
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.collect
import timber.log.Timber
import java.util.*
import kotlin.system.exitProcess
import com.github.ivbaranov.mfb.MaterialFavoriteButton.OnFavoriteChangeListener
import com.google.android.material.snackbar.Snackbar
import com.zalocoders.dvtweatherapp.db.mappers.toFavouriteLocationEntity
import com.zalocoders.dvtweatherapp.ui.favourites.FavouritesActivity
import com.zalocoders.dvtweatherapp.utils.NetworkUtils
import com.zalocoders.dvtweatherapp.utils.geoCodeLocation
import com.zalocoders.dvtweatherapp.utils.hide
import com.zalocoders.dvtweatherapp.utils.isLocationPermissionEnabled
import com.zalocoders.dvtweatherapp.utils.isUserLocationEnabled
import com.zalocoders.dvtweatherapp.utils.show
import com.zalocoders.dvtweatherapp.utils.showRetrySnackBar
import com.zalocoders.dvtweatherapp.utils.showSnackbar

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
	private lateinit var binding: ActivityMainBinding
	
	private val viewModel: HomeViewModel by viewModels()
	
	private lateinit var currentLocationWeather: CurrentWeather
	private lateinit var currentLocationName: String
	
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		binding = ActivityMainBinding.inflate(layoutInflater)
		setContentView(binding.root)
		
		binding.favouriteLocationsFab.setOnClickListener {
			startActivity(Intent(this,FavouritesActivity::class.java))
		}
		
		checkForLocationPermission()
		observeViewModel()
		addToFavourite()
	}
	
	private fun getUserCurrentLocation() {
		lifecycleScope.launchWhenStarted {
			viewModel.getCurrentLocation().collect { lastLocation ->
				
				Timber.e(lastLocation.latitude.toString())
				cancel()
				if (NetworkUtils.isOnline(this@MainActivity)) {
					getCurrentWeather(lastLocation)
					getForecast(lastLocation)
				} else {
					getLocalCurrentWeather()
					getLocalWeatherForecast()
				}
			}
		}
	}
	
	private fun getForecast(location: Location) {
		lifecycleScope.launchWhenStarted {
			viewModel.getForecast(location).collect { response ->
				when (response) {
					is WeatherResult.Success -> {
						val geoCodeResult = geoCodeLocation(location.latitude, location.longitude)
						
						if(geoCodeResult.isNotEmpty()){
							val data = response.data.toForeCastEntity(geoCodeResult)
							
							if (data.isNotEmpty()) {
								
								setUpRecyclerView(data)
								
								for (forecast in data) {
									viewModel.insertForeCast(forecast)
								}
							}
						}
					}
					is WeatherResult.InternalError -> {
						getLocalWeatherForecast()
					}
				}
			}
		}
	}
	
	private fun getCurrentWeather(location: Location) {
		lifecycleScope.launchWhenStarted {
			viewModel.getCurrentWeather(location).collect { response ->
				when (response) {
					is WeatherResult.Success -> {
						val currentWeather = response.data.toCurrentWeatherEntity(response.data.name)
						currentLocationWeather = currentWeather
						currentLocationName = response.data.name
						binding.addToFavourite.show()
						
						viewModel.insertCurrentLocation(currentWeather)
						
						setBackGroundImage(currentWeather)
						setUpCurrentWeatherViews(currentWeather)
					}
					is WeatherResult.ServerError -> {
						binding.root.showRetrySnackBar("You are offline") {
							checkForLocationPermission()
						}
					}
					is WeatherResult.InternalError -> {
						getLocalCurrentWeather()
						binding.root.showRetrySnackBar("Offline Mode") {
							checkForLocationPermission()
						}
					}
				}
			}
		}
	}
	
	@SuppressLint("SetTextI18n")
	private fun setUpCurrentWeatherViews(currentWeather: CurrentWeather) {
		with(binding) {
			tvTemp.text = currentWeather.normalTemp.toString() + " \u2103"
			tvWeatherDesc.text = currentWeather.weatherConditionName.uppercase(Locale.getDefault())
			tvMaxTitle.text = currentWeather.highTemp.toString() + " \u2103"
			tvMinTitle.text = currentWeather.lowTemp.toString() + " \u2103"
			tvCurrentTitle.text = currentWeather.normalTemp.toString() + " \u2103"
		}
	}
	
	private fun observeViewModel() {
		viewModel.isLoading.observe(this@MainActivity, {
			when (it) {
				true -> {
					binding.progressLayout.show()
				}
				false -> {
					binding.progressLayout.hide()
				}
			}
		})
	}
	
	private fun getLocalWeatherForecast() {
		lifecycleScope.launchWhenStarted {
			viewModel.getAllForeCasts().collect { forecast ->
				setUpRecyclerView(forecast)
			}
		}
	}
	
	private fun getLocalCurrentWeather() {
		lifecycleScope.launchWhenStarted {
			viewModel.getLocalCurrentWeather().collect { currentWeather ->
				setBackGroundImage(currentWeather)
				setUpCurrentWeatherViews(currentWeather)
				currentLocationWeather = currentWeather
				currentLocationName = currentWeather.name
				binding.addToFavourite.show()
			}
		}
	}
	
	private fun checkForLocationPermission() {
		if (isLocationPermissionEnabled()) {
			if (isUserLocationEnabled()) {
				getUserCurrentLocation()
			} else {
				enableLocation()
			}
		} else {
			requestLocationPermission()
		}
	}
	
	private fun requestLocationPermission() {
		Dexter.withContext(this@MainActivity)
				.withPermission(Manifest.permission.ACCESS_FINE_LOCATION)
				.withListener(object : PermissionListener {
					override fun onPermissionGranted(ermissionGrantedResponse: PermissionGrantedResponse) {
						if (isUserLocationEnabled()) {
							getUserCurrentLocation()
						} else {
							enableLocation()
						}
					}
					
					override fun onPermissionDenied(permissionDeniedResponse: PermissionDeniedResponse) {
						// permission denied
					}
					
					override fun onPermissionRationaleShouldBeShown(permissionRequest: PermissionRequest, permissionToken: PermissionToken) {
						// permission denied
					}
					
				}).check()
	}
	
	private fun enableLocation() {
		MaterialAlertDialogBuilder(this).apply {
			setMessage("Please Enable location")
			setPositiveButton("ENABLE") { dialog, _ ->
				val packageName = BuildConfig.APPLICATION_ID
				dialog.dismiss()
				Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
					data = Uri.parse("package:$packageName")
					startActivity(this)
				}
			}
			setNegativeButton("CANCEL") { dialog, _ ->
				dialog.dismiss()
				exitProcess(0)
			}
			show()
		}
	}
	
	private fun setUpRecyclerView(result: List<ForeCast>) {
		val weatherForeCastAdapter = WeatherForeCastAdapter()
		binding.forecastRecyclerview.apply {
			adapter = weatherForeCastAdapter
			layoutManager = LinearLayoutManager(context)
		}
		weatherForeCastAdapter.submitList(result)
	}
	
	private fun getBitmapResource(drawable: Int): Bitmap {
		return BitmapFactory.decodeResource(
				resources,
				drawable
		)
	}
	
	private fun addToFavourite() {
		binding.addToFavourite.setOnFavoriteChangeListener(
				OnFavoriteChangeListener { _, favorite ->
					if (favorite) {
						viewModel.insertFavouriteWeatherLocation(currentLocationWeather.toFavouriteLocationEntity(currentLocationName))
						binding.root.showSnackbar("Added to Favourites", Snackbar.LENGTH_SHORT)
					} else {
						viewModel.deleteFavouriteLocation(currentLocationWeather.toFavouriteLocationEntity(currentLocationName))
						binding.root.showSnackbar("Removed from Favourites", Snackbar.LENGTH_SHORT)
					}
				})
	}
	
	private fun updateStatusBarColor(bitMap: Bitmap) {
		
		Palette.Builder(bitMap)
				.generate { result ->
					
					result?.let {
						val dominantSwatch = it.dominantSwatch
						
						if (dominantSwatch != null) {
							if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
								val window: Window = window
								window.statusBarColor = dominantSwatch.rgb
							} else {
								val window: Window = window
								window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
								window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
								window.statusBarColor = dominantSwatch.rgb
							}
						}
					}
				}
	}
	
	private fun setBackGroundImage(currentWeather: CurrentWeather) {
		when {
			currentWeather.weatherConditionName.contains("rain", true) -> {
				binding.apply {
					rootLayout.setBackgroundColor(ContextCompat.getColor(this@MainActivity,R.color.rainy))
					currentWeatherLayout.setBackgroundResource(R.drawable.sea_rainy)
				}
				val bitMap: Bitmap = getBitmapResource(R.drawable.sea_rainy)
				updateStatusBarColor(bitMap)
			}
			currentWeather.weatherConditionName.contains("sun", true) -> {
				binding.apply {
					rootLayout.setBackgroundColor(ContextCompat.getColor(this@MainActivity,R.color.sunny))
					currentWeatherLayout.setBackgroundResource(R.drawable.sea_sunnypng)
				}
				val bitMap: Bitmap = getBitmapResource(R.drawable.sea_sunnypng)
				updateStatusBarColor(bitMap)
			}
			currentWeather.weatherConditionName.contains("clear", true) -> {
				binding.apply {
					rootLayout.setBackgroundColor(ContextCompat.getColor(this@MainActivity,R.color.sunny))
					currentWeatherLayout.setBackgroundResource(R.drawable.sea_sunnypng)
				}
				val bitMap: Bitmap = getBitmapResource(R.drawable.sea_sunnypng)
				updateStatusBarColor(bitMap)
			}
			currentWeather.weatherConditionName.contains("clouds", true) -> {
				binding.apply {
					rootLayout.setBackgroundColor(ContextCompat.getColor(this@MainActivity,R.color.cloudy))
					currentWeatherLayout.setBackgroundResource(R.drawable.sea_cloudy)
				}
				val bitMap: Bitmap = getBitmapResource(R.drawable.sea_cloudy)
				updateStatusBarColor(bitMap)
			}
		}
	}
	
}
