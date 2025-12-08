package com.devhjs.subwayseathunter.di

import android.content.Context
import com.devhjs.subwayseathunter.data.dto.StationCsvDto
import com.devhjs.subwayseathunter.data.util.CsvParser
import com.devhjs.subwayseathunter.data.util.StationCsvParser
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import java.io.InputStream
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CsvParserModule {

    @Provides
    @Singleton
    fun provideStationCsvParser(): CsvParser<StationCsvDto> {
        return StationCsvParser()
    }


}
