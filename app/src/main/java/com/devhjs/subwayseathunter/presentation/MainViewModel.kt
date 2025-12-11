package com.devhjs.subwayseathunter.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devhjs.subwayseathunter.domain.model.Station
import com.devhjs.subwayseathunter.domain.repository.RealtimeRepository
import com.devhjs.subwayseathunter.domain.repository.StationRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val stationRepository: StationRepository,
    private val realtimeRepository: RealtimeRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(MainUiState())
    val uiState: StateFlow<MainUiState> = _uiState.asStateFlow()

    private var searchJob: Job? = null

    // 검색어 입력 시 호출
    fun searchStations(query: String) {
        searchJob?.cancel()
        if (query.isBlank()) {
            _uiState.update { it.copy(searchResults = emptyList()) }
            return
        }
        searchJob = viewModelScope.launch {
            delay(300) // Debounce
            val results = stationRepository.searchStations(query)
            _uiState.update { it.copy(searchResults = results) }
        }
    }

    // 역 선택 시 호출
    fun selectStation(station: Station) {
        _uiState.update { 
            it.copy(
                selectedStation = station,
                isLoading = true,
                errorMessage = null
            ) 
        }
        loadRealtimeArrivals(station.stationName)
    }

    // 실시간 도착 정보 로드
    private fun loadRealtimeArrivals(stationName: String) {
        viewModelScope.launch {
            val result = realtimeRepository.getRealtimeArrivals(stationName)
            _uiState.update { state ->
                if (result.isSuccess) {
                    state.copy(
                        isLoading = false,
                        realtimeArrivals = result.getOrDefault(emptyList())
                                .sortedBy { it.arrivalTimeSeconds } // 도착 시간순 정렬
                    )
                } else {
                    state.copy(
                        isLoading = false,
                        errorMessage = result.exceptionOrNull()?.message ?: "Unknown Error"
                    )
                }
            }
        }
    }
    
    // 에러 메시지 닫기
    fun clearError() {
        _uiState.update { it.copy(errorMessage = null) }
    }
}
