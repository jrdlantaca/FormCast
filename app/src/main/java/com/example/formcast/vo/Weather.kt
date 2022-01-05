package com.example.formcast.vo

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@Entity(tableName = "weather_table")
@TypeConverters(ObjectConverter::class)
@JsonClass(generateAdapter = true)
data class Weather(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    @Json(name = "lat")
    val lat: Double? = null,
    @Json(name = "lon")
    val lon: Double? = null,
    @Json(name = "current")
    val current: CurrentWeather? = null,
    @Json(name = "hourly")
    val hourly: List<HourlyWeather?>? = null,
    @Json(name = "daily")
    val daily: List<DailyWeather?>? = null,
)

@JsonClass(generateAdapter = true)
data class CurrentWeather(
    @Json(name = "dt")
    val dt: Long? = null,
    @Json(name = "temp")
    val temp: Double? = null,
    @Json(name = "weather")
    val weatherDesc: List<WeatherDesc?>? = null,
)

@JsonClass(generateAdapter = true)
data class HourlyWeather(
    @Json(name = "dt")
    val dt: Long? = null,
    @Json(name = "temp")
    val temp: Double? = null,
    @Json(name = "pop")
    val pop : Double? = null,
    @Json(name = "weather")
    val hourlyDesc: List<WeatherDesc?>? = null
)

@JsonClass(generateAdapter = true)
data class DailyWeather(
    @Json(name = "dt")
    val dt: Long? = null,
    @Json(name = "temp")
    val temp: DailyTemp? = null,
    @Json(name = "pop")
    val pop : Double? = null,
    @Json(name = "weather")
    val dailyDesc: List<WeatherDesc?>? = null
)

@JsonClass(generateAdapter = true)
data class DailyTemp(
    @Json(name = "day")
    val day: Double? = null,
    @Json(name = "night")
    val night: Double? = null
)

@JsonClass(generateAdapter = true)
data class WeatherDesc(
    @Json(name = "id")
    val id : Int? = null,
    @Json(name = "main")
    val main: String? = null,
    @Json(name = "description")
    val description: String? = null,
    @Json(name = "icon")
    val icon : String? = null
)


class ObjectConverter {
    @TypeConverter
    fun currentToString(current: CurrentWeather): String {
        val gson = Gson()
        val type = object : TypeToken<CurrentWeather>() {}.type
        return gson.toJson(current, type).toString()
    }

    @TypeConverter
    fun stringToCurrent(json: String): CurrentWeather {
        val gson = Gson()
        val type = object : TypeToken<CurrentWeather>() {}.type
        return gson.fromJson(json, type)
    }

    @TypeConverter
    fun hourlyToString(hourly: List<HourlyWeather>): String {
        val gson = Gson()
        val type = object : TypeToken<List<HourlyWeather>>() {}.type
        return gson.toJson(hourly, type).toString()
    }

    @TypeConverter
    fun stringToHourly(json: String): List<HourlyWeather> {
        val gson = Gson()
        val type = object : TypeToken<List<HourlyWeather>>() {}.type
        return gson.fromJson(json, type)
    }

    @TypeConverter
    fun dailyToString(daily: List<DailyWeather>): String {
        val gson = Gson()
        val type = object : TypeToken<List<DailyWeather>>() {}.type
        return gson.toJson(daily, type).toString()
    }

    @TypeConverter
    fun stringToDaily(json: String): List<DailyWeather> {
        val gson = Gson()
        val type = object : TypeToken<List<DailyWeather>>() {}.type
        return gson.fromJson(json, type)
    }
}

