package com.example.cryptomonitor.data.assets

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.cryptomonitor.data.assets.local.FavoriteDao
import com.example.cryptomonitor.data.assets.local.entity.Favorite
import com.example.cryptomonitor.data.assets.remote.assets.AssetsApi
import com.example.cryptomonitor.data.core.local.CryptoMonitorDatabase
import com.example.cryptomonitor.domain.asset.AssetsRepository
import com.example.cryptomonitor.model.FavoriteAsset
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AssetsRepositoryImpl @Inject constructor(
    private val api: AssetsApi,
    private val db: CryptoMonitorDatabase,
    private val favoriteAssetDao: FavoriteDao,
) : AssetsRepository {

    @OptIn(ExperimentalPagingApi::class)
    override fun getFavoriteAssets(): Flow<PagingData<FavoriteAsset>> = Pager(
        config = PagingConfig(
            pageSize = 10,
            prefetchDistance = 5,
            enablePlaceholders = false,
        ),
        remoteMediator = PagedAssetsRemoteMediator(db, api)
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