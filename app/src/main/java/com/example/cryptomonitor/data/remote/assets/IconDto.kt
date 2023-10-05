package com.example.cryptomonitor.data.remote.assets

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class IconDto(
    @Json(name = "asset_id")
    val assetId: String,
    val url: String,
)
