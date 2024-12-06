package com.kocerlabs.weatherapp.di

import com.kocerlabs.weatherapp.data.network.RemoteDataSource
import com.kocerlabs.weatherapp.data.network.WeatherApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    fun provideWeatherApi(remoteDataSource: RemoteDataSource): WeatherApi {
        return remoteDataSource.buildApi(WeatherApi::class.java)
    }

}