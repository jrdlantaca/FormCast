package com.example.formcast.api

import com.example.formcast.vo.CurrentWeather
import com.example.formcast.vo.Weather
import retrofit2.Response

import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherService {

    @GET("onecall")
    suspend fun getWeather(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
        @Query("exclude") exclude: String?,
        @Query("appid") apiId: String,
        @Query("units") units : String?
    ): Response<Weather>

}