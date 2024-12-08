package com.kocerlabs.weatherapp.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kocerlabs.weatherapp.data.network.model.CityItem
import com.kocerlabs.weatherapp.data.network.model.WeatherForecastItem
import com.kocerlabs.weatherapp.data.network.model.WeatherItem
import com.kocerlabs.weatherapp.data.repository.WeatherRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: WeatherRepository
) : ViewModel() {
    private val TAG = "HomeViewModel"


    private val _weather: MutableLiveData<WeatherItem> = MutableLiveData()
    val weather: LiveData<WeatherItem>
        get() = _weather

    private val _forecast: MutableLiveData<WeatherForecastItem> = MutableLiveData()
    val forecast: LiveData<WeatherForecastItem>
        get() = _forecast


    fun getCurrentWeather(
        lat: Double,
        lon: Double,
        units: String,
        ApiKey: String,
    ) {
        viewModelScope.launch {
            val result = repository.getCurrentWeather(lat, lon, units, ApiKey)

            if (result.body() != null) _weather.value =
                result.body() else throw IllegalArgumentException("Cevap null geldi")
        }
    }


    fun getForecastWeather(
        lat: Double,
        lon: Double,
        apiKey: String,
    ) {
        viewModelScope.launch {
            val result = repository.getForecastWeather(lat, lon, apiKey)

            if (result.body() != null) _forecast.value =
                result.body() else throw IllegalArgumentException("Cevap null geldi")
        }
    }



}