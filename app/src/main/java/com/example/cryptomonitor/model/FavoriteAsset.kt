package com.example.cryptomonitor.model

data class FavoriteAsset(
    val id: Long,
    val assetId: String,
    val name: String,
    val typeIsCrypto: Int,
    val dataStart: String? = null,
    val dataEnd: String? = null,
    val dataQuoteStart: String? = null,
    val dataQuoteEnd: String? = null,
    val dataOrderBookStart: String? = null,
    val dataOrderBookEnd: String? = null,
    val dataTradeStart: String? = null,
    val dataTradeEnd: String? = null,
    val dataSymbolsCount: Long,
    val volume1HrsUsd: Double,
    val volume1DayUsd: Double,
    val volume1MthUsd: Double,
    val priceUsd: Double? = 0.00,
    val isFavorite: Boolean? = false,
)