package com.devhjs.subwayseathunter.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "stations")
data class StationEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val stationName: String,
    val lineName: String,
    val stationCode: String,
    val lat: Double,
    val lng: Double,
    val transferInfo: String? // Nullable
)
