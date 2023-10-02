package com.example.cryptomonitor.domain.asset

import androidx.paging.PagingData
import androidx.paging.PagingSource
import com.example.cryptomonitor.model.FavoriteAsset
import com.example.cryptomonitor.model.Result
import kotlinx.coroutines.flow.Flow

interface AssetsRepository {
    fun getFavoriteAssets(): Flow<PagingData<FavoriteAsset>>
    suspend fun saveFavorite(assetId: String, isFavorite: Boolean)
}