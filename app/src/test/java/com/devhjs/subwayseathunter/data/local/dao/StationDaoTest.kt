package com.devhjs.subwayseathunter.data.local.dao

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.devhjs.subwayseathunter.data.local.AppDatabase
import com.devhjs.subwayseathunter.data.local.entity.StationEntity
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class StationDaoTest {

    private lateinit var db: AppDatabase
    private lateinit var stationDao: StationDao

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context, AppDatabase::class.java
        ).allowMainThreadQueries().build()
        stationDao = db.stationDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    fun insertAndGetStations() = runBlocking {
        // Given
        val station = StationEntity(
            stationName = "Gangnam",
            lineName = "Line 2",
            stationCode = "216",
            lat = 37.4979,
            lng = 127.0276,
            transferInfo = null
        )

        // When
        stationDao.insertStations(listOf(station))
        val loadedStations = stationDao.getStationsByName("Gangnam")

        // Then
        assertThat(loadedStations).isNotEmpty()
        assertThat(loadedStations[0].stationCode).isEqualTo("216")
    }
}
