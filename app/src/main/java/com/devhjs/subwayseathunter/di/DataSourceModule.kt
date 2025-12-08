package com.devhjs.subwayseathunter.di

import com.devhjs.subwayseathunter.data.source.AssetDataSource
import com.devhjs.subwayseathunter.data.source.AssetDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class DataSourceModule {

    @Binds
    @Singleton
    abstract fun bindAssetDataSource(
        assetDataSourceImpl: AssetDataSourceImpl
    ): AssetDataSource
}
