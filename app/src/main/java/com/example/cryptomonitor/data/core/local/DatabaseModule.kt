package com.example.cryptomonitor.data.core.local

import android.content.Context
import androidx.room.Room
import com.example.cryptomonitor.data.assets.local.AssetsDao
import com.example.cryptomonitor.data.assets.local.FavoriteDao
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
    fun provideAssetDao(appDatabase: CryptoMonitorDatabase): AssetsDao {
        return appDatabase.assetDao
    }

    @Provides
    fun provideFavoriteIdDao(appDatabase: CryptoMonitorDatabase): FavoriteDao {
        return appDatabase.favoriteIdDao
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