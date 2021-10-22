package com.zalocoders.dvtweatherapp.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.zalocoders.dvtweatherapp.data.CurrentWeather
import com.zalocoders.dvtweatherapp.data.Favourite
import com.zalocoders.dvtweatherapp.data.ForeCast

@Database(entities = [CurrentWeather::class, ForeCast::class, Favourite::class], version = 1, exportSchema = false)
abstract class WeatherDatabase : RoomDatabase() {
    abstract fun weatherForecastDao(): WeatherForecastDao
    abstract fun savedLocationDao(): FavouriteLocationDao
}
