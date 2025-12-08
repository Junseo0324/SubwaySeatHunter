package com.devhjs.subwayseathunter.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.devhjs.subwayseathunter.data.local.entity.CongestionEntity

@Dao
interface CongestionDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCongestionStats(stats: List<CongestionEntity>)

    @Query("SELECT * FROM congestion_stats WHERE stationCode = :stationCode AND weekDay = :weekDay")
    suspend fun getCongestion(stationCode: String, weekDay: String): List<CongestionEntity>
}
