package com.devhjs.subwayseathunter.data.util

import com.devhjs.subwayseathunter.data.dto.StationCsvDto
import com.devhjs.subwayseathunter.data.dto.CongestionCsvDto
import java.io.InputStream

class StationCsvParser : CsvParser<StationCsvDto> {
    override fun parse(inputStream: InputStream): List<StationCsvDto> {
        // TODO: Implement parsing logic
        return emptyList()
    }
}

class CongestionCsvParser : CsvParser<CongestionCsvDto> {
    override fun parse(inputStream: InputStream): List<CongestionCsvDto> {
        // TODO: Implement parsing logic
        return emptyList()
    }
}
