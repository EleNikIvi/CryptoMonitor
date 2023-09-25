package com.example.cryptomonitor.data.core.local

import android.content.Context
import androidx.room.Room
import com.example.cryptomonitor.data.assets.local.AssetDao
import com.example.cryptomonitor.data.assets.local.FavoriteAssetDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class DatabaseModule {
    @Provides
    fun provideAssetDao(appDatabase: CryptoMonitorDatabase): AssetDao {
        return appDatabase.assetDao
    }

    @Provides
    fun provideFavoriteAssetDao(appDatabase: CryptoMonitorDatabase): FavoriteAssetDao {
        return appDatabase.favoriteAssetDao
    }

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext appContext: Context): CryptoMonitorDatabase {
        return Room.databaseBuilder(
            appContext,
            CryptoMonitorDatabase::class.java,
            "CryptoMonitor"
        ).build()
    }
}