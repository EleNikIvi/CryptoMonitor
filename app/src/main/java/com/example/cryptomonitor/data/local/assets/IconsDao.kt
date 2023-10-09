package com.example.cryptomonitor.data.local.assets

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.cryptomonitor.data.local.assets.entity.IconEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface IconsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addIcons(icons: List<IconEntity>)

    @Query("DELETE FROM icon")
    suspend fun deleteIcons()

    @Query("SELECT url FROM icon WHERE assetId = :assetId LIMIT 1")
    fun getIconUrl(assetId: String): Flow<String>
}