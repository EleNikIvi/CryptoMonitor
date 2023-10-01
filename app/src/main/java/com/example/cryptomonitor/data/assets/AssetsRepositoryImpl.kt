package com.example.cryptomonitor.data.assets

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.PagingSource
import com.example.cryptomonitor.data.assets.AssetMapper.toModel
import com.example.cryptomonitor.data.assets.local.AssetDao
import com.example.cryptomonitor.data.assets.local.FavoriteIdDao
import com.example.cryptomonitor.data.assets.remote.assets.AssetDto
import com.example.cryptomonitor.data.assets.remote.assets.AssetsApi
import com.example.cryptomonitor.data.assets.remote.assets.PageKeyedRemoteMediator
import com.example.cryptomonitor.data.core.local.CryptoMonitorDatabase
import com.example.cryptomonitor.domain.asset.AssetsRepository
import com.example.cryptomonitor.model.Asset
import com.example.cryptomonitor.model.FavoriteAsset
import com.example.cryptomonitor.model.FavoriteId
import com.example.cryptomonitor.model.Result
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AssetsRepositoryImpl @Inject constructor(
    private val api: AssetsApi,
    private val db: CryptoMonitorDatabase,
    private val assetDao: AssetDao,
    private val favoriteAssetDao: FavoriteIdDao,
) : AssetsRepository {

    override suspend fun refreshAssets(): Result {
        return try {
            val result = api.getAssets()
            if (result.isSuccessful) {
                assetDao.deleteAssets()
                saveAssets(result.body())
                Result.Success
            } else {
                Result.Error
            }
        } catch (e: Exception) {
            Result.Error
        }
    }

    @OptIn(ExperimentalPagingApi::class)
    override fun getAssets(): Flow<PagingData<Asset>> = Pager(
        config = PagingConfig(
            pageSize = 10,
            prefetchDistance = 5,
            enablePlaceholders = false,
        ),
        remoteMediator = PageKeyedRemoteMediator(db, api)
    ) {
        assetDao.getAllAssets()
    }.flow

    override suspend fun saveFavorite(assetId: String, isFavorite: Boolean) {
        favoriteAssetDao.addFavorite(
            FavoriteId(
                assetId = assetId,
                isFavorite = isFavorite
            )
        )
    }

    override fun getFavorites(): PagingSource<Int, FavoriteAsset> = favoriteAssetDao.getPagedFavorites()

    private suspend fun saveAssets(assets: List<AssetDto>?) {
        assets?.map {
            it.toModel()
        }?.let{
            assetDao.addAssets(it)
        }
    }
}