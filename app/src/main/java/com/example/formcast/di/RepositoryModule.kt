package com.example.formcast.di

import android.app.Application
import androidx.room.Room
import com.example.formcast.db.WeatherDao
import com.example.formcast.db.WeatherDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {
    @Provides
    @Singleton
    fun provideDb(app: Application): WeatherDatabase {
        return Room
            .databaseBuilder(app, WeatherDatabase::class.java, "weather.db")
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @Singleton
    fun provideWeatherDao(db: WeatherDatabase): WeatherDao {
        return db.weatherDao()
    }
}
