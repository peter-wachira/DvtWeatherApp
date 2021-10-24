package com.zalocoders.dvtweatherapp.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.zalocoders.dvtweatherapp.data.CurrentWeather
import com.zalocoders.dvtweatherapp.data.ForeCast
import kotlinx.coroutines.flow.Flow

@Dao
interface WeatherForecastDao {
	
	@Insert(onConflict = OnConflictStrategy.REPLACE)
	suspend fun insertCurrentLocationWeather(currentWeather: CurrentWeather)
	
	@Query("SELECT * FROM current_weather ORDER by lastUpdated ASC LIMIT 1")
	fun getMostCurrentCurrentWeather():Flow<CurrentWeather>
	
	@Insert(onConflict = OnConflictStrategy.REPLACE)
	suspend fun insertForeCast(foreCast: ForeCast)
	
	@Query("SELECT * FROM forecasts ORDER by lastUpdated ASC")
	fun getAllForeCasts(): Flow<List<ForeCast>>
	
	@Query("SELECT * FROM current_weather ORDER by lastUpdated ASC")
	fun getCurrentWeather(): Flow<CurrentWeather>
	
	@Query("DELETE FROM forecasts")
	suspend fun deleteAllForecasts()
}
