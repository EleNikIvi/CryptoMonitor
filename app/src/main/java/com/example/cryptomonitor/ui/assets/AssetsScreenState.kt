package com.example.cryptomonitor.ui.assets

import androidx.compose.runtime.Immutable
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import com.example.cryptomonitor.core.model.FavoriteAsset

/**
 * Screen state that exposed to View
 */
@Immutable
data class AssetsScreenState(
    val searchTerm: String = "",
    val showFavorite: Boolean = false,
)

sealed interface AssetsContentState {
    object RefreshingEmptyState : AssetsContentState
    object ErrorRefreshEmptyState : AssetsContentState
    object ErrorRefreshState : AssetsContentState
    object RefreshingState : AssetsContentState
    object LoadingState : AssetsContentState
    object LoadedState : AssetsContentState
}

fun LazyPagingItems<FavoriteAsset>.getContentState(): AssetsContentState =
    if (this.loadState.refresh is LoadState.Loading && this.itemCount == 0) {
        AssetsContentState.RefreshingEmptyState
    } else if (this.loadState.refresh is LoadState.Error && this.itemCount == 0) {
        AssetsContentState.ErrorRefreshEmptyState
    } else if (this.loadState.refresh is LoadState.Error) {
        AssetsContentState.ErrorRefreshState
    } else if (this.loadState.refresh is LoadState.Loading) {
        AssetsContentState.RefreshingState
    } else if (this.loadState.append is LoadState.Loading) {
        AssetsContentState.LoadingState
    } else {
        AssetsContentState.LoadedState
    }
