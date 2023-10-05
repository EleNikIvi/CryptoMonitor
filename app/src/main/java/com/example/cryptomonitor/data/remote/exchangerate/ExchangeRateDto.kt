package com.example.cryptomonitor.data.remote.exchangerate

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ExchangeRateDto(
    val time: String?,
    @Json(name = "asset_id_base")
    val assetIdBase: String?,
    @Json(name = "asset_id_quote")
    val assetIdQuote: String?,
    val rate: Double?,
)