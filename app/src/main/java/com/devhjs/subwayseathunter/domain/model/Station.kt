package com.devhjs.subwayseathunter.domain.model

data class Station(
    val stationName: String,
    val lineName: String,
    val stationCode: String,
    val lat: Double,
    val lng: Double,
    val transferInfo: String? = null
)
