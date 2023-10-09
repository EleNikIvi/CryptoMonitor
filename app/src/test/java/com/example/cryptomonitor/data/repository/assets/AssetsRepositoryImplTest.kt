package com.example.cryptomonitor.data.repository.assets

import com.example.cryptomonitor.MainDispatcherExtension
import com.example.cryptomonitor.data.core.local.CryptoMonitorDatabase
import com.example.cryptomonitor.data.local.assets.entity.Favorite
import com.example.cryptomonitor.data.local.assets.entity.IconEntity
import com.example.cryptomonitor.data.remote.assets.AssetsApi
import com.example.cryptomonitor.data.remote.assets.IconDto
import com.example.cryptomonitor.data.MockDatabase.mockDatabaseWithTransaction
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import retrofit2.Response

@ExtendWith(MainDispatcherExtension::class)
class AssetsRepositoryImplTest {
    private val assetsApi = mockk<AssetsApi>()
    private val database = mockk<CryptoMonitorDatabase>(relaxed = true)
    private val favoriteDao = database.favoriteDao
    private val iconsDao = database.iconsDao
    private val underTest = AssetsRepositoryImpl(
        assetsApi = assetsApi,
        database = database
    )

    @Test
    fun `WHEN fetchIcons is called THEN appropriate api call is triggered`() =
        runTest {
            val iconSize = 32
            underTest.fetchIcons(iconSize)
            coVerify(exactly = 1) {
                assetsApi.getAssetsIcons(iconSize)
            }
        }

    @Test
    fun `WHEN fetchIcons is called and api response is success THEN dao is called for deleting and adding new icons`() =
        runTest {
            val iconSize = 32
            val firstAssetId = "BTC"
            val firstUrl = "firstUrl"
            val secondAssetId = "EUR"
            val secondUrl = "secondUrl"
            mockDatabaseWithTransaction(database)
            val api = mockk<AssetsApi>()
            {
                coEvery {
                    getAssetsIcons(any())
                } returns Response.success(
                    listOf(
                        IconDto(assetId = firstAssetId, url = firstUrl),
                        IconDto(assetId = secondAssetId, url = secondUrl)
                    )
                )
            }
            val underTest = AssetsRepositoryImpl(
                assetsApi = api,
                database = database
            )
            val listEntities = listOf<IconEntity>(
                IconEntity(
                    assetId = firstAssetId,
                    url = firstUrl
                ),
                IconEntity(
                    assetId = secondAssetId,
                    url = secondUrl,
                )
            )

            underTest.fetchIcons(iconSize)
            coVerify(exactly = 1) {
                iconsDao.deleteIcons()
                iconsDao.addIcons(listEntities)
            }
        }

    @Test
    fun `WHEN saveFavorite is called THEN dao is called for saving data of favorite`() =
        runTest {
            val assetId = "BTC"
            val isFavorite = true
            mockDatabaseWithTransaction(database)

            underTest.saveFavorite(assetId, isFavorite)
            coVerify(exactly = 1) {
                favoriteDao.addFavorite(
                    Favorite(
                        assetId = assetId,
                        isFavorite = isFavorite
                    )
                )
            }
        }
}