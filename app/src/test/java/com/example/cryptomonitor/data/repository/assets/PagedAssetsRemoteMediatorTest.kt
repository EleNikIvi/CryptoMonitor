package com.example.cryptomonitor.data.repository.assets

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingConfig
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import com.example.cryptomonitor.MainDispatcherExtension
import com.example.cryptomonitor.core.MockDateTimeUtils.mockGetCurrentDateTimeFormatted
import com.example.cryptomonitor.core.model.FavoriteAsset
import com.example.cryptomonitor.data.MockDatabase
import com.example.cryptomonitor.data.core.local.CryptoMonitorDatabase
import com.example.cryptomonitor.data.local.assets.AssetsDao
import com.example.cryptomonitor.data.local.assets.entity.Asset
import com.example.cryptomonitor.data.remote.assets.AssetDto
import com.example.cryptomonitor.data.remote.assets.AssetsApi
import io.mockk.Called
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import retrofit2.Response

@OptIn(ExperimentalPagingApi::class)
@ExtendWith(MainDispatcherExtension::class)
class PagedAssetsRemoteMediatorTest {
    private val assetsApi = mockk<AssetsApi>(relaxed = true)
    private val assetsDao = mockk<AssetsDao>(relaxed = true)
    private val database = mockk<CryptoMonitorDatabase>(relaxed = true)
    private val underTest = PagedAssetsRemoteMediator(
        database = database,
        assetsApi = assetsApi,
        assetDao = assetsDao,
    )

    private val pagingState = PagingState<Int, FavoriteAsset>(
        pages = listOf(),
        anchorPosition = null,
        config = PagingConfig(10),
        leadingPlaceholderCount = 10
    )
    @Test
    fun `WHEN load is called with LoadType REFRESH THEN api call for retrieving assets is triggered`() =
        runTest {
            underTest.load(
                loadType = LoadType.REFRESH,
                state = pagingState,
            )
            coVerify(exactly = 1) {
                assetsApi.getAssets()
            }
        }

    @Test
    fun `WHEN load is called with LoadType APPEND THEN api call for retrieving assets is triggered`() =
        runTest {
            underTest.load(
                loadType = LoadType.APPEND,
                state = pagingState,
            )
            coVerify(exactly = 1) {
                assetsApi.getAssets()
            }
        }

    @Test
    fun `WHEN load is called with LoadType PREPEND THEN api call for retrieving assets is not triggered`() =
        runTest {
            val result = underTest.load(LoadType.PREPEND, pagingState)
            assertTrue { result is RemoteMediator.MediatorResult.Success }
            coVerify {
                assetsApi.getAssets() wasNot Called
            }
        }

    @Test
    fun `WHEN load is called and api response is success THEN dao is called for deleting and adding new data`() =
        runTest {
            val firstAssetId = "BTC"
            val firstAssetName = "Bitcoin"
            val secondAssetId = "EUR"
            val secondAssetName = "Euro"
            val currentTime = "2023-10-08 21:37:28"
            MockDatabase.mockDatabaseWithTransaction(database)
            val api = mockk<AssetsApi>(relaxed = true)
            {
                coEvery {
                    getAssets()
                } returns Response.success(
                    listOf(
                        AssetDto(assetId = firstAssetId, name = firstAssetName, typeIsCrypto = 1, dataSymbolsCount = 0, volume1HrsUsd = 1.0, volume1DayUsd = 1.0, volume1MthUsd = 1.0),
                        AssetDto(assetId = secondAssetId, name = secondAssetName, typeIsCrypto = 0, dataSymbolsCount = 0, volume1HrsUsd = 1.0, volume1DayUsd = 1.0, volume1MthUsd = 1.0),
                    )
                )
            }
            mockGetCurrentDateTimeFormatted(currentTime)
            val listEntities = listOf<Asset>(
                Asset(assetId = firstAssetId, name = firstAssetName, typeIsCrypto = true, dataSymbolsCount = 0, volume1HrsUsd = 1.0, volume1DayUsd = 1.0, volume1MthUsd = 1.0, updated = currentTime,),
                Asset(assetId = secondAssetId, name = secondAssetName, typeIsCrypto = false, dataSymbolsCount = 0, volume1HrsUsd = 1.0, volume1DayUsd = 1.0, volume1MthUsd = 1.0, updated = currentTime,),
            )

            val underTest = PagedAssetsRemoteMediator(
                database = database,
                assetsApi = api,
                assetDao = assetsDao,
            )
            underTest.load(
                loadType = LoadType.REFRESH,
                state = pagingState,
            )
            coVerify(exactly = 1) {
                assetsDao.deleteAssets()
                assetsDao.addAssets(listEntities)
            }
        }
}