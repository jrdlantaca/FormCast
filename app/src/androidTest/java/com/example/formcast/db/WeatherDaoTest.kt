package com.example.formcast.db

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.asLiveData
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.example.formcast.getOrAwaitValue
import com.example.formcast.vo.*
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
@SmallTest
class WeatherDaoTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var database: WeatherDatabase
    private lateinit var dao: WeatherDao

    @Before
    fun setup() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            WeatherDatabase::class.java
        ).allowMainThreadQueries().build()
        dao = database.weatherDao()
    }


    @After
    fun teardown() {
        database.close()
    }

    @Test
    fun insertWeatherList() = runBlockingTest {
        val weatherDesc = WeatherDesc(0, "main", "cloudy", "clouds.png")
        val currentWeatherItem = CurrentWeather(15, 17.0, listOf(weatherDesc))
        val hourlyWeatherItem = HourlyWeather(14, 18.0, 0.0, listOf(weatherDesc))
        val dailyTemp = DailyTemp(14.0, 20.0)
        val dailyWeatherItem = DailyWeather(16, dailyTemp, 0.5, listOf(weatherDesc))
        val weather = Weather(
            1,
            15.0,
            254.0,
            currentWeatherItem,
            listOf(hourlyWeatherItem),
            listOf(dailyWeatherItem)
        )
        val weatherList = listOf(weather)

        dao.insertWeather(weatherList)

        val allWeatherItems = dao.getWeather().asLiveData().getOrAwaitValue()

        assertThat(allWeatherItems).contains(weather)
    }

    @Test
    fun deleteWeatherList() = runBlockingTest {
        val weatherDesc = WeatherDesc(0, "main", "cloudy", "clouds.png")
        val currentWeatherItem = CurrentWeather(15, 17.0, listOf(weatherDesc))
        val hourlyWeatherItem = HourlyWeather(14, 18.0, 0.0, listOf(weatherDesc))
        val dailyTemp = DailyTemp(14.0, 20.0)
        val dailyWeatherItem = DailyWeather(16, dailyTemp, 0.5, listOf(weatherDesc))
        val weather = Weather(
            1,
            15.0,
            254.0,
            currentWeatherItem,
            listOf(hourlyWeatherItem),
            listOf(dailyWeatherItem)
        )
        val weatherList = listOf(weather)
        dao.insertWeather(weatherList)
        dao.deleteWeather()
        val allWeatherItems = dao.getWeather().asLiveData().getOrAwaitValue()

        assertThat(allWeatherItems).doesNotContain(weather)
    }
}