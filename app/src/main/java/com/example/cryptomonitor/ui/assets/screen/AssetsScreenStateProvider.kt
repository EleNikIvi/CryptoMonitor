package com.example.cryptomonitor.ui.assets.screen

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.paging.PagingData
import com.example.cryptomonitor.model.FavoriteAsset
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
            volume1DayUsd = 108042863369982.51,
            isFavorite = false,
        ),
        FavoriteAsset(
            id = 2,
            assetId = "BTC",
            name = "Bitcoin",
            dataSymbolsCount = 170087,
            volume1DayUsd = 27775759503427263.17,
            priceUsd = 26576.874244875893,
            isFavorite = true,
        ),
        FavoriteAsset(
            id = 3,
            assetId = "PLN",
            name = "Zloty",
            dataSymbolsCount = 128,
            volume1DayUsd = 6788102.46,
            priceUsd = 0.230142411352508374634968543,
            isFavorite = true,
        ),
        FavoriteAsset(
            id = 4,
            assetId = "NIS",
            name = "NIS",
            dataSymbolsCount = 8,
            volume1DayUsd = 0.00,
            priceUsd = 0.2608621640329585023848101465,
            isFavorite = false,
        ),
        FavoriteAsset(
            id = 5,
            assetId = "LTC",
            name = "Litecoin",
            dataSymbolsCount = 5127,
            volume1DayUsd = 153537588649.33,
            priceUsd = 0.2608621640329585023848101465,
            isFavorite = false,
        ),
        FavoriteAsset(
            id = 6,
            assetId = "XRP",
            name = "Ripple",
            dataSymbolsCount = 6632,
            volume1DayUsd = 1350202176.62,
            priceUsd = 0.5084506916991785739914735375,
            isFavorite = false,
        ),
        FavoriteAsset(
            id = 7,
            assetId = "NMC",
            name = "Namecoin",
            dataSymbolsCount = 55,
            volume1DayUsd = 4570.22,
            priceUsd = 1.2309947576436121519537471296,
            isFavorite = false,
        ),
        FavoriteAsset(
            id = 8,
            assetId = "USDT",
            name = "Tether",
            dataSymbolsCount = 149686,
            volume1DayUsd = 23399985863696744.44,
            priceUsd = 1.0000505850957536160999139588,
            isFavorite = false,
        ),
    )
