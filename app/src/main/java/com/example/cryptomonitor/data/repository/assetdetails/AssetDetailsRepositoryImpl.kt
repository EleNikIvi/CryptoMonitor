package com.example.cryptomonitor.data.repository.assetdetails

import android.util.Log
import androidx.room.withTransaction
import com.example.cryptomonitor.core.DateTimeUtils
import com.example.cryptomonitor.core.model.AssetDetails
import com.example.cryptomonitor.core.model.ExchangeRate
import com.example.cryptomonitor.data.core.local.CryptoMonitorDatabase
import com.example.cryptomonitor.data.local.assets.AssetsDao
import com.example.cryptomonitor.data.local.assets.IconsDao
import com.example.cryptomonitor.data.local.details.AssetDetailsDao
import com.example.cryptomonitor.data.local.exchangerate.ExchangeRateDao
import com.example.cryptomonitor.data.remote.assets.AssetsApi
import com.example.cryptomonitor.data.remote.exchangerate.ExchangeRateApi
import com.example.cryptomonitor.data.repository.assetdetails.ExchangeRateMapper.toEntity
import com.example.cryptomonitor.data.repository.assets.AssetMapper.toEntity
import com.example.cryptomonitor.domain.AssetDetailsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AssetDetailsRepositoryImpl @Inject constructor(
    private val exchangeRateApi: ExchangeRateApi,
    private val assetsApi: AssetsApi,
    private val database: CryptoMonitorDatabase,
): AssetDetailsRepository {
    private val assetDao: AssetsDao = database.assetDao
    private val exchangeRateDao: ExchangeRateDao = database.exchangeRateDao
    private val assetDetailsDao: AssetDetailsDao = database.assetDetailsDao
    private val iconsDao: IconsDao = database.iconsDao

    private val DEFAULT_REFERENCE_CURRENCY = "EUR"
    override suspend fun fetchExchangeRate(assetId: String) {
        try {
            val exchangeRateResult = exchangeRateApi.getExchangeRate(
                assetIdBase = assetId,
                assetIdQuote = DEFAULT_REFERENCE_CURRENCY
            )

            if (exchangeRateResult.isSuccessful) {
                database.withTransaction {
                    exchangeRateResult.body()?.toEntity()?.let { exchangeRateEntity ->
                        exchangeRateDao.deleteByAssetId(assetId = assetId)
                        exchangeRateDao.addExchangeRate(exchangeRateEntity)
                    }
                }
            }
        } catch (e: Exception) {
            Log.d("AssetDetailsRepositoryImpl", "Exception e $e")
        }
    }

    override fun getAssetIcon(assetId: String): Flow<String> = iconsDao.getIconUrl(assetId)
    override fun getAssetDetails(assetId: String): Flow<AssetDetails> = assetDetailsDao.getAssetDetails(assetId = assetId)
    override fun getExchangeRate(assetId: String): Flow<ExchangeRate?> = exchangeRateDao.getExchangeRate(assetId)

    override suspend fun fetchAsset(assetId: String) {
        try {
            val assetResult = assetsApi.getAssetDetails(assetId = assetId)

            if (assetResult.isSuccessful) {
                database.withTransaction {
                    assetResult.body()?.toEntity(DateTimeUtils.getCurrentDateTimeFormatted())
                        ?.let { assetEntity ->
                            assetDao.deleteAsset(assetId)
                            assetDao.addAsset(assetEntity)
                        }
                }
            }
        } catch (e: Exception) {
            Log.d("AssetsRepositoryImpl", "Exception e $e")
        }
    }
}