package com.zalocoders.dvtweatherapp.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "forecasts")
data class ForeCast(
		@PrimaryKey(autoGenerate = false)
		@ColumnInfo(name = "day") val day: String = "",
		@ColumnInfo(name = "locationName") val locationName: String,
		@ColumnInfo(name = "lat") val lat: Double,
		@ColumnInfo(name = "lng") val lng: Double,
		@ColumnInfo(name = "normalTemp") val normalTemp: Int,
		@ColumnInfo(name = "lastUpdated") val lastUpdated: Long,
		@ColumnInfo(name = "weatherCondition") val weatherCondition: String
)

@Entity(tableName = "current_weather")
data class CurrentWeather(
		@ColumnInfo(name = "locationName") val name: String,
		@PrimaryKey(autoGenerate = false)
		@ColumnInfo(name = "lat") val lat: Double,
		@ColumnInfo(name = "lng") val lng: Double,
		@ColumnInfo(name = "normalTemp") val normalTemp: Int,
		@ColumnInfo(name = "highTemp") val highTemp: Int,
		@ColumnInfo(name = "lowTemp") val lowTemp: Int,
		@ColumnInfo(name = "lastUpdated") val lastUpdated: Long,
		@ColumnInfo(name = "condition") val weatherCondition: String,
		@ColumnInfo(name = "conditionName") val weatherConditionName: String
)

@Entity(tableName = "favourite_locations")
data class Favourite(
		@ColumnInfo(name = "locationName") val name: String,
		@PrimaryKey(autoGenerate = false)
		@ColumnInfo(name = "lat") val lat: Double,
		@ColumnInfo(name = "lng") val lng: Double,
		@ColumnInfo(name = "normalTemp") val normalTemp: Int,
		@ColumnInfo(name = "highTemp") val highTemp: Int,
		@ColumnInfo(name = "lowTemp") val lowTemp: Int,
		@ColumnInfo(name = "lastUpdated") val lastUpdated: Long,
		@ColumnInfo(name = "condition") val weatherCondition: String,
		@ColumnInfo(name = "conditionName") val weatherConditionName: String
)


