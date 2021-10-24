package com.zalocoders.dvtweatherapp.ui.favourites.map

import com.zalocoders.dvtweatherapp.BaseTest
import com.zalocoders.dvtweatherapp.data.SampleRequest
import com.zalocoders.dvtweatherapp.ui.home.HomeRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.hamcrest.MatcherAssert
import org.hamcrest.core.Is.`is`
import org.junit.Before
import org.junit.Test

class FavouriteLocationRepositoryTest:BaseTest(){
	
	private lateinit var favouriteLocationRepository: FavouriteLocationRepository
	private lateinit var homeRepository: HomeRepository
	
	@Before
	override fun setup() {
		super.setup()
		favouriteLocationRepository = FavouriteLocationRepository(favouriteDao ,foreCastDao)
		homeRepository = HomeRepository(apiService,favouriteDao ,foreCastDao,"api key")
	}
	
	@Test
	fun `test  favourite crud to database`() {
		runBlocking {
			homeRepository.insertFavouriteWeatherLocation(SampleRequest.sampleFavourite)
			
			favouriteLocationRepository.getAllFavouriteLocations()
			
			val favouriteLocation = favouriteLocationRepository.getAllFavouriteLocations().first().toList()[0]
			
			MatcherAssert.assertThat(favouriteLocation.name, `is`(SampleRequest.sampleFavourite.name))
		}
	}
}