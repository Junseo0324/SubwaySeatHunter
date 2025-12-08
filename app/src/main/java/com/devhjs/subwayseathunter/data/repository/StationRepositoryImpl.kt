package com.devhjs.subwayseathunter.data.repository

import com.devhjs.subwayseathunter.data.local.dao.StationDao
import com.devhjs.subwayseathunter.data.local.entity.StationEntity
import com.devhjs.subwayseathunter.data.source.AssetDataSource
import com.devhjs.subwayseathunter.data.util.CsvParser
import com.devhjs.subwayseathunter.data.dto.StationCsvDto
import com.devhjs.subwayseathunter.domain.repository.StationRepository
import com.devhjs.subwayseathunter.domain.model.Station
import java.io.InputStream
import javax.inject.Inject

class StationRepositoryImpl @Inject constructor(
    private val stationDao: StationDao,
    private val stationCsvParser: CsvParser<StationCsvDto>,
    private val assetDataSource: AssetDataSource
) : StationRepository {

    override suspend fun loadInitialData() {
        val existingData = stationDao.getAllStations()
        if (existingData.isEmpty()) {
            val inputStream = assetDataSource.openStationAsset()
            val stationDtos = stationCsvParser.parse(inputStream)
            
            val stationEntities = stationDtos.map { dto ->
                StationEntity(
                    stationName = dto.stationName,
                    lineName = dto.lineName,
                    stationCode = dto.stationCode,
                    lat = dto.lat,
                    lng = dto.lng,
                    transferInfo = dto.transferInfo
                )
            }
            stationDao.insertStations(stationEntities)
        }
    }

    override suspend fun getStationsByName(name: String): List<Station> {
        return stationDao.getStationsByName(name).map { entity ->
            Station(
                stationName = entity.stationName,
                lineName = entity.lineName,
                stationCode = entity.stationCode,
                lat = entity.lat,
                lng = entity.lng,
                transferInfo = entity.transferInfo
            )
        }
    }
}
