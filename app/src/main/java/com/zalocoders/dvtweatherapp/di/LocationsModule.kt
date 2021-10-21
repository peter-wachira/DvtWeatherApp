package com.zalocoders.dvtweatherapp.di

import android.content.Context
import android.location.Geocoder
import com.google.android.gms.location.LocationServices
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LocationsModule {

    @Provides
    @Singleton
    fun provideGeoCoder(@ApplicationContext context: Context) = Geocoder(context)

    @Provides
    @Singleton
    fun provideFusedLocationClient(@ApplicationContext context: Context) =
        LocationServices.getFusedLocationProviderClient(context)
}
