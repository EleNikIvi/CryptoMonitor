package com.example.cryptomonitor.ui.details

import androidx.compose.runtime.Immutable
import com.example.cryptomonitor.core.model.AssetDetails
import com.example.cryptomonitor.core.model.ExchangeRate

/**
 * Screen state that exposed to View
 */
@Immutable
data class AssetDetailsScreenState(
    val title: String = "",
    val iconUrl: String? = "",
    val assetDetailsState: DetailsContentState,
    val rateState: RateContentState,
)

sealed interface DetailsContentState {
    object Loading : DetailsContentState
    data class Loaded(
        val details: AssetDetails,
    ) : DetailsContentState

    object Error : DetailsContentState
}

sealed interface RateContentState {
    object Loading : RateContentState
    data class Loaded(
        val exchangeRate: ExchangeRate,
    ) : RateContentState

    object Error : RateContentState
}