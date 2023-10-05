package com.example.cryptomonitor.ui.details.screen

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.example.cryptomonitor.core.model.AssetDetails
import com.example.cryptomonitor.core.model.ExchangeRate
import com.example.cryptomonitor.ui.core.Const
import com.example.cryptomonitor.ui.details.AssetDetailsScreenState
import com.example.cryptomonitor.ui.details.DetailsContentState
import com.example.cryptomonitor.ui.details.RateContentState

class AssetsDetailsScreenStateProvider :
    PreviewParameterProvider<AssetDetailsScreenState> {
    override val values: Sequence<AssetDetailsScreenState> = sequenceOf(
        fullyLoadedState(),
        partlyLoadedState(),
        errorState()
    )
}

private fun fullyLoadedState() = AssetDetailsScreenState(
    title = "BTC",
    iconUrl = "",
    assetDetailsState = DetailsContentState.Loaded(
        details = AssetDetails(
            assetId = "BTC",
            name = Const.LOREM_IPSUM,
            typeIsCrypto = true,
            dataSymbolsCount = 5,
            volume1DayUsd = 8.80,
            priceUsd = 28081.82,
            assetUpdated = "2023-10-02 11:29:57"
        )
    ),
    rateState = RateContentState.Loaded(
        exchangeRate = ExchangeRate(
            rateUpdated = "2023-10-02 11:29:57",
            assetIdBase = "BTC",
            assetIdQuote = "EUR",
            rate = 26597.76,
        )
    )
)

private fun partlyLoadedState() = AssetDetailsScreenState(
    title = "BTC",
    iconUrl = null,
    assetDetailsState = DetailsContentState.Loaded(
        details = AssetDetails(
            assetId = "BTC",
            name = Const.LOREM_IPSUM,
            typeIsCrypto = true,
            dataSymbolsCount = 5,
            volume1DayUsd = 8.80,
            priceUsd = 0.00,
            assetUpdated = "2023-10-02 11:29:57"
        )
    ),
    rateState = RateContentState.Loading
)

private fun errorState() = AssetDetailsScreenState(
    title = "BTC",
    iconUrl = "",
    assetDetailsState = DetailsContentState.Error,
    rateState = RateContentState.Error
)