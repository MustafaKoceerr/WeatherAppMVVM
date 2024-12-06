package com.kocerlabs.weatherapp.ui.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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




}