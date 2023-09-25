package com.example.cryptomonitor.ui.assets.screen

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.example.cryptomonitor.ui.assets.AssetState
import com.example.cryptomonitor.ui.assets.AssetsContentState
import com.example.cryptomonitor.ui.assets.AssetsScreenState
import com.example.cryptomonitor.ui.core.Const.LOREM_IPSUM

class AssetsScreenStateProvider : PreviewParameterProvider<AssetsScreenState> {
    override val values: Sequence<AssetsScreenState> = sequenceOf(
        getPreviewAssets(),
        getEmptyState(),
        getOldDataState(),
    )
}

private fun getEmptyState() = AssetsScreenState(
    contentState = AssetsContentState.EmptyScreen
)

private fun getOldDataState() = AssetsScreenState(
    contentState = AssetsContentState.ErrorMessage,
)

private fun getLoadingState() = AssetsScreenState(
    contentState = AssetsContentState.Loading,
)

private fun getPreviewAssets() = AssetsScreenState(
    showFavorite = true,
    contentState = AssetsContentState.Loaded(
        assets = listOf(
            AssetState(
                assetId = "USD",
                name = LOREM_IPSUM,
                isCrypto = false,
                dataSymbolsCount = LOREM_IPSUM,
                volume1DayUsd = "108042863369982.51",
                isFavorite = false,
            ),
            AssetState(
                assetId = "BTC",
                name = "Bitcoin",
                isCrypto = true,
                dataSymbolsCount = "170087",
                volume1DayUsd = "27775759503427263.17",
                priceUsd = "26576.874244875893",
                isFavorite = true,
            ),
            AssetState(
                assetId = "PLN",
                name = "Zloty",
                isCrypto = false,
                dataSymbolsCount = "128",
                volume1DayUsd = "6788102.46",
                priceUsd = "0.230142411352508374634968543",
            ),
            AssetState(
                assetId = "NIS",
                name = "NIS",
                isCrypto = true,
                dataSymbolsCount = "8",
                volume1DayUsd = "0.00",
                priceUsd = "0.2608621640329585023848101465",
                isFavorite = true,
            ),
            AssetState(
                assetId = "LTC",
                name = "Litecoin",
                isCrypto = true,
                dataSymbolsCount = "5127",
                volume1DayUsd = "153537588649.33",
                priceUsd = "64.638391969884886761682659858",
            ),
            AssetState(
                assetId = "XRP",
                name = "Ripple",
                isCrypto = true,
                dataSymbolsCount = "6632",
                volume1DayUsd = "1350202176.62",
                priceUsd = "0.5084506916991785739914735375",
            ),
            AssetState(
                assetId = "NMC",
                name = "Namecoin",
                isCrypto = true,
                dataSymbolsCount = "55",
                volume1DayUsd = "4570.22",
                priceUsd = "1.2309947576436121519537471296",
            ),
            AssetState(
                assetId = "USDT",
                name = "Tether",
                isCrypto = true,
                dataSymbolsCount = "149686",
                volume1DayUsd = "23399985863696744.44",
                priceUsd = "1.0000505850957536160999139588",
            ),
        )
    )
)