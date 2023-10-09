package com.example.cryptomonitor.ui.details

import androidx.lifecycle.SavedStateHandle
import app.cash.turbine.test
import com.example.cryptomonitor.MainDispatcherExtension
import com.example.cryptomonitor.core.model.AssetDetails
import com.example.cryptomonitor.core.model.ExchangeRate
import com.example.cryptomonitor.domain.assetdetails.AssetDetailsInteractor
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(MainDispatcherExtension::class)
class AssetDetailsViewModelTest {
    private val assetId = "BTC"
    private val iconUrl = "some_url"

    private val assetDetailsInteractor = mockk<AssetDetailsInteractor>(relaxed = true)

    @Test
    fun `WHEN assetId is retrieved THEN screen state title equals asseId`() =
        runTest {
            val asset_key = "assetId"
            val savedStateHandle = SavedStateHandle(mapOf(asset_key to assetId))
            val underTest = createViewModelInstance(savedStateHandle = savedStateHandle)

            underTest.screenState.test {
                assertEquals(assetId, awaitItem().title)
            }
        }

    @Test
    fun `WHEN iconUrl is retrieved THEN screen state iconUrl contains that data`() =
        runTest {
            val interactor = mockk<AssetDetailsInteractor>(relaxed = true)
            {
                every {
                    getAssetIconUrl(any())
                } returns flowOf(iconUrl)
            }
            val underTest = createViewModelInstance(
                savedStateHandle = SavedStateHandle(),
                interactor = interactor
            )

            underTest.screenState.test {
                assertEquals(iconUrl, awaitItem().iconUrl)
            }
        }

    @Test
    fun `WHEN asset details are retrieved THEN assetDetailsState is Loaded`() =
        runTest {
            val assetDetails = AssetDetails(
                assetId = assetId,
                name = "Bitcoin",
                typeIsCrypto = true,
                dataSymbolsCount = 5,
                volume1DayUsd = 8.80,
                priceUsd = 28081.82,
                assetUpdated = "2023-10-02 11:29:57"
            )
            val interactor = mockk<AssetDetailsInteractor>(relaxed = true)
            {
                every {
                    getAssetDetails(any())
                } returns flowOf(assetDetails)
            }
            val underTest = createViewModelInstance(
                savedStateHandle = SavedStateHandle(),
                interactor = interactor
            )

            val expectedAssetLoadedContent = DetailsContentState.Loaded(
                details = assetDetails
            )

            underTest.screenState.test {
                assertEquals(expectedAssetLoadedContent, awaitItem().assetDetailsState)
            }
        }

    @Test
    fun `WHEN exchange rate is retrieved THEN rateState is Loaded`() =
        runTest {
            val exchangeRate = ExchangeRate(
                rateUpdated = "2023-10-02 11:29:57",
                assetIdBase = "BTC",
                assetIdQuote = "EUR",
                rate = 26597.76,
            )
            val interactor = mockk<AssetDetailsInteractor>(relaxed = true)
            {
                every {
                    getExchangeRate(any())
                } returns flowOf(exchangeRate)
            }
            val underTest = createViewModelInstance(
                savedStateHandle = SavedStateHandle(),
                interactor = interactor
            )

            val exchangeRateLoadedContent = RateContentState.Loaded(
                exchangeRate = exchangeRate
            )

            underTest.screenState.test {
                assertEquals(exchangeRateLoadedContent, awaitItem().rateState)
            }
        }

    @Test
    fun `WHEN no data received for asset details THEN assetDetailsState is Error`() =
        runTest {
            val savedStateHandle = SavedStateHandle()
            val interactor = mockk<AssetDetailsInteractor>(relaxed = true)
            {
                every {
                    getAssetIconUrl(any())
                } returns flowOf(iconUrl)
                every {
                    getAssetDetails(any())
                } returns flowOf(AssetDetails())
                every {
                    getExchangeRate(any())
                } returns flowOf<ExchangeRate?>(null)
            }
            val underTest = createViewModelInstance(
                savedStateHandle = savedStateHandle,
                interactor = interactor
            )

            val expectedAssetLoadedContent = DetailsContentState.Error

            underTest.screenState.test {
                assertEquals(expectedAssetLoadedContent, awaitItem().assetDetailsState)
            }
        }

    @Test
    fun `WHEN no data received for exchange rate THEN rateState is Error`() =
        runTest {
            val savedStateHandle = SavedStateHandle()
            val interactor = mockk<AssetDetailsInteractor>(relaxed = true)
            {
                every {
                    getAssetIconUrl(any())
                } returns flowOf(iconUrl)
                every {
                    getAssetDetails(any())
                } returns flowOf(AssetDetails())
                every {
                    getExchangeRate(any())
                } returns flowOf<ExchangeRate?>(null)
            }
            val underTest = createViewModelInstance(
                savedStateHandle = savedStateHandle,
                interactor = interactor
            )

            val exchangeRateLoadedContent = RateContentState.Error

            underTest.screenState.test {
                assertEquals(exchangeRateLoadedContent, awaitItem().rateState)
            }
        }

    @Test
    fun `WHEN fetch details is triggered THEN appropriate interactor calls for asset and exchange rate are triggered`() =
        runTest {
            val underTest = createViewModelInstance(savedStateHandle = SavedStateHandle())

            underTest.fetchDetails()
            coVerify {
                assetDetailsInteractor.fetchExchangeRate(any())
                assetDetailsInteractor.fetchAsset(any())
            }
        }

    @Test
    fun `WHEN fetchExchangeRate is triggered THEN appropriate interactor call for exchange rate is triggered`() =
        runTest {
            val underTest = createViewModelInstance(savedStateHandle = SavedStateHandle())

            underTest.fetchExchangeRate()
            coVerify {
                assetDetailsInteractor.fetchExchangeRate(any())
            }
        }

    /**
     * Helper method for creating ViewModel in order to test SavedState.
     */
    private fun createViewModelInstance(
        savedStateHandle: SavedStateHandle,
        interactor: AssetDetailsInteractor = assetDetailsInteractor
    ): AssetDetailsViewModel {
        return AssetDetailsViewModel(
            assetDetailsInteractor = interactor,
            savedStateHandle = savedStateHandle,
        )
    }
}