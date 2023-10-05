package com.example.cryptomonitor.data.core.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.cryptomonitor.data.local.assets.AssetsDao
import com.example.cryptomonitor.data.local.assets.FavoriteDao
import com.example.cryptomonitor.data.local.assets.IconsDao
import com.example.cryptomonitor.data.local.assets.entity.Asset
import com.example.cryptomonitor.data.local.assets.entity.Favorite
import com.example.cryptomonitor.data.local.assets.entity.IconEntity
import com.example.cryptomonitor.data.local.details.AssetDetailsDao
import com.example.cryptomonitor.data.local.exchangerate.ExchangeRateDao
import com.example.cryptomonitor.data.local.exchangerate.entity.ExchangeRateEntity

@Database(
    entities = [Asset::class, Favorite::class, ExchangeRateEntity::class, IconEntity::class],
    version = 1,
    exportSchema = false
)
abstract class CryptoMonitorDatabase : RoomDatabase() {
    abstract val assetDao: AssetsDao
    abstract val favoriteDao: FavoriteDao
    abstract val iconsDao: IconsDao
    abstract val exchangeRateDao: ExchangeRateDao
    abstract val assetDetailsDao: AssetDetailsDao
}