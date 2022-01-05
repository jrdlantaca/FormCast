package com.example.formcast.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.formcast.vo.Weather
import kotlinx.coroutines.flow.Flow

@Dao
interface WeatherDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertWeather(weather : List<Weather>)

    @Query("SELECT * FROM weather_table")
    fun getWeather() : Flow<List<Weather>>

    @Query("DELETE FROM weather_table")
    fun deleteWeather()

}