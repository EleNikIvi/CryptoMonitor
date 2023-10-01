package com.example.cryptomonitor.domain.asset

import androidx.paging.ExperimentalPagingApi
import androidx.paging.PagingData
import com.example.cryptomonitor.model.Asset
import com.example.cryptomonitor.model.Result
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AssetsInteractor @Inject constructor(
    private val assetsRepository: AssetsRepository,
) {
    suspend fun refreshAssets(): Result =
        assetsRepository.refreshAssets()
    
    fun getAssets(): Flow<PagingData<Asset>> = assetsRepository.getAssets()

    //suspend fun getFavoriteAssets(): Flow<List<FavoriteId>> = assetsRepository.getFavorites()

    suspend fun saveFavorite(assetId: String, isFavorite: Boolean) =
        assetsRepository.saveFavorite(assetId, isFavorite)

}