package com.devhjs.subwayseathunter.di

import com.devhjs.subwayseathunter.data.repository.RealtimeRepositoryImpl
import com.devhjs.subwayseathunter.data.repository.StationRepositoryImpl
import com.devhjs.subwayseathunter.domain.repository.RealtimeRepository
import com.devhjs.subwayseathunter.domain.repository.StationRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindStationRepository(
        stationRepositoryImpl: StationRepositoryImpl
    ): StationRepository

    @Binds
    @Singleton
    abstract fun bindRealtimeRepository(
        realtimeRepositoryImpl: RealtimeRepositoryImpl
    ): RealtimeRepository
}
