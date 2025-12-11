package com.devhjs.subwayseathunter.data.dto

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class RealtimeArrivalDto(
    @Json(name = "subwayId") val subwayId: String, // 호선ID (1002: 2호선)
    @Json(name = "updnLine") val updnLine: String, // 상하행선구분 (0:상행/내선, 1:하행/외선)
    @Json(name = "trainLineNm") val trainLineNm: String, // 도착지방면 (성수행 - 구로디지털단지방면)
    @Json(name = "subwayHeading") val subwayHeading: String?, // 내리는문방향 (오른쪽, 왼쪽) - Docs says subwayHeading but often API has differnt names, using common ones. Actually 'subwayHeading' might not be in the docs for this API exactly or named differently. Let's check standard names. 'statnNm' is station name.
    // Re-checking standard fields from common Seoul API usage.
    // 'statnNm': 지하철역명
    // 'btrainNo': 열차번호
    // 'arvlMsg2': 첫번째 도착메세지 (전역 진입, 2분 후)
    // 'arvlMsg3': 두번째 도착메세지 (종합운동장)
    // 'arvlCd': 도착코드 (0:진입, 1:도착, 2:출발, 3:전역출발, 4:전역진입, 5:전역도착, 99:운행중)
    @Json(name = "statnNm") val statnNm: String,
    @Json(name = "btrainNo") val btrainNo: String,
    @Json(name = "arvlMsg2") val arvlMsg2: String,
    @Json(name = "arvlMsg3") val arvlMsg3: String,
    @Json(name = "arvlCd") val arvlCd: String,
    @Json(name = "barvlDt") val barvlDt: String // 열차도착예정시간 (단위:초)
)
