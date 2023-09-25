package com.example.cryptomonitor.data.assets.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.cryptomonitor.model.FavoriteAsset
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteAssetDao {
    @Query("SELECT * FROM favorite_asset")
    fun getAllFavorites(): Flow<List<FavoriteAsset>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addFavorite(favorite: FavoriteAsset)
}