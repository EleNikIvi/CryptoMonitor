package com.example.cryptomonitor.core.model

data class AssetDetails(
    val assetId: String = "",
    val name: String = "",
    val typeIsCrypto: Boolean = true,
    val dataSymbolsCount: Long = 0,
    val volume1DayUsd: Double = 0.00,
    val priceUsd: Double = 0.00,
    val assetUpdated: String = "",
)