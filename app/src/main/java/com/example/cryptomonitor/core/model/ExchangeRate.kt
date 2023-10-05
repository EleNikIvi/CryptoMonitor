package com.example.cryptomonitor.core.model

data class ExchangeRate(
    val rateUpdated: String = "",
    val assetIdBase: String = "",
    val assetIdQuote: String = "",
    val rate: Double = 0.00,
)