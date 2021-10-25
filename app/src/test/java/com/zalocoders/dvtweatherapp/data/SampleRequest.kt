package com.zalocoders.dvtweatherapp.data

import android.location.Location

object SampleRequest {
    val sampleFavourite = Favourite(
            "Nairobi",
            -1.2907344085176307, 36.82093485505406,
            20,
            25,
            20,
            System.currentTimeMillis(),
            "sunny",
            "800"
    )
    
    val sampleCurrentWeather = CurrentWeather(
            "Nairobi",
            -1.2907344085176307, 36.82093485505406,
            20,
            25,
            20,
            System.currentTimeMillis(),
            "rainy",
            "800"
    )
    
    val testLocation = Location("this").apply {
        latitude = -1.2907344085176307
        longitude = 36.82093485505406
    }
}