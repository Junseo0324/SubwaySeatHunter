package com.devhjs.subwayseathunter.data.util

import com.devhjs.subwayseathunter.data.dto.StationCsvDto
import com.devhjs.subwayseathunter.data.dto.CongestionCsvDto
import java.io.InputStream

class StationCsvParser : CsvParser<StationCsvDto> {
    override fun parse(inputStream: InputStream): List<StationCsvDto> {
        val reader = inputStream.bufferedReader()
        reader.readLine() // Skip header
        return reader.lineSequence()
            .filter { it.isNotBlank() }
            .mapNotNull { line ->
                val columns = line.split(",")
                if (columns.size < 5) return@mapNotNull null // Basic validation

                try {
                    val stationName = columns[0].trim()
                    val lineName = columns[1].trim()
                    val stationCode = columns[2].trim()
                    val lat = columns[3].trim().toDouble()
                    val lng = columns[4].trim().toDouble()
                    val transferInfo = if (columns.size > 5 && columns[5].isNotBlank()) columns[5].trim() else null

                    StationCsvDto(
                        stationName = stationName,
                        lineName = lineName,
                        stationCode = stationCode,
                        lat = lat,
                        lng = lng,
                        transferInfo = transferInfo
                    )
                } catch (e: Exception) {
                    e.printStackTrace()
                    null
                }
            }
            .toList()
    }
}

class CongestionCsvParser : CsvParser<CongestionCsvDto> {
    override fun parse(inputStream: InputStream): List<CongestionCsvDto> {
        // TODO: Implement parsing logic
        return emptyList()
    }
}
