package com.example.cryptomonitor.data.core.local

import android.content.Context
import androidx.room.Room
import com.example.cryptomonitor.data.local.assets.AssetsDao
import com.example.cryptomonitor.data.local.assets.FavoriteDao
import com.example.cryptomonitor.data.local.assets.IconsDao
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
    fun provideFavoriteDao(appDatabase: CryptoMonitorDatabase): FavoriteDao {
        return appDatabase.favoriteDao
    }

    @Provides
    fun provideIconsDao(appDatabase: CryptoMonitorDatabase): IconsDao {
        return appDatabase.iconsDao
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