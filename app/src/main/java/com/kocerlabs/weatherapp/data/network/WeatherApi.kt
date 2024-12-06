package com.kocerlabs.weatherapp.data.network

import com.kocerlabs.weatherapp.data.network.model.WeatherItem
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {
//    val key = "dd9ed7f2e3c8964ec1fd8e458fa12991"

    @GET("data/2.5/weather")
    suspend fun getCurrentWeather(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
        @Query("units") units: String,
        @Query("appid") ApiKey: String,
    ): Response<WeatherItem>
}
