package com.example.cryptomonitor.data.remote.assets

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class AssetDto(
    @Json(name = "asset_id")
    val assetId: String,
    val name: String,
    @Json(name = "type_is_crypto")
    val typeIsCrypto: Int,
    @Json(name = "data_start")
    val dataStart: String? = null,
    @Json(name = "data_end")
    val dataEnd: String? = null,
    @Json(name = "data_quote_start")
    val dataQuoteStart: String? = null,
    @Json(name = "data_quote_end")
    val dataQuoteEnd: String? = null,
    @Json(name = "data_orderbook_start")
    val dataOrderBookStart: String? = null,
    @Json(name = "data_orderbook_end")
    val dataOrderBookEnd: String? = null,
    @Json(name = "data_trade_start")
    val dataTradeStart: String? = null,
    @Json(name = "data_trade_end")
    val dataTradeEnd: String? = null,
    @Json(name = "data_symbols_count")
    val dataSymbolsCount: Long,
    @Json(name = "volume_1hrs_usd")
    val volume1HrsUsd: Double,
    @Json(name = "volume_1day_usd")
    val volume1DayUsd: Double,
    @Json(name = "volume_1mth_usd")
    val volume1MthUsd: Double,
    @Json(name = "price_usd")
    val priceUsd: Double? = 0.00,
)
