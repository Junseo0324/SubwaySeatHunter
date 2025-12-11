package com.devhjs.subwayseathunter.domain.repository

import com.devhjs.subwayseathunter.domain.model.RealtimeArrival

interface RealtimeRepository {
    suspend fun getRealtimeArrivals(stationName: String): Result<List<RealtimeArrival>>
}
