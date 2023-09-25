package com.example.cryptomonitor.data.core.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.cryptomonitor.data.assets.local.AssetDao
import com.example.cryptomonitor.model.Asset
import com.example.cryptomonitor.data.assets.local.FavoriteAssetDao
import com.example.cryptomonitor.model.FavoriteAsset

@Database(
    entities = [Asset::class, FavoriteAsset::class],
    version = 1,
    exportSchema = false
)
abstract class CryptoMonitorDatabase : RoomDatabase() {
    abstract val assetDao: AssetDao
    abstract val favoriteAssetDao: FavoriteAssetDao
}