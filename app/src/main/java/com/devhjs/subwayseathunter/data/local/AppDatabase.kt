package com.devhjs.subwayseathunter.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.devhjs.subwayseathunter.data.local.dao.CongestionDao
import com.devhjs.subwayseathunter.data.local.dao.StationDao
import com.devhjs.subwayseathunter.data.local.entity.CongestionEntity
import com.devhjs.subwayseathunter.data.local.entity.StationEntity

@Database(entities = [StationEntity::class, CongestionEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun stationDao(): StationDao
    abstract fun congestionDao(): CongestionDao
}
