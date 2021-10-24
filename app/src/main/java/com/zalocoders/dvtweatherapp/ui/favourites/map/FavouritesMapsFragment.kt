package com.zalocoders.dvtweatherapp.ui.favourites.map


import android.annotation.SuppressLint
import androidx.fragment.app.Fragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MapStyleOptions
import com.google.android.gms.maps.model.MarkerOptions
import com.zalocoders.dvtweatherapp.R
import com.zalocoders.dvtweatherapp.databinding.FragmentFavouritesMapsBinding
import com.zalocoders.dvtweatherapp.utils.getWeatherIcon
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import timber.log.Timber

@AndroidEntryPoint
class FavouritesMapsFragment : Fragment(),OnMapReadyCallback {
	
	private val binding:FragmentFavouritesMapsBinding by lazy{
		FragmentFavouritesMapsBinding.inflate(layoutInflater)
	}
	
	private lateinit var favouritesMap: GoogleMap
	
	private val viewModel :FavouriteLocationViewModel by viewModels()
	
	override fun onCreateView(inflater: LayoutInflater,
							  container: ViewGroup?,
							  savedInstanceState: Bundle?): View = binding.root
	
	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
		mapFragment?.getMapAsync(this)
		
	}
	
	@SuppressLint("MissingPermission")
	override fun onMapReady(map: GoogleMap) {
		favouritesMap = map
		favouritesMap.isBuildingsEnabled = false
		favouritesMap.uiSettings.isMapToolbarEnabled = false
		favouritesMap.uiSettings.isMyLocationButtonEnabled = false
		favouritesMap.isMyLocationEnabled = true
		
		favouritesMap.setMapStyle(
				MapStyleOptions.loadRawResourceStyle(
						binding.root.context,
						R.raw.maps_styling
				)
		)
		zoomMapWithCurrentLocation()
		addLocationsToMap()
	}
	
	private fun zoomMapWithCurrentLocation(){
		lifecycleScope.launchWhenStarted {
			viewModel.getMostCurrentCurrentWeather().collect { currentWeather ->
				val latLng = LatLng(currentWeather.lat, currentWeather.lng)
				favouritesMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng,5F))
			}
		}
		
	}
	
	private fun addLocationsToMap(){
		lifecycleScope.launchWhenStarted {
			viewModel.getAllFavourites().collect { favouriteLocations ->
				
				if(favouriteLocations.isNotEmpty()){
					
					for(favourite in favouriteLocations){
						val latLng = LatLng(favourite.lat,favourite.lng)
						val condition = favourite.weatherConditionName
						
						val markerOptions = MarkerOptions()
						markerOptions.title("${favourite.name} was "+ condition)
						markerOptions.position(latLng)
						
						when {
							condition.contains("rain", true) -> {
								markerOptions.icon(binding.root.context.getWeatherIcon(R.drawable.rain))
							}
							condition.contains("sun", true) -> {
								markerOptions.icon(binding.root.context.getWeatherIcon(R.drawable.clear))
							}
							condition.contains("cloud", true) -> {
								markerOptions.icon(binding.root.context.getWeatherIcon(R.drawable.clouds))
							}
						}
						favouritesMap.addMarker(markerOptions)
						
					}
				}
			}
		}
	}
	
}