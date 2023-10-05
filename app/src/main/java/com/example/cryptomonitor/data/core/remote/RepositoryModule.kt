package com.example.cryptomonitor.data.core.remote

import com.example.cryptomonitor.data.repository.assetdetails.AssetDetailsRepositoryImpl
import com.example.cryptomonitor.data.repository.assets.AssetsRepositoryImpl
import com.example.cryptomonitor.domain.AssetDetailsRepository
import com.example.cryptomonitor.domain.AssetsRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
interface RepositoryModule {

    @Binds
    @ViewModelScoped
    fun bindAssetsRepository(repository: AssetsRepositoryImpl): AssetsRepository

    @Binds
    @ViewModelScoped
    fun bindDetailsRepository(repository: AssetDetailsRepositoryImpl): AssetDetailsRepository
}