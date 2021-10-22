package com.zalocoders.dvtweatherapp.network.utils

sealed class WeatherResult<out R> {
    
    /**
     * This is used to represent successful responses (2xx response codes, non empty response bodies)
     */
    data class Success<out T>(val data: T) : WeatherResult<T>()
    
    /**
     * Used to represent Server errors (non 2xx status code)
     */
    data class ServerError(
            val code: Int? = null,
            val errorBody: ErrorResponse? = null
    ) : WeatherResult<Nothing>()
    
    /**
     * Used to represent connectivity errors (a request that didn't result in a response)
     */
    object InternalError : WeatherResult<Nothing>()
}
