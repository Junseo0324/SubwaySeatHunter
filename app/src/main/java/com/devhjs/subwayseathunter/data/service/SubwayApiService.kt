package com.devhjs.subwayseathunter.data.service

import com.devhjs.subwayseathunter.data.dto.SubwayArrivalResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface SubwayApiService {
    @GET("api/subway/{apiKey}/json/realtimeStationArrival/{startIndex}/{endIndex}/{stationName}")
    suspend fun getRealtimeStationArrivals(
        @Path("apiKey") apiKey: String,
        @Path("startIndex") startIndex: Int,
        @Path("endIndex") endIndex: Int,
        @Path("stationName") stationName: String
    ): Response<SubwayArrivalResponse>
}
