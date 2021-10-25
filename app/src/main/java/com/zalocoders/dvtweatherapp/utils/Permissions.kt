package com.zalocoders.dvtweatherapp.utils

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.location.LocationManager
import androidx.core.app.ActivityCompat
import androidx.core.location.LocationManagerCompat
import java.io.IOException
import java.util.*

fun Context.isLocationPermissionEnabled(): Boolean {
	return ActivityCompat.checkSelfPermission(
			this,
			Manifest.permission.ACCESS_FINE_LOCATION
	) == PackageManager.PERMISSION_GRANTED
}

fun Context.isUserLocationEnabled(): Boolean {
	val locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
	return LocationManagerCompat.isLocationEnabled(locationManager)
}


fun Context.geoCodeLocation(lat: Double, lon: Double): String {
	val geocoder = Geocoder(this, Locale.getDefault())
	try {
		val addresses: List<Address> = geocoder.getFromLocation(lat, lon, 1)
		val address: Address = addresses[0]
		return address.adminArea
	} catch (e: Throwable) {
		throw IOException(e)
	}
}