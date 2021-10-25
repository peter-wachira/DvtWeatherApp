package com.zalocoders.dvtweatherapp.request_dispatcher

import com.google.common.io.Resources
import java.io.File
import java.net.HttpURLConnection
import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.RecordedRequest

class RequestDispatcher : Dispatcher() {
	override fun dispatch(request: RecordedRequest): MockResponse {
		
		return when {
			request.path!!.contains("weather", true) -> {
				MockResponse()
						.setResponseCode(HttpURLConnection.HTTP_OK)
						.setBody(getJson("json/weather_response.json"))
			}
			request.path!!.contains("onecall", true) -> {
				MockResponse()
						.setResponseCode(HttpURLConnection.HTTP_OK)
						.setBody(getJson("json/forecast.json"))
			}
			else -> {
				throw IllegalArgumentException("Unknown path ${request.path}")
			}
		}
		
	}
	
	fun getJson(path: String): String {
		
		val uri = Resources.getResource(path)
		val file = File(uri.path)
		
		return String(file.readBytes())
	}
}