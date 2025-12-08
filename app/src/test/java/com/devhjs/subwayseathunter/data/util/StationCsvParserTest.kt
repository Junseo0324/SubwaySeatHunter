package com.devhjs.subwayseathunter.data.util

import com.google.common.truth.Truth.assertThat
import org.junit.Test
import java.io.ByteArrayInputStream

class StationCsvParserTest {

    private val parser = StationCsvParser()

    @Test
    fun `valid csv data parses correctly`() {
        // Given
        val csvData = """
            station_name,line_name,station_code,lat,lng,transfer_info
            Gangnam,Line 2,216,37.4979,127.0276,
            Seoul Station,Line 1,133,37.5559,126.9723,Line 4
        """.trimIndent()
        val inputStream = ByteArrayInputStream(csvData.toByteArray())

        // When
        val result = parser.parse(inputStream)

        // Then
        assertThat(result).hasSize(2)
        
        val firstStation = result[0]
        assertThat(firstStation.stationName).isEqualTo("Gangnam")
        assertThat(firstStation.lineName).isEqualTo("Line 2")
        assertThat(firstStation.stationCode).isEqualTo("216")
        assertThat(firstStation.lat).isEqualTo(37.4979)
        assertThat(firstStation.lng).isEqualTo(127.0276)
        assertThat(firstStation.transferInfo).isNull()

        val secondStation = result[1]
        assertThat(secondStation.stationName).isEqualTo("Seoul Station")
        assertThat(secondStation.transferInfo).isEqualTo("Line 4")
    }

    @Test
    fun `csv with invalid format skips lines or handle errors gracefully`() {
         // Given (Missing columns)
        val csvData = """
            station_name,line_name,station_code,lat,lng,transfer_info
            Invalid Station,Line X
        """.trimIndent()
        val inputStream = ByteArrayInputStream(csvData.toByteArray())

        // When
        val result = parser.parse(inputStream)

        // Then (Assuming we want to skip invalid lines or list is empty)
        assertThat(result).isEmpty()
    }
}
