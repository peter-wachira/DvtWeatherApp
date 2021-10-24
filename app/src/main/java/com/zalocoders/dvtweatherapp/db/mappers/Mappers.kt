package com.zalocoders.dvtweatherapp.db.mappers

import com.zalocoders.dvtweatherapp.data.CurrentWeather
import com.zalocoders.dvtweatherapp.data.Favourite
import com.zalocoders.dvtweatherapp.data.ForeCast
import com.zalocoders.dvtweatherapp.data.models.CurrentLocationWeatherResponse
import com.zalocoders.dvtweatherapp.data.models.ForeCastResponse
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import kotlin.collections.ArrayList

fun ForeCastResponse.toForeCastEntity(name: String): List<ForeCast> {
	val items: MutableList<ForeCast> = ArrayList()
	
	for (weatherForecast in daily) {
		val foreCastEntity = ForeCast(
				day = formatTime(weatherForecast.dt.toLong()),
				locationName = name,
				lat = lat,
				lng = lon,
				normalTemp = weatherForecast.temp.day.toInt(),
				lastUpdated = weatherForecast.dt.toLong(),
				weatherCondition = weatherForecast.weather[0].id.toString()
		)
		
		items.add(foreCastEntity)
	}
	
	return items
}

fun CurrentLocationWeatherResponse.toCurrentWeatherEntity(locationName: String = ""): CurrentWeather {
	return CurrentWeather(
			name = if (name.trim().isNotEmpty()) locationName else name,
			lat = coord.lat,
			lng = coord.lon,
			normalTemp = this.main.temp.toInt(),
			highTemp = main.tempMax.toInt(),
			lowTemp = main.tempMin.toInt(),
			lastUpdated = System.currentTimeMillis(),
			weatherCondition = weather[0].id.toString(),
			weatherConditionName = weather[0].description
	)
}

fun CurrentWeather.toFavouriteLocationEntity(locationName: String = ""): Favourite {
	return Favourite(
			name = if (name.trim().isNotEmpty()) locationName else name,
			lat = lat,
			lng = lng,
			normalTemp = normalTemp,
			highTemp = highTemp,
			lowTemp = lowTemp,
			lastUpdated = System.currentTimeMillis(),
			weatherCondition = weatherCondition,
			weatherConditionName = weatherConditionName
	)
}

fun formatTime(currentTime: Long): String {
	val epoch = currentTime * TIME_INTERVAL
	val dateFormat = SimpleDateFormat("EEEE", Locale.getDefault())
	return dateFormat.format(Date(epoch))
}

private const val TIME_INTERVAL = 1000L
