package com.example.cryptomonitor.data.assets

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.example.cryptomonitor.data.assets.AssetMapper.toModel
import com.example.cryptomonitor.data.assets.local.AssetsDao
import com.example.cryptomonitor.data.assets.local.entity.Asset
import com.example.cryptomonitor.data.assets.remote.UnsuccessfulResponseCodeException
import com.example.cryptomonitor.data.assets.remote.assets.AssetsApi
import com.example.cryptomonitor.data.core.local.CryptoMonitorDatabase
import com.example.cryptomonitor.model.FavoriteAsset
import retrofit2.HttpException
import java.io.IOException

@OptIn(ExperimentalPagingApi::class)
class PagedAssetsRemoteMediator(
    private val db: CryptoMonitorDatabase,
    private val assetApi: AssetsApi,
) : RemoteMediator<Int, FavoriteAsset>() {
    private val assetDao: AssetsDao = db.assetDao

    override suspend fun initialize(): InitializeAction {
        // Require that remote REFRESH is launched on initial load and succeeds before launching
        // remote PREPEND / APPEND.
        return InitializeAction.LAUNCH_INITIAL_REFRESH
    }

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, FavoriteAsset>
    ): MediatorResult {
        try {
            val result = assetApi.getAssets()

            var items = emptyList<Asset>()
            if (result.isSuccessful) {
                db.withTransaction {
                    if (loadType == LoadType.REFRESH) {
                        assetDao.deleteAssets()
                    }
                    items = result.body()?.map {
                        it.toModel()
                    } ?: throw UnsuccessfulResponseCodeException("Server response has empty body")
                    assetDao.addAssets(items)
                }
            } else {
                throw UnsuccessfulResponseCodeException(result.message())
            }

            return MediatorResult.Success(endOfPaginationReached = items.isEmpty())
        } catch (e: UnsuccessfulResponseCodeException) {
            return MediatorResult.Error(e)
        } catch (e: IOException) {
            return MediatorResult.Error(e)
        } catch (e: HttpException) {
            return MediatorResult.Error(e)
        }
    }
}
