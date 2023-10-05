package com.example.cryptomonitor.data.repository.assets

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.example.cryptomonitor.core.DateTimeUtils.getCurrentDateTimeFormatted
import com.example.cryptomonitor.core.model.FavoriteAsset
import com.example.cryptomonitor.data.core.local.CryptoMonitorDatabase
import com.example.cryptomonitor.data.core.remote.UnsuccessfulResponseCodeException
import com.example.cryptomonitor.data.local.assets.AssetsDao
import com.example.cryptomonitor.data.remote.assets.AssetsApi
import com.example.cryptomonitor.data.repository.assets.AssetMapper.toEntity
import retrofit2.HttpException
import java.io.IOException

@OptIn(ExperimentalPagingApi::class)
class PagedAssetsRemoteMediator(
    private val database: CryptoMonitorDatabase,
    private val assetsApi: AssetsApi,
    private val assetDao: AssetsDao,
) : RemoteMediator<Int, FavoriteAsset>() {

    override suspend fun initialize(): InitializeAction {
        return InitializeAction.LAUNCH_INITIAL_REFRESH
    }

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, FavoriteAsset>
    ): MediatorResult {
        try {
            if (loadType == LoadType.PREPEND) return MediatorResult.Success(endOfPaginationReached = true)

            val result = assetsApi.getAssets()

            if (result.isSuccessful) {
                database.withTransaction {
                    if (loadType == LoadType.REFRESH) {
                        assetDao.deleteAssets()
                    }
                    val formattedDateTime = getCurrentDateTimeFormatted()
                    result.body()?.map {
                        it.toEntity(formattedDateTime)
                    }?.let {
                        assetDao.addAssets(it)
                    } ?: throw UnsuccessfulResponseCodeException("Server response has empty body")
                }
            } else {
                throw UnsuccessfulResponseCodeException(result.message())
            }

            return MediatorResult.Success(endOfPaginationReached = true)
        } catch (e: UnsuccessfulResponseCodeException) {
            return MediatorResult.Error(e)
        } catch (e: IOException) {
            return MediatorResult.Error(e)
        } catch (e: HttpException) {
            return MediatorResult.Error(e)
        }
    }
}
