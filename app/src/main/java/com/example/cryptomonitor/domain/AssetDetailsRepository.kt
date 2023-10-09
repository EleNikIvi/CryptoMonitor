package com.example.cryptomonitor.domain

import com.example.cryptomonitor.core.model.AssetDetails
import com.example.cryptomonitor.core.model.ExchangeRate
import kotlinx.coroutines.flow.Flow

interface AssetDetailsRepository {
    suspend fun fetchExchangeRate(assetId: String)
    fun getAssetIcon(assetId: String): Flow<String>
    fun getAssetDetails(assetId: String): Flow<AssetDetails>
    fun getExchangeRate(assetId: String): Flow<ExchangeRate?>
    suspend fun fetchAsset(assetId: String)
}