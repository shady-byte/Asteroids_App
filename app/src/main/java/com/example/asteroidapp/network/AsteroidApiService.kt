package com.example.asteroidapp.network

import com.example.asteroidapp.adapters.Constants
import com.example.asteroidapp.dataBase.NasaTodayImage
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(ScalarsConverterFactory.create())
    .baseUrl(Constants.BASE_URL)
    .build()

private val imageRetrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(Constants.TODAY_IMAGE_URL)
    .build()

interface  AsteroidApiService {
    @GET("neo/rest/v1/feed")
    suspend fun getAsteroidNearEarth(@Query("start_date") startDate: String,@Query("end_date") endDate: String,
                             @Query("api_key") apikey: String): String

    @GET("apod")
    suspend fun getTodayImage(@Query("api_key") apiKey: String) : NasaTodayImage
}

object AsteroidApi {
    val retrofitService: AsteroidApiService by lazy {
        retrofit.create(AsteroidApiService::class.java)
    }
    val imageRetrofitService: AsteroidApiService by lazy {
        imageRetrofit.create(AsteroidApiService::class.java)
    }
}


