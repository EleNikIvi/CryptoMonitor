package com.example.cryptomonitor.ui.assets.screen

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.paging.PagingData
import com.example.cryptomonitor.core.model.FavoriteAsset
import com.example.cryptomonitor.ui.core.Const
import kotlinx.coroutines.flow.MutableStateFlow

class AssetsScreenStateProvider :
    PreviewParameterProvider<MutableStateFlow<PagingData<FavoriteAsset>>> {
    override val values: Sequence<MutableStateFlow<PagingData<FavoriteAsset>>> = sequenceOf(
        MutableStateFlow(PagingData.from(getPreviewAssets())),
        MutableStateFlow(PagingData.from(emptyList())),
    )
}

private fun getPreviewAssets() =
    listOf<FavoriteAsset>(
        FavoriteAsset(
            id = 1,
            assetId = "USD",
            name = Const.LOREM_IPSUM,
            dataSymbolsCount = 5,
            isFavorite = false,
            updated = "2023-10-02 11:29:57"
        ),
        FavoriteAsset(
            id = 2,
            assetId = "BTC",
            name = "Bitcoin",
            dataSymbolsCount = 170087,
            isFavorite = true,
            updated = "2023-10-02 11:29:57"
        ),
        FavoriteAsset(
            id = 3,
            assetId = "PLN",
            name = "Zloty",
            dataSymbolsCount = 128,
            isFavorite = true,
            updated = "2023-10-02 11:29:57"
        ),
        FavoriteAsset(
            id = 4,
            assetId = "NIS",
            name = "NIS",
            dataSymbolsCount = 8,
            isFavorite = false,
            updated = "2023-10-02 11:29:57"
        ),
        FavoriteAsset(
            id = 5,
            assetId = "LTC",
            name = "Litecoin",
            dataSymbolsCount = 5127,
            isFavorite = false,
            updated = "2023-10-02 11:29:57"
        ),
        FavoriteAsset(
            id = 6,
            assetId = "XRP",
            name = "Ripple",
            dataSymbolsCount = 6632,
            isFavorite = false,
            updated = "2023-10-02 11:29:57"
        ),
        FavoriteAsset(
            id = 7,
            assetId = "NMC",
            name = "Namecoin",
            dataSymbolsCount = 55,
            isFavorite = false,
            updated = "2023-10-02 11:29:57"
        ),
        FavoriteAsset(
            id = 8,
            assetId = "USDT",
            name = "Tether",
            dataSymbolsCount = 149686,
            isFavorite = false,
            updated = "2023-10-02 11:29:57"
        ),
    )
