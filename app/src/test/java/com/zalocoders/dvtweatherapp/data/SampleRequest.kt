package com.zalocoders.dvtweatherapp.data

import com.zalocoders.dvtweatherapp.data.Favourite

object SampleRequest {
    val sampleFavourite = Favourite(
            "Nairobi",
            -1.2907344085176307, 36.82093485505406,
            20,
            25,
            20,
            System.currentTimeMillis(),
            true,
            "800",
            "Clear"

    )
    
    val sampleCurrentWeather = CurrentWeather(
            "Nairobi",
            -1.2907344085176307, 36.82093485505406,
            20,
            25,
            20,
            System.currentTimeMillis(),
            true,
            "800",
            "Clear"
    
    )
}