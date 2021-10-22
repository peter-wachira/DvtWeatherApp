package com.zalocoders.dvtweatherapp.di

import android.content.Context
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.zalocoders.dvtweatherapp.R
import com.zalocoders.dvtweatherapp.network.ApiService
import com.zalocoders.dvtweatherapp.utils.loggingInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.Protocol
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
	
	private var BASE_URL = "https://api.openweathermap.org/data/2.5/"
	private const val TIME_OUT = 30
	
	@Provides
	@ApiKey
	@Singleton
	fun provideApiKey(@ApplicationContext context: Context): String =
			context.getString(R.string.api_key)
	
	@Provides
	@Singleton
	fun provideHttpClient(): OkHttpClient {
		val builder = OkHttpClient.Builder()
				.connectTimeout(TIME_OUT.toLong(), TimeUnit.SECONDS)
				.readTimeout(TIME_OUT.toLong(), TimeUnit.SECONDS)
				.writeTimeout(TIME_OUT.toLong(), TimeUnit.SECONDS)
				.protocols(listOf(Protocol.HTTP_2, Protocol.HTTP_1_1))
				.addInterceptor { chain ->
					val request = chain.request()
					val authRequest = request.newBuilder()
							.header("Content-Type", "application/json")
							.build()
					return@addInterceptor chain.proceed(authRequest)
				}
				.addNetworkInterceptor(loggingInterceptor)
		return builder.build()
	}
	
	@Provides
	@Singleton
	fun provideMoshi(): Moshi = Moshi.Builder()
			.add(KotlinJsonAdapterFactory())
			.build()
	
	@Provides
	@Singleton
	fun provideRetrofit(okHttpClient: OkHttpClient, moshi: Moshi): Retrofit = Retrofit.Builder()
			.baseUrl(BASE_URL)
			.client(okHttpClient)
			.addConverterFactory(MoshiConverterFactory.create(moshi))
			.build()
	
	@Provides
	fun provideApiService(retrofit: Retrofit): ApiService = retrofit.create(ApiService::class.java)
	
	@Qualifier
	@Retention(AnnotationRetention.BINARY)
	annotation class ApiKey
	
}

