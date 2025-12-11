package com.devhjs.subwayseathunter.data.service

import com.devhjs.subwayseathunter.data.dto.SubwayArrivalResponse
import com.devhjs.subwayseathunter.di.NetworkModule
import com.google.common.truth.Truth.assertThat
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class SubwayApiServiceTest {

    private lateinit var mockWebServer: MockWebServer
    private lateinit var apiService: SubwayApiService

    @Before
    fun setup() {
        mockWebServer = MockWebServer()
        mockWebServer.start()

        val moshi = Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl(mockWebServer.url("/"))
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()

        apiService = retrofit.create(SubwayApiService::class.java)
    }

    @After
    fun teardown() {
        mockWebServer.shutdown()
    }

    @Test
    fun `getRealtimeStationArrivals sends correct request and parses response`() = runBlocking {
        // Given
        val mockResponse = """
            {
              "errorMessage": {
                "status": 200,
                "code": "INFO-000",
                "message": "Success",
                "link": "",
                "developerMessage": "",
                "total": 1
              },
              "realtimeArrivalList": [
                {
                  "subwayId": "1002",
                  "updnLine": "Inner",
                  "trainLineNm": "Dest",
                  "statnNm": "Gangnam",
                  "btrainNo": "1234",
                  "arvlMsg2": "2 min",
                  "arvlMsg3": "Gangnam",
                  "arvlCd": "99",
                  "barvlDt": "120"
                }
              ]
            }
        """.trimIndent()

        mockWebServer.enqueue(MockResponse().setBody(mockResponse))

        // When
        val response = apiService.getRealtimeStationArrivals("TEST_KEY", 1, 5, "Gangnam")

        // Then
        // 1. Verify Request
        val request = mockWebServer.takeRequest()
        assertThat(request.path).isEqualTo("/api/subway/TEST_KEY/json/realtimeStationArrival/1/5/Gangnam")
        assertThat(request.method).isEqualTo("GET")

        // 2. Verify Response
        assertThat(response.isSuccessful).isTrue()
        val body = response.body()
        assertThat(body).isNotNull()
        assertThat(body?.errorMessage?.status).isEqualTo(200)
        assertThat(body?.realtimeArrivalList).hasSize(1)
        assertThat(body?.realtimeArrivalList!![0].statnNm).isEqualTo("Gangnam")
    }
}
