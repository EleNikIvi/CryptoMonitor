package com.example.cryptomonitor.data.local.details

import androidx.room.Dao
import androidx.room.Query
import com.example.cryptomonitor.core.model.AssetDetails
import com.example.cryptomonitor.data.local.assets.entity.Asset
import kotlinx.coroutines.flow.Flow

@Dao
interface AssetDetailsDao {
    @Query("SELECT asset.assetId AS assetId, asset.name AS name, asset.typeIsCrypto AS typeIsCrypto, asset.dataSymbolsCount AS dataSymbolsCount, asset.volume1DayUsd AS volume1DayUsd, asset.priceUsd AS priceUsd, asset.updated AS assetUpdated FROM asset WHERE asset.assetId = :assetId")
    fun getAssetDetails(assetId: String): Flow<AssetDetails>
}