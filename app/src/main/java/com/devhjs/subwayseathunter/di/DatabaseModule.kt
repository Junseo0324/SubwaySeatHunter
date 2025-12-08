package com.devhjs.subwayseathunter.di

import android.content.Context
import androidx.room.Room
import com.devhjs.subwayseathunter.data.local.AppDatabase
import com.devhjs.subwayseathunter.data.local.dao.CongestionDao
import com.devhjs.subwayseathunter.data.local.dao.StationDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "subway_seat_hunter.db"
        ).build()
    }

    @Provides
    fun provideStationDao(database: AppDatabase): StationDao {
        return database.stationDao()
    }

    @Provides
    fun provideCongestionDao(database: AppDatabase): CongestionDao {
        return database.congestionDao()
    }
}
