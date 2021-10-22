package com.zalocoders.dvtweatherapp.di

import android.content.Context
import androidx.room.Room
import com.zalocoders.dvtweatherapp.db.WeatherDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    
    @Provides
    fun provideWeatherDatabase(@ApplicationContext context: Context) =
            Room.databaseBuilder(context, WeatherDatabase::class.java, "weather_db")
                    .fallbackToDestructiveMigration()
                    .build()
    
    @Provides
    fun provideWeatherForecastDao(weatherDatabase: WeatherDatabase) = weatherDatabase.weatherForecastDao()
    
    @Provides
    fun provideSavedLocationDao(weatherDatabase: WeatherDatabase) = weatherDatabase.savedLocationDao()
}
