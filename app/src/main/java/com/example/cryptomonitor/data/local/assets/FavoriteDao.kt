package com.example.cryptomonitor.data.local.assets

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.cryptomonitor.data.local.assets.entity.Favorite
import com.example.cryptomonitor.core.model.FavoriteAsset

@Dao
interface FavoriteDao {
    @Query("SELECT asset.id AS id, asset.assetId AS assetId, asset.name AS name, asset.dataSymbolsCount AS dataSymbolsCount, asset.updated AS updated, favorite.isFavorite AS isFavorite, icon.url AS iconUrl FROM asset LEFT JOIN favorite ON asset.assetId = favorite.assetId LEFT JOIN icon ON asset.assetId = icon.assetId")
    fun getPagedFavorites(): PagingSource<Int, FavoriteAsset>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addFavorite(favorite: Favorite)
}