package com.kocerlabs.weatherapp.ui.addcity

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kocerlabs.weatherapp.data.network.model.CityItem
import com.kocerlabs.weatherapp.data.repository.CityRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddCityViewModel @Inject constructor(
    private val repository: CityRepository
) : ViewModel() {

    private val TAG = "AddCityViewModel"
    private val _geo: MutableLiveData<List<CityItem>> = MutableLiveData()
    val geo: LiveData<List<CityItem>>
        get() = _geo


    fun getDirectGeo(
        cityName: String,
        limit: Int,
        apiKey: String,
    ) {
        viewModelScope.launch {
            val result = repository.getDirectGeo(cityName, limit, apiKey)
            result.body()?.forEach {
                Log.d(TAG, "Cevap: $it")
            }
            if (result.body() != null) _geo.value =
                result.body()
        }
    }
}