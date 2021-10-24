package com.zalocoders.dvtweatherapp.network.utils

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONException
import org.json.JSONObject
import retrofit2.HttpException
import timber.log.Timber
import java.io.IOException

suspend fun <T> safeApiCall(
		apiCall: suspend () -> T
): WeatherResult<T> = withContext(Dispatchers.IO) {
	try {
		WeatherResult.Success(apiCall.invoke())
	} catch (throwable: Throwable) {
		Timber.e(throwable)
		when (throwable) {
			is IOException -> WeatherResult.InternalError
			is HttpException -> {
				val code = throwable.code()
				
				val errorResponse = convertErrorBody(throwable)
				WeatherResult.ServerError(code, errorResponse)
			}
			else -> {
				WeatherResult.ServerError(null, null)
			}
		}
	}
}

private fun convertErrorBody(throwable: HttpException): ErrorResponse {
	
	val body = throwable.response()?.errorBody()
	val jsonString = body?.string()
	
	val message = try {
		val jsonObject = JSONObject(jsonString)
		jsonObject.getString("message")
	} catch (exception: JSONException) {
		when (throwable.code()) {
			500 -> {
				"Unable to complete request your request, try again later"
			}
			503 -> {
				"Service temporarily unavailable, try again in a few minutes"
			}
			else -> {
				"Unable to complete request your request, try again later"
			}
		}
	}
	
	val errorCode = throwable.response()?.code()
	return ErrorResponse(message, errorCode, jsonString!!)
}
