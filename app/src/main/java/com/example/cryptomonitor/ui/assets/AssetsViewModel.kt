package com.example.cryptomonitor.ui.assets

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cryptomonitor.domain.asset.AssetsInteractor
import com.example.cryptomonitor.model.Asset
import com.example.cryptomonitor.model.FavoriteAsset
import com.example.cryptomonitor.model.Result
import com.example.cryptomonitor.ui.core.flow.SaveableStateFlow.Companion.saveableStateFlow
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AssetsViewModel @Inject constructor(
    private val assetsInteractor: AssetsInteractor,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private val _assets = savedStateHandle.saveableStateFlow(
        key = "assets-view-model-list-key",
        initialValue = emptyList<Asset>(),
    )
    private val _favoriteAssets = savedStateHandle.saveableStateFlow(
        key = "assets-view-model-favorites-key",
        initialValue = emptyList<FavoriteAsset>(),
    )
    private val _searchTerm = savedStateHandle.saveableStateFlow(
        key = "assets-view-model-search-key",
        initialValue = "",
    )
    private val _showFavorites = savedStateHandle.saveableStateFlow(
        key = "assets-view-model-favorite-key",
        initialValue = false,
    )
    private val _isOldData = savedStateHandle.saveableStateFlow(
        key = "assets-view-model-old-data-key",
        initialValue = false,
    )
    private val _isRefreshing = MutableStateFlow(false)
    private val _loadingMore = MutableStateFlow(false)

    init {
        refreshAssets()

        viewModelScope.launch(Dispatchers.Default) {
            assetsInteractor.getAssets()
                .collect { values ->
                    _assets.value = values
                }

        }
        viewModelScope.launch(Dispatchers.Default) {
            assetsInteractor.getFavoriteAssets()
                .collect { values ->
                    _favoriteAssets.value = values
                }
        }
    }

    private val _assetsToShow: StateFlow<List<AssetState>> = combine(
        _assets.asStateFlow(),
        _favoriteAssets.asStateFlow(),
        _showFavorites.asStateFlow(),
    ) { assetEntities, favoriteAssets, showFavorite ->
        val assets = assetEntities.map {
            it.toState(
                favoriteAssets.firstOrNull { favorite -> favorite.assetId == it.assetId }?.isFavorite
                    ?: false
            )
        }

        if (showFavorite) assets.filter { it.isFavorite } else assets
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = emptyList<AssetState>()
    )

    val screenState: StateFlow<AssetsScreenState> = combine(
        _assetsToShow,
        _searchTerm.asStateFlow(),
        _isOldData.asStateFlow(),
        _isRefreshing.asStateFlow(),
        _showFavorites.asStateFlow(),
    ) { assets, searchTerm, showCheckNetworkMessage, isRefreshing, showFavorite ->

        val showEmptyMessage = assets.isEmpty() && !showCheckNetworkMessage

        val filteredAssets = if (assets.isNotEmpty() && searchTerm.isNotBlank()) {
            assets.filter { it.name.contains(other = searchTerm, ignoreCase = true) }
        } else assets

        val contentState = when {
            showEmptyMessage -> AssetsContentState.EmptyScreen
            showCheckNetworkMessage -> AssetsContentState.ErrorMessage
            else -> AssetsContentState.Loaded(
                assets = filteredAssets,
            )
        }

        AssetsScreenState(
            contentState = contentState,
            searchTerm = searchTerm,
            showFavorite = showFavorite,
            isRefreshing = isRefreshing,
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = AssetsScreenState()
    )

    fun refreshAssets() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                _isRefreshing.update { true }
                assetsInteractor.refreshAssets().apply {
                    if (this is Result.Success) {
                        _isOldData.update { false }
                    } else {
                        _isOldData.update { true }
                    }
                }
            } catch (e: Exception) {
                _isOldData.update { true }
            } finally {
                _isRefreshing.update { false }
            }
        }
    }

    fun onSearchTermChange(searchTerm: String) {
        _searchTerm.value = searchTerm.trim()
    }

    fun onSearchFieldClear() {
        _searchTerm.value = ""
    }

    fun onShowFavorite(show: Boolean) {
        _showFavorites.value = show
    }

    fun onFavoriteSelected(assetId: String, isFavorite: Boolean) {
        viewModelScope.launch {
            assetsInteractor.saveFavorite(assetId, isFavorite)
        }
    }
}