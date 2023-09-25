package com.example.cryptomonitor.domain.asset

import com.example.cryptomonitor.model.Asset
import com.example.cryptomonitor.model.FavoriteAsset
import com.example.cryptomonitor.model.Result
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AssetsInteractor @Inject constructor(
    private val assetsRepository: AssetsRepository,
) {
    suspend fun refreshAssets(): Result =
        assetsRepository.refreshAssets()

    suspend fun getAssets(): Flow<List<Asset>> = assetsRepository.getAssets()

    suspend fun getFavoriteAssets(): Flow<List<FavoriteAsset>> = assetsRepository.getFavorites()

    suspend fun saveFavorite(assetId: String, isFavorite: Boolean) =
        assetsRepository.saveFavorite(assetId, isFavorite)

}