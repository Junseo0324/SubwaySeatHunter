package com.devhjs.subwayseathunter.presentation.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.devhjs.subwayseathunter.domain.model.RealtimeArrival
import com.devhjs.subwayseathunter.domain.model.Station
import com.devhjs.subwayseathunter.ui.theme.GreenLine2

@Composable
fun DetailScreen(
    station: Station,
    arrivals: List<RealtimeArrival>
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text(
            text = "${station.stationName}역 도착 정보",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold,
            color = GreenLine2,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        if (arrivals.isEmpty()) {
            Text(
                text = "도착 정보가 없습니다.",
                modifier = Modifier.padding(vertical = 16.dp)
            )
        } else {
            LazyColumn {
                items(arrivals) { arrival ->
                    ArrivalItem(arrival)
                    Divider(modifier = Modifier.padding(vertical = 8.dp))
                }
            }
        }
    }
}

@Composable
fun ArrivalItem(arrival: RealtimeArrival) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = arrival.trainLineNm, // 방면
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = arrival.secondMessage, // 현재 위치 (전역 진입 등)
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
        
        Column(horizontalAlignment = Alignment.End) {
            Text(
                text = arrival.arrivalMessage, // 도착 예정 시간 (2분 후)
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.primary,
                fontWeight = FontWeight.Bold
            )
        }
    }
}
