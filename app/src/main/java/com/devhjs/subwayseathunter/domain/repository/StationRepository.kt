package com.devhjs.subwayseathunter.domain.repository

import com.devhjs.subwayseathunter.domain.model.Station

interface StationRepository {
    suspend fun getStationsByName(name: String): List<Station>
    suspend fun loadInitialData() // CSV에서 초기 데이터 로드
}
