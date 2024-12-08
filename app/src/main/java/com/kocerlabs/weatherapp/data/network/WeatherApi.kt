package com.kocerlabs.weatherapp.data.network

import com.kocerlabs.weatherapp.data.network.model.CityItem
import com.kocerlabs.weatherapp.data.network.model.WeatherForecastItem
import com.kocerlabs.weatherapp.data.network.model.WeatherItem
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {


    @GET("data/2.5/weather/")
    suspend fun getCurrentWeather(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
        @Query("units") units: String,
        @Query("appid") apiKey: String,
    ): Response<WeatherItem>


    @GET("data/2.5/forecast/")
    suspend fun getForecastWeather(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
        @Query("appid") apiKey: String,
    ): Response<WeatherForecastItem>


    @GET("geo/1.0/direct")
    suspend fun getDirectGeocoding(
        @Query("q") cityName: String,
        @Query("limit") limit: Int,
        @Query("appid") apiKey: String,
    ): Response<List<CityItem>>

}
