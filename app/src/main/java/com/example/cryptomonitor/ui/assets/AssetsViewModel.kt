package com.example.cryptomonitor.ui.assets

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.filter
import com.example.cryptomonitor.core.model.FavoriteAsset
import com.example.cryptomonitor.domain.asset.AssetsInteractor
import com.example.cryptomonitor.ui.core.flow.SaveableStateFlow.Companion.saveableStateFlow
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AssetsViewModel @Inject constructor(
    private val assetsInteractor: AssetsInteractor,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private val _assets: Flow<PagingData<FavoriteAsset>> = assetsInteractor.getFavoriteAssets()
        .cachedIn(viewModelScope)

    private val searchTerm = savedStateHandle.saveableStateFlow(
        key = "assets-view-model-search-key",
        initialValue = "",
    )
    private val _showFavorites = savedStateHandle.saveableStateFlow(
        key = "assets-view-model-favorite-key",
        initialValue = false,
    )

    val assets: Flow<PagingData<FavoriteAsset>> = combine(
        _assets,
        searchTerm.asStateFlow(),
        _showFavorites.asStateFlow(),
    ) { assets, searchTerm, showFavorite ->
        val filteredBySearchTerm = if (searchTerm.isNotBlank()) {
            assets.filter {
                it.name.contains(other = searchTerm, ignoreCase = true)
            }
        } else assets

        if (showFavorite) filteredBySearchTerm.filter {
            it.isFavorite ?: false
        } else filteredBySearchTerm
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = PagingData.empty()
    )

    val screenState: StateFlow<AssetsScreenState> = combine(
        searchTerm.asStateFlow(),
        _showFavorites.asStateFlow(),
    ) { searchTerm, showFavorite ->
        AssetsScreenState(
            searchTerm = searchTerm,
            showFavorite = showFavorite,
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = AssetsScreenState()
    )

    private val ICON_SIZE = 32

    init {
        viewModelScope.launch(Dispatchers.IO) {
            assetsInteractor.fetchIcons(ICON_SIZE)
        }
    }

    fun onSearchTermChange(searchTerm: String) {
        this.searchTerm.value = searchTerm
    }

    fun onSearchFieldClear() {
        searchTerm.value = ""
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