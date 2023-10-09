package com.example.cryptomonitor.domain.assetdetails

import com.example.cryptomonitor.MainDispatcherExtension
import com.example.cryptomonitor.domain.AssetDetailsRepository
import io.mockk.coVerify
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(MainDispatcherExtension::class)
class AssetDetailsInteractorTest {

    private val assetId = "BTC"

    private val repository = mockk<AssetDetailsRepository>(relaxed = true)
    private val underTest = AssetDetailsInteractor(assetDetailsRepository = repository)

    @Test
    fun `WHEN fetchExchangeRate is called THEN appropriate repository call is triggered`() =
        runTest {
            underTest.fetchExchangeRate(assetId)
            coVerify {
                repository.fetchExchangeRate(assetId)
            }
        }

    @Test
    fun `WHEN fetchAsset is called THEN appropriate repository call is triggered`() =
        runTest {
            underTest.fetchAsset(assetId)
            coVerify {
                repository.fetchAsset(assetId)
            }
        }

    @Test
    fun `WHEN getAssetIconUrl is called THEN appropriate repository call is triggered`() =
        runTest {
            underTest.getAssetIconUrl(assetId)
            verify {
                repository.getAssetIcon(assetId)
            }
        }

    @Test
    fun `WHEN getAssetDetails is called THEN appropriate repository call is triggered`() =
        runTest {
            underTest.getAssetDetails(assetId)
            verify {
                repository.getAssetDetails(assetId)
            }
        }

    @Test
    fun `WHEN getExchangeRate is called THEN appropriate repository call is triggered`() =
        runTest {
            underTest.getExchangeRate(assetId)
            verify {
                repository.getExchangeRate(assetId)
            }
        }
}