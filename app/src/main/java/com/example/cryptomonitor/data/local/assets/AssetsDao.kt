package com.example.cryptomonitor.data.local.assets

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.cryptomonitor.data.local.assets.entity.Asset

@Dao
interface AssetsDao {
    @Query("SELECT * FROM asset")
    fun getAllAssets(): PagingSource<Int, Asset>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addAssets(assets: List<Asset>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addAsset(asset: Asset)

    @Query("DELETE FROM asset")
    suspend fun deleteAssets()

    @Query("DELETE FROM asset WHERE assetId = :assetId")
    suspend fun deleteAsset(assetId: String)

    @Query("Select updated From asset Order By updated DESC LIMIT 1")
    suspend fun getUpdatedTime(): String
}