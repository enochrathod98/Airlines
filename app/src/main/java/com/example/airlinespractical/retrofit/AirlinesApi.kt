package com.example.airlinespractical.retrofit

import com.example.airlinespractical.models.AirlinesList
import retrofit2.http.GET
import retrofit2.http.Query

interface AirlinesApi {

    @GET("qfonapp-passenger")
    suspend fun getAirlines(@Query("page") page: Int,@Query("size") size: Int) : AirlinesList
}