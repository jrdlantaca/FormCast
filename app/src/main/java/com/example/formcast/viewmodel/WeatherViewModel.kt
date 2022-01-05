package com.example.formcast.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.*
import com.example.formcast.repository.WeatherRepository
import com.example.formcast.utils.Resource
import com.example.formcast.vo.Weather
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val repository: WeatherRepository
) : ViewModel() {
    val lat = mutableStateOf(0.0)
    val lon = mutableStateOf(0.0)
    val loading = mutableStateOf(false)


    private val _weather = MutableLiveData<Resource<List<Weather>>>()
    var weather: LiveData<Resource<List<Weather>>> = _weather

    fun updateWeather() {
        loading.value = true
        viewModelScope.launch {
            weather = repository.getWeather(lat = lat.value, lon = lon.value, loading).asLiveData()
        }

    }
}