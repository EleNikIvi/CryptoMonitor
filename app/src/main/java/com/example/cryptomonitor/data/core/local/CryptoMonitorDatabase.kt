package com.example.cryptomonitor.data.core.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.cryptomonitor.data.assets.local.AssetsDao
import com.example.cryptomonitor.data.assets.local.entity.Asset
import com.example.cryptomonitor.data.assets.local.FavoriteDao
import com.example.cryptomonitor.data.assets.local.entity.Favorite

@Database(
    entities = [Asset::class, Favorite::class],
    version = 1,
    exportSchema = false
)
abstract class CryptoMonitorDatabase : RoomDatabase() {
    abstract val assetDao: AssetsDao
    abstract val favoriteIdDao: FavoriteDao
}