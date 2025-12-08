package com.devhjs.subwayseathunter.data.dto

data class CongestionCsvDto(
    val stationCode: String,
    val lineName: String,
    val weekDay: String, // 평일, 토요일, 일요일
    val timeSlot: String, // 05:30, 06:00 ...
    val congestion: Int // 혼잡도 %
)
