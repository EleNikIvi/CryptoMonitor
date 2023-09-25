package com.example.cryptomonitor.data.assets.remote.assets

import retrofit2.Response
import retrofit2.http.GET

interface AssetsApi {
    @GET("v1/assets")
    suspend fun getAssets(): Response<List<AssetDto>>
}
