package com.musicdemo.network

import retrofit2.http.GET

interface ApiService {

    @GET
    fun getWeatherByLocation() : String
}