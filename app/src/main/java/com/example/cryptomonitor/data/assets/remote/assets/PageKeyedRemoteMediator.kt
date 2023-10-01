package com.example.cryptomonitor.data.assets.remote.assets

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.example.cryptomonitor.data.assets.AssetMapper.toModel
import com.example.cryptomonitor.data.assets.local.AssetDao
import com.example.cryptomonitor.data.core.local.CryptoMonitorDatabase
import com.example.cryptomonitor.model.Asset
import retrofit2.HttpException
import java.io.IOException

@OptIn(ExperimentalPagingApi::class)
class PageKeyedRemoteMediator(
    private val db: CryptoMonitorDatabase,
    private val assetApi: AssetsApi,
) : RemoteMediator<Int, Asset>() {
    private val assetDao: AssetDao = db.assetDao

    override suspend fun initialize(): InitializeAction {
        // Require that remote REFRESH is launched on initial load and succeeds before launching
        // remote PREPEND / APPEND.
        return InitializeAction.LAUNCH_INITIAL_REFRESH
    }

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, Asset>
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
                    } ?: emptyList()
                    assetDao.addAssets(items)
                }
            }

            return MediatorResult.Success(endOfPaginationReached = items.isEmpty())
        } catch (e: IOException) {
            return MediatorResult.Error(e)
        } catch (e: HttpException) {
            return MediatorResult.Error(e)
        }
    }
}
