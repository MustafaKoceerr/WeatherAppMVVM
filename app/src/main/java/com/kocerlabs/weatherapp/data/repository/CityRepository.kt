package com.kocerlabs.weatherapp.data.repository

import com.kocerlabs.weatherapp.data.network.WeatherApi
import javax.inject.Inject

class CityRepository @Inject constructor(
    private val api: WeatherApi,

    ) : BaseRepository() {
    suspend fun getDirectGeo(
        cityName: String,
        limit: Int,
        ApiKey: String,
    ) = safeApiCall { api.getDirectGeocoding(cityName, limit, ApiKey) }

}