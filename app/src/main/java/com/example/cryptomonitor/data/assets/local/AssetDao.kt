package com.example.cryptomonitor.data.assets.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.cryptomonitor.model.Asset
import kotlinx.coroutines.flow.Flow

@Dao
interface AssetDao {
    @Query("SELECT * FROM asset")
    fun getAllAssets(): Flow<List<Asset>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addAsset(asset: Asset)

    @Query("SELECT * FROM asset WHERE assetId = :id")
    suspend fun getAsset(id: Long): Asset
}