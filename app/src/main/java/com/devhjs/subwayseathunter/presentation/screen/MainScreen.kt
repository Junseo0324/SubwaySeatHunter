package com.devhjs.subwayseathunter.presentation.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue // Important for by keyword
import androidx.hilt.navigation.compose.hiltViewModel
import com.devhjs.subwayseathunter.presentation.MainViewModel

@OptIn(ExperimentalMaterial3Api::class)

@Composable
fun MainScreen(
    viewModel: MainViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    var searchQuery by remember { mutableStateOf("") }

    Scaffold(
        modifier = Modifier.fillMaxSize()
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .padding(16.dp)
        ) {
            // Search Bar
            androidx.compose.material3.OutlinedTextField(
                value = searchQuery,
                onValueChange = { 
                    searchQuery = it
                    viewModel.searchStations(it) 
                },
                label = { Text("지하철역 검색 (예: 강남)") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Station List
            LazyColumn {
                items(uiState.searchResults) { station ->
                    StationItem(
                        station = station,
                        onClick = { viewModel.selectStation(station) }
                    )
                }
            }
            
            // Detail Bottom Sheet
            if (uiState.selectedStation != null) {
                androidx.compose.material3.ModalBottomSheet(
                    onDismissRequest = { viewModel.clearSelection() },
                    sheetState = androidx.compose.material3.rememberModalBottomSheetState(skipPartiallyExpanded = true)
                ) {
                    DetailScreen(
                        station = uiState.selectedStation!!,
                        arrivals = uiState.realtimeArrivals
                    )
                }
            }
        }
    }
}
