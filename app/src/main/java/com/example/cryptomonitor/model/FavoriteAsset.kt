package com.example.cryptomonitor.model

data class FavoriteAsset(
    val id: Long,
    val assetId: String,
    val name: String,
    val dataSymbolsCount: Long,
    val volume1DayUsd: Double,
    val priceUsd: Double? = 0.00,
    val isFavorite: Boolean? = false,
)