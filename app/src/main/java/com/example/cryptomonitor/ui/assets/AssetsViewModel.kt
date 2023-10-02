package com.example.cryptomonitor.ui.assets

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.filter
import com.example.cryptomonitor.domain.asset.AssetsInteractor
import com.example.cryptomonitor.model.FavoriteAsset
import com.example.cryptomonitor.model.Result
import com.example.cryptomonitor.ui.core.flow.SaveableStateFlow.Companion.saveableStateFlow
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
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

    private val _assets: Flow<PagingData<FavoriteAsset>> = assetsInteractor.getFavoriteAssets()
        .cachedIn(viewModelScope)

    private val _searchTerm = savedStateHandle.saveableStateFlow(
        key = "assets-view-model-search-key",
        initialValue = "",
    )
    private val _showFavorites = savedStateHandle.saveableStateFlow(
        key = "assets-view-model-favorite-key",
        initialValue = false,
    )
    private val _isRefreshing = MutableStateFlow(false)

    val assets: Flow<PagingData<FavoriteAsset>> = combine(
        _assets,
        _searchTerm.asStateFlow(),
        _showFavorites.asStateFlow(),
    ) { assets, searchTerm, showFavorite ->
        val filteredBySearchTerm = if (searchTerm.isNotBlank()) {
            assets.filter {
                it.name.contains(other = searchTerm, ignoreCase = true)
            }
        } else assets

        if (showFavorite) filteredBySearchTerm.filter { it.isFavorite ?: false } else filteredBySearchTerm
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = PagingData.empty()
    )

    val screenState: StateFlow<AssetsScreenState> = combine(
        _searchTerm.asStateFlow(),
        _showFavorites.asStateFlow(),
    ) { searchTerm, showFavorite  ->
        AssetsScreenState(
            searchTerm = searchTerm,
            showFavorite = showFavorite,
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = AssetsScreenState()
    )

    fun onSearchTermChange(searchTerm: String) {
        _searchTerm.value = searchTerm
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