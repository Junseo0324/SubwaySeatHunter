package com.devhjs.subwayseathunter.domain.model

data class RealtimeArrival(
    val subwayId: String,
    val stationName: String, // 역명
    val trainLineNm: String, // 도착지 방면
    val arrivalMessage: String, // 첫 번째 도착 메시지 (e.g. 2분 후)
    val secondMessage: String, // 두 번째 도착 메시지
    val arrivalCode: String, // 도착 코드
    val arrivalTimeSeconds: Int, // 도착 예정 시간 (초)
    val creationTime: Long = System.currentTimeMillis() // 생성 시간 (갱신 주기 체크용)
)
