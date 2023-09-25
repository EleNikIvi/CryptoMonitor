package com.example.cryptomonitor.data.assets.remote

import com.example.cryptomonitor.data.assets.AssetsRepositoryImpl
import com.example.cryptomonitor.domain.asset.AssetsRepository
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
    fun bindRecipeRepository(repository: AssetsRepositoryImpl): AssetsRepository
}