package com.musicdemo.network

import com.musicdemo.model.apientities.Weather
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("v1/current.json?")
    suspend fun getWeatherByLocation(@Query("key") key: String, @Query("q") q : String, @Query("aqi") qi : String): Weather

}