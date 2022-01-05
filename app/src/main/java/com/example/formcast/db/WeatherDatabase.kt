package com.example.formcast.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.formcast.vo.CurrentWeather
import com.example.formcast.vo.HourlyWeather
import com.example.formcast.vo.Weather

@Database(
    entities = [Weather::class],
    version = 2,
    exportSchema = false
)
abstract class WeatherDatabase : RoomDatabase() {
    abstract fun weatherDao() : WeatherDao
}