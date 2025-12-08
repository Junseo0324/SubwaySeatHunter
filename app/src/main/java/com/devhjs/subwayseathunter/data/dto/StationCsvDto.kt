package com.devhjs.subwayseathunter.data.dto

data class StationCsvDto(
    val stationName: String, // 역명
    val lineName: String,    // 호선 (e.g., "2호선")
    val stationCode: String, // 역 코드 (외부 코드)
    val lat: Double,         // 위도
    val lng: Double,         // 경도
    val transferInfo: String? = null // 환승 정보 (Optional)
)
