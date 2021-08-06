package com.musicdemo.network

import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("v1/current.json?")
    fun getWeatherByLocation(@Query("key") key: String, @Query("q") q : String, @Query("qi") qi : String): String
}