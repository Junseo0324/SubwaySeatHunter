package com.devhjs.subwayseathunter.presentation

import app.cash.turbine.test
import com.devhjs.subwayseathunter.domain.model.RealtimeArrival
import com.devhjs.subwayseathunter.domain.model.Station
import com.devhjs.subwayseathunter.domain.repository.RealtimeRepository
import com.devhjs.subwayseathunter.domain.repository.StationRepository
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class MainViewModelTest {

    private val stationRepository: StationRepository = mockk()
    private val realtimeRepository: RealtimeRepository = mockk()
    private lateinit var viewModel: MainViewModel
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        viewModel = MainViewModel(stationRepository, realtimeRepository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `searchStations updates searchResults`() = runTest(testDispatcher) {
        // Given
        val stations = listOf(
            Station("Gangnam", "Line 2", "216", 37.0, 127.0, null)
        )
        coEvery { stationRepository.searchStations("Gang") } returns stations

        // When
        viewModel.searchStations("Gang")

        // Then
        viewModel.uiState.test {
            val initialState = awaitItem() // Initial empty state
            assertThat(initialState.searchResults).isEmpty()
            
            // Wait for debounce and search result
            testScheduler.advanceTimeBy(301)
            
            val resultState = awaitItem()
            assertThat(resultState.searchResults).hasSize(1)
            assertThat(resultState.searchResults[0].stationName).isEqualTo("Gangnam")
        }
    }

    @Test
    fun `selectStation loads realtime arrivals`() = runTest(testDispatcher) {
        // Given
        val station = Station("Gangnam", "Line 2", "216", 37.0, 127.0, null)
        val arrivals = listOf(
            RealtimeArrival("1002", "Gangnam", "Dest", "2min", "Dest", "99", 120),
            RealtimeArrival("1002", "Gangnam", "Dest", "5min", "Dest", "99", 300)
        )
        coEvery { realtimeRepository.getRealtimeArrivals("Gangnam") } returns Result.success(arrivals)

        // When
        viewModel.selectStation(station)

        // Then
        viewModel.uiState.test {
            // 1. Initial State (or previous state if any) - Skip if necessary or check strictly
            // Current implementation: selectStation updates properties directly. 
            // _uiState.update happens synchronously for isLoading=true, but loadRealtimeArrivals is async.
            
            // Note: selectStation has _uiState.update { ... isLoading=true ... }
            // Then it calls loadRealtimeArrivals which launches coroutine.
            
            // First emission: isLoading = true, selectedStation = station
            val loadingState = awaitItem()
            assertThat(loadingState.isLoading).isTrue()
            assertThat(loadingState.selectedStation).isEqualTo(station)
            
            // Run coroutine
            testScheduler.advanceUntilIdle()
            
            // Second emission: isLoading = false, realtimeArrivals = arrivals
            val successState = awaitItem()
            assertThat(successState.isLoading).isFalse()
            assertThat(successState.realtimeArrivals).hasSize(2)
            assertThat(successState.realtimeArrivals[0].arrivalTimeSeconds).isEqualTo(120)
        }
    }
}
