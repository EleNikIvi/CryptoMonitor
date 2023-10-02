package com.example.cryptomonitor.domain.asset

import androidx.paging.PagingData
import com.example.cryptomonitor.model.FavoriteAsset
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AssetsInteractor @Inject constructor(
    private val assetsRepository: AssetsRepository,
) {
    fun getFavoriteAssets(): Flow<PagingData<FavoriteAsset>> = assetsRepository.getFavoriteAssets()

    suspend fun saveFavorite(assetId: String, isFavorite: Boolean) =
        assetsRepository.saveFavorite(assetId, isFavorite)

}