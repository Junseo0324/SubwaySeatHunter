package com.devhjs.subwayseathunter.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.devhjs.subwayseathunter.data.local.entity.StationEntity

@Dao
interface StationDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertStations(stations: List<StationEntity>)

    @Query("SELECT * FROM stations WHERE stationName = :name")
    suspend fun getStationsByName(name: String): List<StationEntity>

    @Query("SELECT * FROM stations WHERE stationName LIKE '%' || :query || '%'")
    suspend fun searchStations(query: String): List<StationEntity>
    
    @Query("SELECT * FROM stations")
    suspend fun getAllStations(): List<StationEntity>
}
