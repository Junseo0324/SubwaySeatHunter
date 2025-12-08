package com.devhjs.subwayseathunter.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "congestion_stats")
data class CongestionEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val stationCode: String,
    val lineName: String,
    val weekDay: String, // e.g. "WEEKDAY", "SATURDAY", "SUNDAY"
    val timeSlot: String, // e.g. "08:00"
    val congestion: Int // percentage
)
