package com.example.cryptomonitor.data.remote.exchangerate

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ExchangeRateApi {
    @GET("v1/exchangerate/{assetIdBase}/{assetIdQuote}")
    suspend fun getExchangeRate(
        @Path("assetIdBase") assetIdBase: String,
        @Path("assetIdQuote") assetIdQuote: String,
    ): Response<ExchangeRateDto>
}