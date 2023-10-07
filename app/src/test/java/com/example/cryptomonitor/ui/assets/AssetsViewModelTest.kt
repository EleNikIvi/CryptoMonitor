package com.example.cryptomonitor.ui.assets

import androidx.lifecycle.SavedStateHandle
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.testing.asPagingSourceFactory
import androidx.paging.testing.asSnapshot
import app.cash.turbine.test
import com.example.cryptomonitor.MainDispatcherExtension
import com.example.cryptomonitor.core.model.FavoriteAsset
import com.example.cryptomonitor.domain.asset.AssetsInteractor
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(MainDispatcherExtension::class)
class AssetsViewModelTest {

    private val btc = FavoriteAsset(
        id = 1,
        assetId = "BTC",
        name = "Bitcoin",
        dataSymbolsCount = 0,
        updated = "",
    )
    private val eurFavorite = FavoriteAsset(
        id = 2,
        assetId = "EUR",
        name = "EURO",
        dataSymbolsCount = 0,
        updated = "",
        isFavorite = true,
    )
    private val btci = FavoriteAsset(
        id = 3,
        assetId = "BTCI",
        name = "Bitcoin I",
        dataSymbolsCount = 0,
        updated = "",
    )
    private val fullListOfAssets: List<FavoriteAsset> = listOf(btc, eurFavorite, btci)
    private val assetsInteractor = mockk<AssetsInteractor>(relaxed = true) {
        every {
            getFavoriteAssets()
        } returns Pager(
            config = PagingConfig(
                pageSize = 10,
                prefetchDistance = 5,
                enablePlaceholders = false,
            ),
            initialKey = null,
            pagingSourceFactory = fullListOfAssets.asPagingSourceFactory(),
        ).flow
    }

    @Test
    fun `WHEN screen is open THEN favorite assets and icons are retrieved and asset contains list of retrieved assets`() =
        runTest {
            val underTest = createViewModelInstance(savedStateHandle = SavedStateHandle())

            val itemsSnapshot: List<FavoriteAsset> = underTest.assets.asSnapshot {
                // Scroll to the 10th item in the list. This will also suspend till
                // the prefetch requirement is met if there's one.
                // It also suspends until all loading is complete.
                scrollTo(index = 10)
            }
            coVerify {
                assetsInteractor.fetchIcons(32)
                assetsInteractor.getFavoriteAssets()
            }

            val expected = fullListOfAssets
            // With the asSnapshot complete, we can now verify that the snapshot
            // has the expected values
            assertEquals(expected, itemsSnapshot)

        }

    @Test
    fun `WHEN search term changes THEN asset contains list of filtered assets`() =
        runTest {
            val underTest = createViewModelInstance(savedStateHandle = SavedStateHandle())

            val searchTerm = "bit"
            val expected = listOf(btc, btci)
            underTest.onSearchTermChange(searchTerm)
            val itemsSnapshot: List<FavoriteAsset> = underTest.assets.asSnapshot {
                // Scroll to the 10th item in the list. This will also suspend till
                // the prefetch requirement is met if there's one.
                // It also suspends until all loading is complete.
                scrollTo(index = 10)
            }

            // With the asSnapshot complete, we can now verify that the snapshot
            // has the expected values
            assertEquals(expected, itemsSnapshot)
        }

    @Test
    fun `WHEN show favorite is called THEN asset contains list of only favorite assets`() =
        runTest {
            val underTest = createViewModelInstance(savedStateHandle = SavedStateHandle())

            val expected = listOf(eurFavorite)
            underTest.onShowFavorite(true)
            val itemsSnapshot: List<FavoriteAsset> = underTest.assets.asSnapshot {
                // Scroll to the 10th item in the list. This will also suspend till
                // the prefetch requirement is met if there's one.
                // It also suspends until all loading is complete.
                scrollTo(index = 10)
            }

            // With the asSnapshot complete, we can now verify that the snapshot
            // has the expected values
            assertEquals(expected, itemsSnapshot)
        }

    @Test
    fun `WHEN favorite is selected THEN appropriate interactor call is triggered`() =
        runTest {
            val underTest = createViewModelInstance(savedStateHandle = SavedStateHandle())

            val assetId = "BTC"
            val isFavorite = true
            underTest.onFavoriteSelected(assetId, isFavorite)
            coVerify {
                assetsInteractor.saveFavorite(assetId, isFavorite)
            }
        }

    @Test
    fun `WHEN search term changes THEN screen state searchTerm changes from empty to new one`() =
        runTest {
            val underTest = createViewModelInstance(savedStateHandle = SavedStateHandle())

            val searchTerm = "some text"
            val emptySearchScreenState = AssetsScreenState(
                searchTerm = "",
            )
            val expectedSideEffect = AssetsScreenState(
                searchTerm = searchTerm,
            )

            underTest.screenState.test {
                assertEquals(emptySearchScreenState, awaitItem())
                underTest.onSearchTermChange(searchTerm)
                assertEquals(expectedSideEffect, awaitItem())
            }
        }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `WHEN search is cleared THEN screen state searchTerm becomes empty`() =
        runTest {
            val underTest = createViewModelInstance(savedStateHandle = SavedStateHandle())

            val searchTerm = "some text"
            val previousSideEffect = AssetsScreenState(
                searchTerm = searchTerm,
            )
            val emptySearchScreenState = AssetsScreenState(
                searchTerm = "",
            )

            underTest.onSearchTermChange(searchTerm)
            underTest.screenState.test {
                // check if screenState search term is not empty
                assertEquals(previousSideEffect, awaitItem())
                // run test
                underTest.onSearchFieldClear()
                assertEquals(emptySearchScreenState, awaitItem())
            }
        }

    /**
     * Helper method for creating ViewModel in order to test SavedState.
     */
    private fun createViewModelInstance(savedStateHandle: SavedStateHandle): AssetsViewModel {
        return AssetsViewModel(
            assetsInteractor = assetsInteractor,
            savedStateHandle = savedStateHandle,
        )
    }
}