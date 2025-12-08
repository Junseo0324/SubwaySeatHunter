package com.devhjs.subwayseathunter.data.source

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import java.io.InputStream
import javax.inject.Inject

class AssetDataSourceImpl @Inject constructor(
    @ApplicationContext private val context: Context
) : AssetDataSource {
    override fun openStationAsset(): InputStream {
        return context.assets.open("서울시_역사마스터_정보.csv")
    }
}
