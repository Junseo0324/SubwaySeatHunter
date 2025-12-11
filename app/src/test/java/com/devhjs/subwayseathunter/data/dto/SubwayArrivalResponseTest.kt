package com.devhjs.subwayseathunter.data.dto

import com.google.common.truth.Truth.assertThat
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import org.junit.Test

class SubwayArrivalResponseTest {

    private val moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()
    private val adapter = moshi.adapter(SubwayArrivalResponse::class.java)

    @Test
    fun `parses valid json response correctly`() {
        // Given
        val json = """
            {
              "errorMessage": {
                "status": 200,
                "code": "INFO-000",
                "message": "정상 처리되었습니다.",
                "link": "",
                "developerMessage": "",
                "total": 4
              },
              "realtimeArrivalList": [
                {
                  "subwayId": "1002",
                  "updnLine": "내선",
                  "trainLineNm": "성수행 - 삼성방면",
                  "statnNm": "선릉",
                  "btrainNo": "2312",
                  "arvlMsg2": "2분 후 (삼성)",
                  "arvlMsg3": "삼성",
                  "arvlCd": "99",
                  "barvlDt": "120"
                }
              ]
            }
        """.trimIndent()

        // When
        val response = adapter.fromJson(json)

        // Then
        assertThat(response).isNotNull()
        assertThat(response?.errorMessage?.code).isEqualTo("INFO-000")
        assertThat(response?.realtimeArrivalList).hasSize(1)
        
        val firstArrival = response!!.realtimeArrivalList!![0]
        assertThat(firstArrival.subwayId).isEqualTo("1002")
        assertThat(firstArrival.statnNm).isEqualTo("선릉")
        assertThat(firstArrival.trainLineNm).isEqualTo("성수행 - 삼성방면")
        assertThat(firstArrival.arvlMsg2).isEqualTo("2분 후 (삼성)")
        assertThat(firstArrival.barvlDt).isEqualTo("120")
    }

    @Test
    fun `parses error response correctly`() {
        // Given (When API key is invalid or quota exceeded, sometimes body is different or errorMessage has different code)
        // Normal error response structure
        val json = """
            {
              "errorMessage": {
                "status": 333,
                "code": "INFO-200",
                "message": "해당하는 데이터가 없습니다.",
                "link": "",
                "developerMessage": "",
                "total": 0
              },
              "realtimeArrivalList": []
            }
        """.trimIndent()

        // When
        val response = adapter.fromJson(json)

        // Then
        assertThat(response).isNotNull()
        assertThat(response?.errorMessage?.code).isEqualTo("INFO-200")
        assertThat(response?.realtimeArrivalList).isEmpty()
    }
}
