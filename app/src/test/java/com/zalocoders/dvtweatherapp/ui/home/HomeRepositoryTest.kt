package com.zalocoders.dvtweatherapp.ui.home

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.zalocoders.dvtweatherapp.data.SampleResponses
import com.google.common.truth.Truth
import com.zalocoders.dvtweatherapp.BaseTest
import com.zalocoders.dvtweatherapp.data.SampleRequest
import com.zalocoders.dvtweatherapp.network.utils.WeatherResult
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.hamcrest.MatcherAssert
import org.hamcrest.core.Is.`is`
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class HomeRepositoryTest:BaseTest() {
	
	private lateinit var homeRepository: HomeRepository
	
	@Before
	override fun setup() {
		super.setup()
		homeRepository = HomeRepository(apiService,favouriteDao ,foreCastDao,"api key")
	}
	
	@Test
	fun `test getting forecast from remote`() {
		runBlocking {
			val response = homeRepository.getForecast(location = SampleResponses.testLocation)
			
			Truth.assertThat(response is WeatherResult.Success)
		}
	}
	
	@Test
	fun `test getting current weather from remote`() {
		runBlocking {
			val response = homeRepository.getLocalCurrentWeather(location = SampleResponses.testLocation)
			Truth.assertThat(response is WeatherResult.Success)
		}
	}
	
	@Test
	fun `test inserting current weather into database`() {
		runBlocking {
			homeRepository.insertCurrentLocationWeather(SampleRequest.sampleCurrentWeather)
			
			val location = homeRepository.getAllForeCasts().first().toList()[0]
			
			MatcherAssert.assertThat(location.locationName, `is`(SampleRequest.sampleCurrentWeather.name))
		}
	}
}

