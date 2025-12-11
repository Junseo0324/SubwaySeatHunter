package com.devhjs.subwayseathunter.data.repository

import com.devhjs.subwayseathunter.BuildConfig
import com.devhjs.subwayseathunter.data.service.SubwayApiService
import com.devhjs.subwayseathunter.domain.model.RealtimeArrival
import com.devhjs.subwayseathunter.domain.repository.RealtimeRepository
import javax.inject.Inject

class RealtimeRepositoryImpl @Inject constructor(
    private val apiService: SubwayApiService
) : RealtimeRepository {

    override suspend fun getRealtimeArrivals(stationName: String): Result<List<RealtimeArrival>> {
        return try {
            val response = apiService.getRealtimeStationArrivals(
                apiKey = BuildConfig.REALTIME_API_KEY,
                startIndex = 1, // 서울시 API는 보통 1부터 시작
                endIndex = 10,  // 넉넉하게 10개 조회
                stationName = stationName
            )
            if (response.isSuccessful) {
                val body = response.body()
                val arrivalList = body?.realtimeArrivalList?.map { dto ->
                    RealtimeArrival(
                        subwayId = dto.subwayId,
                        stationName = dto.statnNm,
                        trainLineNm = dto.trainLineNm,
                        arrivalMessage = dto.arvlMsg2,
                        secondMessage = dto.arvlMsg3,
                        arrivalCode = dto.arvlCd,
                        arrivalTimeSeconds = dto.barvlDt.toIntOrNull() ?: 0
                    )
                } ?: emptyList()
                Result.success(arrivalList)
            } else {
                Result.failure(Exception("API Error: ${response.code()} ${response.message()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
