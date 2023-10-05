package com.example.cryptomonitor.data.remote.assets

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface AssetsApi {
    @GET("v1/assets")
    suspend fun getAssets(): Response<List<AssetDto>>

    @GET("v1/assets/{assetId}")
    suspend fun getAssetDetails(
        @Path("assetId") assetId: String
    ): Response<AssetDto>

    @GET("v1/assets/icons/{iconSize}")
    suspend fun getAssetsIcons(
        @Path("iconSize") iconSize: Int
    ): Response<List<IconDto>>
}
