package com.devhjs.subwayseathunter

import android.app.Application
import com.devhjs.subwayseathunter.domain.repository.StationRepository
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltAndroidApp
class SubwaySeatHunterApplication : Application() {

    @Inject
    lateinit var stationRepository: StationRepository

    override fun onCreate() {
        super.onCreate()
        CoroutineScope(Dispatchers.IO).launch {
            stationRepository.loadInitialData()
        }
    }
}
