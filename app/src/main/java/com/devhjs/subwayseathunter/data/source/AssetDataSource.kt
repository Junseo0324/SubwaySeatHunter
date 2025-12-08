package com.devhjs.subwayseathunter.data.source

import java.io.InputStream

interface AssetDataSource {
    fun openStationAsset(): InputStream
}
