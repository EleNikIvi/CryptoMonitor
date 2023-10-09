package com.example.cryptomonitor.domain.asset

import com.example.cryptomonitor.MainDispatcherExtension
import com.example.cryptomonitor.domain.AssetsRepository
import io.mockk.coVerify
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(MainDispatcherExtension::class)
class AssetsInteractorTest {

    private val repository = mockk<AssetsRepository>(relaxed = true)
    private val underTest = AssetsInteractor(assetsRepository = repository)

    @Test
    fun `WHEN fetchIcons is called THEN appropriate repository call is triggered`() =
        runTest {
            val iconSize = 32
            underTest.fetchIcons(iconSize)
            coVerify {
                repository.fetchIcons(iconSize)
            }
        }

    @Test
    fun `WHEN getFavoriteAssets is called THEN appropriate interactor call is triggered`() =
        runTest {
            underTest.getFavoriteAssets()
            verify {
                repository.getFavoriteAssets()
            }
        }

    @Test
    fun `WHEN saveFavorite is called THEN appropriate interactor call is triggered`() =
        runTest {
            val assetId = "BTC"
            val isFavorite = true
            underTest.saveFavorite(assetId, isFavorite)
            coVerify {
                repository.saveFavorite(assetId, isFavorite)
            }
        }
}