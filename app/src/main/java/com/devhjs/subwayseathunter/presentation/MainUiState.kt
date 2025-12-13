package com.devhjs.subwayseathunter.presentation

import com.devhjs.subwayseathunter.domain.model.RealtimeArrival
import com.devhjs.subwayseathunter.domain.model.Station

data class MainUiState(
    val isLoading: Boolean = false,
    val searchResults: List<Station> = emptyList(), // 검색된 역 리스트
    val selectedStation: Station? = null, // 선택된 역
    val realtimeArrivals: List<RealtimeArrival> = emptyList(), // 실시간 도착 정보
    val errorMessage: String? = null // 에러 메시지
)
