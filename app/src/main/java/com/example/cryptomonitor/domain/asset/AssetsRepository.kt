package com.example.cryptomonitor.domain.asset

import androidx.paging.PagingData
import androidx.paging.PagingSource
import com.example.cryptomonitor.model.Asset
import com.example.cryptomonitor.model.FavoriteAsset
import com.example.cryptomonitor.model.Result
import kotlinx.coroutines.flow.Flow

interface AssetsRepository {

    suspend fun refreshAssets(): Result

    fun getAssets(): Flow<PagingData<Asset>>
    suspend fun saveFavorite(assetId: String, isFavorite: Boolean)
    fun getFavorites(): PagingSource<Int, FavoriteAsset>
}