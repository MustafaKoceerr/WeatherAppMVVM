package com.kocerlabs.weatherapp.data.repository

import com.kocerlabs.weatherapp.data.network.WeatherApi
import javax.inject.Inject

class WeatherRepository @Inject constructor(
    private val api: WeatherApi
) : BaseRepository() {


    suspend fun getCurrentWeather(
        lat: Double,
        lon: Double,
        units: String,
        ApiKey: String,
    ) = safeApiCall { api.getCurrentWeather(lat, lon, units, ApiKey) }
}