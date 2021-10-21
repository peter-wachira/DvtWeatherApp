package com.zalocoders.dvtweatherapp.network

import com.zalocoders.dvtweatherapp.data.models.CurrentLocationWeatherResponse
import com.zalocoders.dvtweatherapp.data.models.ForeCastResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("weather")
    suspend fun getCurrentWeatherByLocation(
        @Query("lat") lat: String,
        @Query("lon") lon: String,
        @Query("units") units: String = "metric",
        @Query("appid") apiKey: String
    ): CurrentLocationWeatherResponse

    @GET("onecall")
    suspend fun getCurrentForecastByLocation(
        @Query("lat") lat: String,
        @Query("lon") lon: String,
        @Query("units") units: String = "metric",
        @Query("exclude") exclude: String = "current,minutely,hourly,alerts",
        @Query("appid") apiKey: String,
    ): ForeCastResponse
}