package com.example.cryptomonitor.domain.assetdetails

import com.example.cryptomonitor.core.model.AssetDetails
import com.example.cryptomonitor.core.model.ExchangeRate
import com.example.cryptomonitor.domain.AssetDetailsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AssetDetailsInteractor @Inject constructor(
    private val assetDetailsRepository: AssetDetailsRepository,
) {
    suspend fun fetchExchangeRate(assetId: String) =
        assetDetailsRepository.fetchExchangeRate(assetId = assetId)

    suspend fun fetchAsset(assetId: String) = assetDetailsRepository.fetchAsset(assetId = assetId)

    fun getAssetIconUrl(assetId: String): Flow<String> =
        assetDetailsRepository.getAssetIcon(assetId = assetId)

    fun getAssetDetails(assetId: String): Flow<AssetDetails> =
        assetDetailsRepository.getAssetDetails(assetId = assetId)

    fun getExchangeRate(assetId: String): Flow<ExchangeRate?> =
        assetDetailsRepository.getExchangeRate(assetId = assetId)
}