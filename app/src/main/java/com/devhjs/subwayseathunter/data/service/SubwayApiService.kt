package com.devhjs.subwayseathunter.data.service

import com.devhjs.subwayseathunter.data.dto.SubwayArrivalResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface SubwayApiService {
    @GET("api/subway/{apiKey}/json/realtimeStationArrival/0/5/{stationName}")
    suspend fun getRealtimeStationArrivals(
        @Path("apiKey") apiKey: String,
        @Path("stationName") stationName: String
    ): Response<SubwayArrivalResponse>
}
