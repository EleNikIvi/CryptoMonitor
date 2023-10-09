package com.example.cryptomonitor.data.local.exchangerate

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.cryptomonitor.core.model.ExchangeRate
import com.example.cryptomonitor.data.local.exchangerate.entity.ExchangeRateEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ExchangeRateDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addExchangeRate(exchangeRate: ExchangeRateEntity)

    @Query("SELECT exr.rateUpdated AS rateUpdated, exr.assetIdBase AS assetIdBase, exr.assetIdQuote AS assetIdQuote, exr.rate AS rate FROM exchange_rate exr WHERE exr.assetIdBase = :assetIdBase")
    fun getExchangeRate(assetIdBase: String): Flow<ExchangeRate?>

    @Query("DELETE FROM exchange_rate WHERE assetIdBase = :assetId")
    suspend fun deleteByAssetId(assetId: String)
}