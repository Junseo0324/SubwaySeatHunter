package com.devhjs.subwayseathunter.data.repository

import com.devhjs.subwayseathunter.data.dto.RealtimeArrivalDto
import com.devhjs.subwayseathunter.data.dto.SubwayArrivalResponse
import com.devhjs.subwayseathunter.data.service.SubwayApiService
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Test
import retrofit2.Response

class RealtimeRepositoryTest {

    private val apiService: SubwayApiService = mockk()
    private val repository = RealtimeRepositoryImpl(apiService)

    @Test
    fun `getRealtimeArrivals returns mapped domain models on success`() = runBlocking {
        // Given
        val arrivalDto = RealtimeArrivalDto(
            subwayId = "1002",
            updnLine = "Inner",
            trainLineNm = "Gangnam",
            subwayHeading = null,
            statnNm = "Gangnam",
            btrainNo = "1001",
            arvlMsg2 = "Arriving",
            arvlMsg3 = "Gangnam",
            arvlCd = "99",
            barvlDt = "60"
        )
        val apiResponse = SubwayArrivalResponse(
            errorMessage = null,
            realtimeArrivalList = listOf(arrivalDto)
        )
        coEvery { apiService.getRealtimeStationArrivals(any(), "Gangnam") } returns Response.success(apiResponse)

        // When
        val result = repository.getRealtimeArrivals("Gangnam")

        // Then
        assertThat(result.isSuccess).isTrue()
        val arrivals = result.getOrNull()
        assertThat(arrivals).hasSize(1)
        assertThat(arrivals!![0].stationName).isEqualTo("Gangnam")
        assertThat(arrivals[0].arrivalTimeSeconds).isEqualTo(60)
    }

    @Test
    fun `getRealtimeArrivals returns failure on api error`() = runBlocking {
        // Given
        coEvery { apiService.getRealtimeStationArrivals(any(), "Gangnam") } returns Response.error(404, "Not Found".toResponseBody())

        // When
        val result = repository.getRealtimeArrivals("Gangnam")

        // Then
        assertThat(result.isFailure).isTrue()
        assertThat(result.exceptionOrNull()).isInstanceOf(Exception::class.java)
    }

    @Test
    fun `getRealtimeArrivals handles exception during api call`() = runBlocking {
        // Given
        coEvery { apiService.getRealtimeStationArrivals(any(), "Gangnam") } throws RuntimeException("Network Error")

        // When
        val result = repository.getRealtimeArrivals("Gangnam")

        // Then
        assertThat(result.isFailure).isTrue()
        assertThat(result.exceptionOrNull()).hasMessageThat().contains("Network Error")
    }
}
