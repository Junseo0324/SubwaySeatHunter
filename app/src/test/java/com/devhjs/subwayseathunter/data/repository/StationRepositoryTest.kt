package com.devhjs.subwayseathunter.data.repository

import com.devhjs.subwayseathunter.data.dto.StationCsvDto
import com.devhjs.subwayseathunter.data.local.dao.StationDao
import com.devhjs.subwayseathunter.data.local.entity.StationEntity
import com.devhjs.subwayseathunter.data.source.AssetDataSource
import com.devhjs.subwayseathunter.data.util.CsvParser
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Test
import java.io.InputStream

class StationRepositoryTest {

    private val stationDao: StationDao = mockk(relaxed = true)
    private val csvParser: CsvParser<StationCsvDto> = mockk()
    private val assetDataSource: AssetDataSource = mockk()
    private val inputStream: InputStream = mockk(relaxed = true)
    
    private val repository = StationRepositoryImpl(
        stationDao = stationDao,
        stationCsvParser = csvParser,
        assetDataSource = assetDataSource
    )

    @Test
    fun `loadInitialData parses csv and inserts into db`() = runBlocking {
        // Given
        val csvDtos = listOf(
            StationCsvDto("Gangnam", "Line 2", "216", 37.0, 127.0, null)
        )
        every { assetDataSource.openStationAsset() } returns inputStream
        every { csvParser.parse(any()) } returns csvDtos
        coEvery { stationDao.getAllStations() } returns emptyList() // Initially empty

        // When
        repository.loadInitialData()

        // Then
        coVerify { stationDao.insertStations(any()) }
    }

    @Test
    fun `getStationsByName returns mapped domain models`() = runBlocking {
        // Given
        val stationEntities = listOf(
            StationEntity(1, "Gangnam", "Line 2", "216", 37.0, 127.0, null)
        )
        coEvery { stationDao.getStationsByName("Gangnam") } returns stationEntities

        // When
        val result = repository.getStationsByName("Gangnam")

        // Then
        assertThat(result).hasSize(1)
        assertThat(result[0].stationName).isEqualTo("Gangnam")
        assertThat(result[0].lineName).isEqualTo("Line 2")
    }
}
