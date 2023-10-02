package com.example.cryptomonitor.data.assets.local

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.cryptomonitor.data.assets.local.entity.Favorite
import com.example.cryptomonitor.model.FavoriteAsset

@Dao
interface FavoriteDao {
    @Query("SELECT asset.id, asset.assetId AS assetId, asset.name AS name, asset.dataSymbolsCount AS dataSymbolsCount, asset.volume1DayUsd AS volume1DayUsd, asset.priceUsd AS priceUsd, favorite.isFavorite AS isFavorite FROM asset LEFT JOIN favorite ON asset.assetId = favorite.assetId ORDER BY id ASC")
    fun getPagedFavorites(): PagingSource<Int, FavoriteAsset>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addFavorite(favorite: Favorite)
}