package com.zalocoders.dvtweatherapp.network.utils

data class ErrorResponse(override val message: String, val statusCode: Int?, val body: String) : Exception(message)