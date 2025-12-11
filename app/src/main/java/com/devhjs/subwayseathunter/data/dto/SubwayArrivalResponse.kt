package com.devhjs.subwayseathunter.data.dto

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class SubwayArrivalResponse(
    @Json(name = "errorMessage") val errorMessage: ErrorMessageDto?,
    @Json(name = "realtimeArrivalList") val realtimeArrivalList: List<RealtimeArrivalDto>?
)

@JsonClass(generateAdapter = true)
data class ErrorMessageDto(
    @Json(name = "status") val status: Int,
    @Json(name = "code") val code: String,
    @Json(name = "message") val message: String,
    @Json(name = "link") val link: String?,
    @Json(name = "developerMessage") val developerMessage: String?,
    @Json(name = "total") val total: Int
)
