package com.example.cryptomonitor.data.assets

import com.example.cryptomonitor.data.assets.AssetMapper.toModel
import com.example.cryptomonitor.data.assets.local.AssetDao
import com.example.cryptomonitor.data.assets.local.FavoriteAssetDao
import com.example.cryptomonitor.model.FavoriteAsset
import com.example.cryptomonitor.data.assets.remote.assets.AssetDto
import com.example.cryptomonitor.data.assets.remote.assets.AssetsApi
import com.example.cryptomonitor.domain.asset.AssetsRepository
import com.example.cryptomonitor.model.Asset
import com.example.cryptomonitor.model.Result
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AssetsRepositoryImpl @Inject constructor(
    private val api: AssetsApi,
    private val assetDao: AssetDao,
    private val favoriteAssetDao: FavoriteAssetDao,
) : AssetsRepository {

    override suspend fun refreshAssets(): Result {
        return try {
            val result = api.getAssets()
            if (result.isSuccessful) {
                saveAssets(result.body())
                Result.Success
            } else {
                Result.Error
            }
        } catch (e: Exception) {
            Result.Error
        }
    }

    override suspend fun getAssets(): Flow<List<Asset>> = assetDao.getAllAssets()

    override suspend fun saveFavorite(assetId: String, isFavorite: Boolean) {
        favoriteAssetDao.addFavorite(
            FavoriteAsset(
                assetId = assetId,
                isFavorite = isFavorite
            )
        )
    }

    override suspend fun getFavorites(): Flow<List<FavoriteAsset>> =
        favoriteAssetDao.getAllFavorites()

    private suspend fun saveAssets(assets: List<AssetDto>?) {
        assets?.forEach {
            assetDao.addAsset(it.toModel())
        }
    }
}