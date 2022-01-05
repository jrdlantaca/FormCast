package com.example.formcast.repository

import androidx.compose.runtime.MutableState
import androidx.room.withTransaction
import com.example.formcast.api.WeatherService
import com.example.formcast.db.WeatherDatabase
import com.example.formcast.utils.API_KEY
import com.example.formcast.utils.networkBoundResource
import com.example.formcast.vo.Weather
import kotlinx.coroutines.delay
import javax.inject.Inject

class WeatherRepository @Inject constructor(
    private val weatherService: WeatherService,
    private val db: WeatherDatabase
) {

    private val weatherDao = db.weatherDao()
    private val weatherList = mutableListOf<Weather>()

    fun getWeather(lat: Double, lon: Double, loading: MutableState<Boolean>) = networkBoundResource(
        query = {
            weatherDao.getWeather()
        },
        fetch = {
            delay(4000)
            val response = weatherService.getWeather(
                lat = lat,
                lon = lon,
                exclude = null,
                apiId = API_KEY,
                units = "metric"
            )
            weatherList.add(response.body()!!)
            loading.value = false
            weatherList
        },
        saveFetchResult = { weather ->
            db.withTransaction {
                weatherDao.deleteWeather()
                weatherDao.insertWeather(weather)
            }
        }
    )
}