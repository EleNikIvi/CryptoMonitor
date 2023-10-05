package com.example.cryptomonitor.domain

import androidx.paging.PagingData
import com.example.cryptomonitor.core.model.FavoriteAsset
import kotlinx.coroutines.flow.Flow

interface AssetsRepository {
    suspend fun fetchIcons(iconSize: Int)
    fun getFavoriteAssets(): Flow<PagingData<FavoriteAsset>>
    suspend fun saveFavorite(assetId: String, isFavorite: Boolean)
}