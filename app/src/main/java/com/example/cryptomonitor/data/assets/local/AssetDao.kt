package com.example.cryptomonitor.data.assets.local

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.cryptomonitor.model.Asset

@Dao
interface AssetDao {
    @Query("SELECT * FROM asset")
    fun getAllAssets(): PagingSource<Int, Asset>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addAssets(assets: List<Asset>)

    @Query("SELECT * FROM asset WHERE assetId = :id")
    suspend fun getAsset(id: String): Asset

    @Query("DELETE FROM asset")
    suspend fun deleteAssets()
}