package com.example.cryptomonitor.ui.assets

import androidx.compose.runtime.Immutable
import com.example.cryptomonitor.model.Asset

/**
 * Screen state that exposed to View
 */
@Immutable
data class AssetsScreenState(
    val contentState: AssetsContentState = AssetsContentState.EmptyScreen,
    val searchTerm: String = "",
    val showFavorite: Boolean = false,
    val isRefreshing: Boolean = false,
)

@Immutable
sealed interface AssetsContentState {
    object EmptyScreen : AssetsContentState

    data class Loaded(
        val assets: List<AssetState>,
        val loadingMore: Boolean = false
    ) : AssetsContentState

    object ErrorMessage : AssetsContentState

    object Loading : AssetsContentState
}

data class AssetState(
    val assetId: String,
    val name: String,
    val isCrypto: Boolean,
    val dataSymbolsCount: String,
    val volume1DayUsd: String,
    val priceUsd: String = "",
    var isFavorite: Boolean = false,
)

fun Asset.toState(isFavorite: Boolean) = AssetState(
    assetId = assetId,
    name = name,
    isCrypto = typeIsCrypto == 1,
    dataSymbolsCount = dataSymbolsCount.toString(),
    volume1DayUsd = volume1DayUsd.toString(),
    priceUsd = priceUsd?.toString() ?: "",
    isFavorite = isFavorite,
)