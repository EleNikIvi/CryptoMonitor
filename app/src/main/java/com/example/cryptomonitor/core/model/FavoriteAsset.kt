package com.example.cryptomonitor.core.model

data class FavoriteAsset(
    val id: Long,
    val assetId: String,
    val name: String,
    val dataSymbolsCount: Long,
    val isFavorite: Boolean? = false,
    val updated: String,
    val iconUrl: String? = "",
)