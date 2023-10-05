package com.example.cryptomonitor.data.repository.assets

import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.room.withTransaction
import com.example.cryptomonitor.core.model.FavoriteAsset
import com.example.cryptomonitor.data.core.local.CryptoMonitorDatabase
import com.example.cryptomonitor.data.local.assets.AssetsDao
import com.example.cryptomonitor.data.local.assets.FavoriteDao
import com.example.cryptomonitor.data.local.assets.IconsDao
import com.example.cryptomonitor.data.local.assets.entity.Favorite
import com.example.cryptomonitor.data.remote.assets.AssetsApi
import com.example.cryptomonitor.data.repository.assets.AssetMapper.toEntity
import com.example.cryptomonitor.domain.AssetsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AssetsRepositoryImpl @Inject constructor(
    private val assetsApi: AssetsApi,
    private val database: CryptoMonitorDatabase,
) : AssetsRepository {
    private val favoriteAssetDao: FavoriteDao = database.favoriteDao
    private val assetDao: AssetsDao = database.assetDao
    private val iconsDao: IconsDao = database.iconsDao

    override suspend fun fetchIcons(iconSize: Int) {
        try {
            val iconsResult = assetsApi.getAssetsIcons(iconSize)
            if (iconsResult.isSuccessful) {
                database.withTransaction {
                    iconsResult.body()?.map {
                        it.toEntity()
                    }?.let {
                        iconsDao.deleteAssets()
                        iconsDao.addIcons(it)
                    }
                }
            }
        } catch (e: Exception) {
            Log.d("AssetsRepositoryImpl", "Exception e $e")
        }
    }

    @OptIn(ExperimentalPagingApi::class)
    override fun getFavoriteAssets(): Flow<PagingData<FavoriteAsset>> = Pager(
        config = PagingConfig(
            pageSize = 10,
            prefetchDistance = 5,
            enablePlaceholders = false,
        ),
        remoteMediator = PagedAssetsRemoteMediator(
            database = database,
            assetsApi = assetsApi,
            assetDao = assetDao
        )
    ) {
        favoriteAssetDao.getPagedFavorites()
    }.flow

    override suspend fun saveFavorite(assetId: String, isFavorite: Boolean) {
        favoriteAssetDao.addFavorite(
            Favorite(
                assetId = assetId,
                isFavorite = isFavorite
            )
        )
    }
}