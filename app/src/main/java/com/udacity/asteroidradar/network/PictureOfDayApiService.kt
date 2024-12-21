package com.udacity.asteroidradar.network

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.udacity.asteroidradar.BuildConfig
import com.udacity.asteroidradar.Constants
import com.udacity.asteroidradar.model.PictureOfDay
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

private const val NASA_API_KEY = BuildConfig.API_KEY_NASA

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(Constants.BASE_URL)
    .build()

interface PictureOfDayApiService {

    @GET("planetary/apod")
    suspend fun getPictureOfDay(
        @Query("api_key") key: String = NASA_API_KEY
    ): PictureOfDay

}

object PictureOfDayApi {
    val retrofitService : PictureOfDayApiService by lazy { retrofit.create(PictureOfDayApiService::class.java) }
}