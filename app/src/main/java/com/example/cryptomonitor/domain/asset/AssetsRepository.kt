package com.example.cryptomonitor.domain.asset

import com.example.cryptomonitor.model.Asset
import com.example.cryptomonitor.model.FavoriteAsset
import com.example.cryptomonitor.model.Result
import kotlinx.coroutines.flow.Flow

interface AssetsRepository {

    suspend fun refreshAssets(): Result

    suspend fun getAssets(): Flow<List<Asset>>
    suspend fun saveFavorite(assetId: String, isFavorite: Boolean)
    suspend fun getFavorites(): Flow<List<FavoriteAsset>>
}