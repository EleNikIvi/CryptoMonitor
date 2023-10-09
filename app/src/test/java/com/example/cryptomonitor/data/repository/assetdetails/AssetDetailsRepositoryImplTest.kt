package com.example.cryptomonitor.data.repository.assetdetails

import com.example.cryptomonitor.MainDispatcherExtension
import com.example.cryptomonitor.core.MockDateTimeUtils.mockGetCurrentDateTimeFormatted
import com.example.cryptomonitor.data.MockDatabase
import com.example.cryptomonitor.data.core.local.CryptoMonitorDatabase
import com.example.cryptomonitor.data.local.assets.AssetsDao
import com.example.cryptomonitor.data.local.assets.IconsDao
import com.example.cryptomonitor.data.local.assets.entity.Asset
import com.example.cryptomonitor.data.local.details.AssetDetailsDao
import com.example.cryptomonitor.data.local.exchangerate.ExchangeRateDao
import com.example.cryptomonitor.data.local.exchangerate.entity.ExchangeRateEntity
import com.example.cryptomonitor.data.remote.assets.AssetDto
import com.example.cryptomonitor.data.remote.assets.AssetsApi
import com.example.cryptomonitor.data.remote.exchangerate.ExchangeRateApi
import com.example.cryptomonitor.data.remote.exchangerate.ExchangeRateDto
import com.example.cryptomonitor.data.repository.assetdetails.ExchangeRateMapper.toEntity
import com.example.cryptomonitor.data.repository.assets.AssetMapper
import com.example.cryptomonitor.data.repository.assets.AssetMapper.toEntity
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkObject
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import retrofit2.Response

@ExtendWith(MainDispatcherExtension::class)
class AssetDetailsRepositoryImplTest {
    private val exchangeRateApi = mockk<ExchangeRateApi>()
    private val assetsApi = mockk<AssetsApi>()
    private val database = mockk<CryptoMonitorDatabase>(relaxed = true)
    private val assetDao: AssetsDao = database.assetDao
    private val exchangeRateDao: ExchangeRateDao = database.exchangeRateDao
    private val assetDetailsDao: AssetDetailsDao = database.assetDetailsDao
    private val iconsDao: IconsDao = database.iconsDao
    private val underTest = AssetDetailsRepositoryImpl(
        exchangeRateApi = exchangeRateApi,
        assetsApi = assetsApi,
        database = database
    )
    private val assetId = "BTC"
    private val DEFAULT_REFERENCE_CURRENCY = "EUR"
    private val time = "2023-10-08 21:37:28"

    @Test
    fun `WHEN fetchExchangeRate is called THEN appropriate api call is triggered`() =
        runTest {
            underTest.fetchExchangeRate(assetId)
            coVerify(exactly = 1) {
                exchangeRateApi.getExchangeRate(
                    assetIdBase = assetId,
                    assetIdQuote = DEFAULT_REFERENCE_CURRENCY
                )
            }
        }

    @Test
    fun `WHEN fetchExchangeRate is called and api response is success THEN dao is called for deleting and adding new exchange rate`() =
        runTest {
            val assetIdQuote = DEFAULT_REFERENCE_CURRENCY
            val rate = 0.00
            MockDatabase.mockDatabaseWithTransaction(database)
            val api = mockk<ExchangeRateApi>()
            {
                coEvery {
                    getExchangeRate(
                        assetIdBase = assetId,
                        assetIdQuote = DEFAULT_REFERENCE_CURRENCY
                    )
                } returns Response.success(
                    ExchangeRateDto(
                        time = time,
                        assetIdBase = assetId,
                        assetIdQuote = assetIdQuote,
                        rate = rate
                    )
                )
            }
            val entity = ExchangeRateEntity(
                rateUpdated = time,
                assetIdBase = assetId,
                assetIdQuote = assetIdQuote,
                rate = rate,
            )
            mockkObject(ExchangeRateMapper)
            every { any<ExchangeRateDto>().toEntity() } returns entity

            val underTest = AssetDetailsRepositoryImpl(
                exchangeRateApi = api,
                assetsApi = assetsApi,
                database = database
            )

            underTest.fetchExchangeRate(assetId)

            coVerify(exactly = 1) {
                exchangeRateDao.deleteByAssetId(assetId)
                exchangeRateDao.addExchangeRate(entity)
            }
        }

    @Test
    fun `WHEN getAssetIcon is called THEN appropriate dao call is triggered`() =
        runTest {
            underTest.getAssetIcon(assetId)
            coVerify(exactly = 1) {
                iconsDao.getIconUrl(assetId)
            }
        }

    @Test
    fun `WHEN getAssetDetails is called THEN appropriate dao call is triggered`() =
        runTest {
            underTest.getAssetDetails(assetId)
            coVerify(exactly = 1) {
                assetDetailsDao.getAssetDetails(assetId = assetId)
            }
        }

    @Test
    fun `WHEN getExchangeRate is called THEN appropriate dao call is triggered`() =
        runTest {
            underTest.getExchangeRate(assetId)
            coVerify(exactly = 1) {
                exchangeRateDao.getExchangeRate(assetId)
            }
        }

    @Test
    fun `WHEN fetchAsset is called THEN appropriate api call is triggered`() =
        runTest {
            underTest.fetchAsset(assetId)
            coVerify(exactly = 1) {
                assetsApi.getAssetDetails(assetId)
            }
        }

    @Test
    fun `WHEN fetchAsset is called and api response is success THEN dao is called for deleting and adding new asset data`() =
        runTest {
            val assetName = "Bitcoin"
            MockDatabase.mockDatabaseWithTransaction(database)
            mockGetCurrentDateTimeFormatted(time)
            val api = mockk<AssetsApi>()
            {
                coEvery {
                    getAssetDetails(assetId)
                } returns Response.success(
                    AssetDto(assetId = assetId, name = assetName, typeIsCrypto = 1, dataSymbolsCount = 0, volume1HrsUsd = 1.0, volume1DayUsd = 1.0, volume1MthUsd = 1.0),
                )
            }
            val entity = Asset(assetId = assetId, name = assetName, typeIsCrypto = true, dataSymbolsCount = 0, volume1HrsUsd = 1.0, volume1DayUsd = 1.0, volume1MthUsd = 1.0, updated = time,)
            mockkObject(AssetMapper)
            every { any<AssetDto>().toEntity(any()) } returns entity

            val underTest = AssetDetailsRepositoryImpl(
                exchangeRateApi = exchangeRateApi,
                assetsApi = api,
                database = database
            )

            underTest.fetchAsset(this@AssetDetailsRepositoryImplTest.assetId)

            coVerify(exactly = 1) {
                assetDao.deleteAsset(assetId)
                assetDao.addAsset(entity)
            }
        }
}